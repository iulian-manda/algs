package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    private final SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        throwIfNull(p);
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        throwIfNull(p);
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        throwIfNull(rect);
        Queue<Point2D> result = new Queue<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                result.enqueue(p);
            }
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        throwIfNull(p);
        if (isEmpty()) {
            return null;
        }
        Point2D nearest = set.min();
        for (Point2D point : set) {
            if (p.distanceSquaredTo(point) < p.distanceSquaredTo(nearest)) {
                nearest = point;
            }
        }
        return nearest;
    }

    private void throwIfNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
}