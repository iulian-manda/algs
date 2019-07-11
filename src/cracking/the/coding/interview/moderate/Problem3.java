package cracking.the.coding.interview.moderate;

public class Problem3 {

    public static void main(String[] args) {
        Point a = new Point(4, 2);
        Point b = new Point(2, 4);
        Point c = new Point(2, 2);
        Point d = new Point(4, 4);
        Line line1 = new Line(a, b);
        Line line2 = new Line(c, d);
        System.out.println(line1.intersect(line2));
    }

    static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Point)) return false;
            Point that = (Point) other;
            return this.x == that.x && this.y == that.y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    static class Line {
        double a, b;
        Point point1, point2;
        boolean vertical, horizontal;

        Line(Point point1, Point point2) {
            this.point1 = point1;
            this.point2 = point2;
            if (point1.x == point2.x) {
                horizontal = true;
                return;
            }
            if (point2.y == point1.y) {
                vertical = true;
            }
            a = (point2.y - point1.y) / (point2.x - point1.x);
            b = point1.y - a * point1.x;
        }

        Point intersect(Line that) {
            double x = (that.b - this.b) / (this.a - that.a);
            double y = a * x + b;
            return new Point(x, y);
        }
    }

}
