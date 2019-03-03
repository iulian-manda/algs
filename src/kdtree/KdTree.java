package kdtree;

import edu.princeton.cs.algs4.*;

public class KdTree {

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        throwIfNull(p);
        if (contains(p)) return;
        root = insert(root, p, true);
        size++;
    }

    private Node insert(Node node, Point2D value, boolean xOrder) {
        if (node == null) return new Node(value, xOrder);
        int cmp = compare(value, node);
        if (cmp < 0) node.left = insert(node.left, value, !xOrder);
        else node.right = insert(node.right, value, !xOrder);
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        throwIfNull(p);
        Node node = root;
        while (node != null) {
            int cmp = compare(p, node);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else if (p.equals(node.value)) return true;
            else node = node.right;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, null);
    }

    private void draw(Node node, Node previous) {
        if (node == null) return;
        double nodeY = node.value.y();
        double nodeX = node.value.x();
        if (previous == null) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(nodeX, 0, nodeX, 1);
        } else {
            double previousY = previous.value.y();
            double previousX = previous.value.x();
            if (node.xOrder) {
                StdDraw.setPenColor(StdDraw.RED);
                if (nodeY > previousY)
                    StdDraw.line(nodeX, previousY, nodeX, 1);
                else
                    StdDraw.line(nodeX, previousY, nodeX, 0);
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                if (nodeX > previousX)
                    StdDraw.line(previousX, nodeY, 1, nodeY);
                else
                    StdDraw.line(previousX, nodeY, 0, nodeY);
            }
        }
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        node.value.draw();
        StdDraw.setPenRadius();
        draw(node.left, node);
        draw(node.right, node);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        throwIfNull(rect);
        Queue<Point2D> queue = new Queue<>();
        range(rect, root, queue);
        return queue;
    }

    private void range(RectHV rect, Node node, Queue<Point2D> queue) {
        if (node == null) return;
        if (rect.contains(node.value)) {
            queue.enqueue(node.value);
        }
        double n, min, max;
        if (node.xOrder) {
            n = node.value.x();
            min = rect.xmin();
            max = rect.xmax();
        } else {
            n = node.value.y();
            min = rect.ymin();
            max = rect.ymax();
        }
        if (max < n) {
            range(rect, node.left, queue);
        } else if (n < min)
            range(rect, node.right, queue);
        else {
            range(rect, node.left, queue);
            range(rect, node.right, queue);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        throwIfNull(p);
        if (isEmpty()) {
            return null;
        }
        return nearest(root, p, root.value);
    }

    private Point2D nearest(Node node, Point2D p, Point2D nearest) {
        if (node == null) return nearest;
        double distance = p.distanceSquaredTo(node.value);
        double nearestDistance = p.distanceSquaredTo(nearest);

        if (distance < nearestDistance) {
            nearest = node.value;
            nearestDistance = distance;
        }

        Node first, second;
        int cmp = compare(p, node);
        if (cmp < 0) {
            first = node.left;
            second = node.right;
        } else {
            first = node.right;
            second = node.left;
        }

        Point2D result = nearest(first, p, nearest);
        double resultDistance = p.distanceSquaredTo(result);
        if (resultDistance >= nearestDistance) {
            result = nearest(second, p, nearest);
            resultDistance = p.distanceSquaredTo(result);
            if (resultDistance < nearestDistance) {
                nearest = result;
            }
        } else if(resultDistance < nearestDistance){
            nearest = result;
        }
        return nearest;
    }

    private void throwIfNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }

    private int compare(Point2D point, Node node) {
        if (node == null) {
            return 1;
        }
        return node.xOrder ? Point2D.X_ORDER.compare(point, node.value) : Point2D.Y_ORDER.compare(point, node.value);
    }

    private class Node {
        private final Point2D value;
        private Node left, right;
        private final boolean xOrder;

        public Node(Point2D value, boolean xOrder) {
            this.value = value;
            this.xOrder = xOrder;
        }

        public double getKey() {
            return xOrder ? value.x() : value.y();
        }
    }

    public static void main(String[] args) {
        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        Point2D point2D = new Point2D(0.063, 0.607);
        StdOut.println(kdtree.nearest(point2D));
        kdtree.draw();
        StdDraw.setPenRadius(0.01);
        point2D.draw();
    }
}