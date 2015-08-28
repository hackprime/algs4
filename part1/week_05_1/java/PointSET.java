// Corner cases.
// Throw a java.lang.NullPointerException if any argument is null.
//
// Performance requirements.
// Your implementation should support insert()
// and contains() in time proportional to the logarithm of the number of points
// in the set in the worst case; it should support nearest() and range() in time
// proportional to the number of points in the set.


import java.util.TreeSet

public class PointSET {
    public PointSET() {
        // construct an empty set of points
    }

    public boolean isEmpty() {
        // is the set empty?
    }

    public int size() {
        // number of points in the set
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
    }

    public void draw() {
        // draw all points to standard draw
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
    }


    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}
