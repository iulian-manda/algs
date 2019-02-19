package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private int n;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        for (Point point : input) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] points = Arrays.copyOf(input, input.length);
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i+1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        segments = new LineSegment[1];
        n = 0;
        if (points.length < 4) {
            return;
        }
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        for (int i = 0; i < points.length - 2; i++) {
            Arrays.sort(sortedPoints, i, points.length, points[i].slopeOrder());
            int segmentLength = 1;
            int startIndex = i + 1;
            double slope = points[i].slopeTo(sortedPoints[i + 1]);
            for (int j = i + 2; j < sortedPoints.length; j++) {
                double newSlope = points[i].slopeTo(sortedPoints[j]);
                if (slope != newSlope) {
                    if (segmentLength >= 3) {
                        Arrays.sort(sortedPoints, startIndex, j);
                        add(points[i], sortedPoints[j - 1]);
                    }
                    slope = newSlope;
                    segmentLength = 1;
                    startIndex = j;
                } else {
                    segmentLength++;
                }
            }
            if (segmentLength >= 3) {
                Arrays.sort(sortedPoints, startIndex, sortedPoints.length);
                add(points[i], sortedPoints[sortedPoints.length - 1]);
            }
        }

    }

    private void add(Point p, Point q) {
        LineSegment segment = new LineSegment(p, q);
        if (n == segments.length) {
            resize(segments.length * 2);
        }
        segments[n++] = segment;
    }

    private void resize(int capacity) {
        LineSegment[] newSegments = Arrays.copyOf(segments, capacity);
        segments = newSegments;
    }

    // the number of line segments
    public int numberOfSegments() {
        return n;
    }

    // the line segments
    public LineSegment[] segments() {
        if (segments.length > n) {
            resize(n);
        }
        return Arrays.copyOf(segments, n);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}