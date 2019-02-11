import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int n;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        segments = new LineSegment[points.length * points.length * points.length];
        n = 0;
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                        if (segment(p, q, r, s)) {
                            segments[n++] = new LineSegment(p, s);
                        }
                    }
                }
            }
        }
    }

    private boolean segment(Point p, Point q, Point r, Point s) {
        return p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s);
    }

    // the number of line segments
    public int numberOfSegments() {
        return n;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    public static void main (String[] args) throws Exception {
        Scanner sc = new Scanner(new FileInputStream(new File("/Users/iulian.manda/IdeaProjects/percolation/resources/collinear/input6.txt")));
        int n = sc.nextInt();
        Point[] points = new Point[n];
        for (int i=0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            points[i] = new Point(x, y);
        }
        BruteCollinearPoints bc = new BruteCollinearPoints(points);
        LineSegment[] segments = bc.segments();
        for (Point p : points) {
            StdOut.println(p);
        }
        for (int i = 0; i < bc.numberOfSegments(); i++) {
            StdOut.println(segments[i]);
        }
    }
}