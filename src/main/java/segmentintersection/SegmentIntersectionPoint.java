package segmentintersection;

public class SegmentIntersectionPoint {

    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{x=" + x + ", y=" + y + "}";
        }
    }


    private static final double EPSILON = 1e-9;

    /*
     *  det | a b |
     *      | c d |
     */
    private static double determinant(double a, double b, double c, double d) {
        return a * d - c * b;
    }

    /*
     *  Check if C is between A and B
     */
    private static boolean between(double a, double b, double c) {
        return Math.min(a, b) <= c + EPSILON && c <= Math.max(a, b) + EPSILON;
    }

    /*
     *     a     b  c     d
     *     *-----*  *-----*     return b - c (< 0, do not overlap)
     *     a   c   b      d
     *     *---*---*------*     return b - c (> 0, overlap)
     */
    private static double overlap(double a, double b, double c, double d) {
        return Math.min(Math.max(a, b), Math.max(c, d)) - Math.max(Math.min(a, b), Math.min(c, d));
    }

    /*
     *  Point of intersection of the lines a -> b and c -> d
     */
    private static Point pointOfIntersection(Point a, Point b, Point c, Point d) {
        // line A1 * x + B1 * y + C1 = 0
        double A1 = a.y - b.y,  B1 = b.x - a.x,  C1 = -A1 * a.x - B1 * a.y;

        // line A2 * x + B2 * y + C2 = 0
        double A2 = c.y - d.y,  B2 = d.x - c.x,  C2 = -A2 * c.x - B2 * c.y;

        // denominator in Cramer's rule
        double denominator = determinant(A1, B1, A2, B2);

        // the lines are not parallel - they have point of intersection
        if (denominator < -EPSILON || denominator > EPSILON) {
            // finding that point
            double x = -determinant(C1, B1, C2, B2) / denominator;
            double y = -determinant(A1, C1, A2, C2) / denominator;

            // checking if the point on the line segments
            if (between(a.x, b.x, x) && between(a.y, b.y, y) &&
                    between(c.x, d.x, x) && between(c.y, d.y, y)) {
                return new Point(x, y);
            } else {
                // the point of intersection is not on both line segments
                //           *
                // *------*  |
                //           |
                //           *
                return null;
            }
        }

        // else - the lines are parallel or equivalent

        double equivDeterminantX = determinant(A1, C1, A2, C2);
        double equivDeterminantY = determinant(B1, C1, B2, C2);

        // the lines are equivalent
        if (equivDeterminantX > -EPSILON && equivDeterminantX < EPSILON &&
            equivDeterminantY > -EPSILON && equivDeterminantY < EPSILON) {

            double overlap_x = overlap(a.x, b.x, c.x, d.x);
            double overlap_y = overlap(a.y, b.y, c.y, d.y);

            // the lines have overlapping points
            if (overlap_x > -EPSILON && overlap_y > -EPSILON) {

                // *-------*-----------* the lines are just touching each other
                if (overlap_x < EPSILON && overlap_y < EPSILON) {
                    double x = Math.min(Math.max(a.x, b.x), Math.max(c.x, d.x));
                    double y = Math.min(Math.max(a.y, b.y), Math.max(c.y, d.y));
                    return new Point(x, y);
                }

                // *----*----*--------* the lines have many points of intersection
                return null;
            }

            // *----*  *------* the lines have no points of intersection
            return null;
        }

        // the lines are parallel and not equivalent - no points of intersection
        // *------*
        //     *-------*
        return null;
    }


    public static void main(String[] args) {

        // [3, 0]
        Point a = new Point(1, 0);
        Point b = new Point(3, 0);
        Point c = new Point(3.0000000001, -0.0000000001);
        Point d = new Point(4, -0.0000000001);
        System.out.println("\nExpected: {3, 0}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [3, 0]
        a = new Point(1, 0);
        b = new Point(3, 0);
        c = new Point(3.0000000001, -0.0000000001);
        d = new Point(4, 0);
        System.out.println("\nExpected: {3, 0}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [3, 0]
        d = new Point(1, 0);
        c = new Point(3, 0);
        b = new Point(3.0000000001, 0.0000000001);
        a = new Point(4, 0);
        System.out.println("\nExpected: {3, 0}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [3, 0]
        d = new Point(1, 0);
        c = new Point(3, 0);
        b = new Point(3.0000000001, 0);
        a = new Point(4, 0);
        System.out.println("\nExpected: {3, 0}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [3, 0]
        a = new Point(1, 0);
        b = new Point(3, 0);
        c = new Point(3.0000000001, 0);
        d = new Point(4, 0);
        System.out.println("\nExpected: {3, 0}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // null (many points of intersection)
        a = new Point(1, 0);
        b = new Point(3, 0);
        c = new Point(2.9, 0);
        d = new Point(4, 0);
        System.out.println("\nExpected: null (many points of intersection)");
        System.out.println(pointOfIntersection(a, b, c, d));

        // null (no points of intersection)
        a = new Point(1, 0);
        b = new Point(3, 0);
        c = new Point(3.1, 0);
        d = new Point(4, 0);
        System.out.println("\nExpected: null (no points of intersection)");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [2, 0]
        a = new Point(1, 0);
        b = new Point(3, 0);
        c = new Point(2, 4);
        d = new Point(2, 0);
        System.out.println("\nExpected: {2, 0}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [7, 1]
        a = new Point(1, 1);
        b = new Point(7, 1);
        c = new Point(7, 4);
        d = new Point(7, 1);
        System.out.println("\nExpected: {7, 1}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [2, 0]
        a = new Point(2, 4);
        b = new Point(2, 0);
        c = new Point(1, 0);
        d = new Point(3, 0);
        System.out.println("\nExpected: {2, 0}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [-1, 1]
        a = new Point(-2, 2);
        b = new Point(0, 0);
        c = new Point(-2, 0);
        d = new Point(0, 2);
        System.out.println("\nExpected: {-1, 1}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [0, 5]
        a = new Point(-2, 4);
        b = new Point(2, 6);
        c = new Point(-1, 8);
        d = new Point(1, 2);
        System.out.println("\nExpected: {0, 5}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [3, -6]
        a = new Point(3, -4);
        b = new Point(3, -7);
        c = new Point(3, -6);
        d = new Point(5, -6);
        System.out.println("\nExpected: {3, -6}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [1, 0]
        a = new Point(1, 0);
        b = new Point(1, 0);
        c = new Point(1, 0);
        d = new Point(1, 0);
        System.out.println("\nExpected: {1, 0}");
        System.out.println(pointOfIntersection(a, b, c, d));

        // [1, 0]
        a = new Point(0, 0);
        b = new Point(1, 0);
        c = new Point(1.0000000001, -1);
        d = new Point(1.0000000001, 1);
        System.out.println("\nExpected: {1, 0}");
        System.out.println(pointOfIntersection(a, b, c, d));
    }
}