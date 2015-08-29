// Corner cases.
// Throw a java.lang.NullPointerException if any argument is null.
//
// Performance requirements.
// Your implementation should support insert()
// and contains() in time proportional to the logarithm of the number of points
// in the set in the worst case; it should support nearest() and range() in time
// proportional to the number of points in the set.

import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;

public class PointSET {
    private TreeSet<Point2D> set;

    public PointSET() {
        // construct an empty set of points
        set = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        // is the set empty?
        return set.isEmpty();
    }

    public int size() {
        // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        set.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        return set.contains((Object) p);
    }

    public void draw() {
        // draw all points to standard draw
        for (Point2D p : set) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                queue.enqueue(p);
            }
        }
        return queue;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        Point2D nearest = null;
        double shortestDistance = Double.POSITIVE_INFINITY;
        for (Point2D point : set) {
            if (nearest == null || point.distanceTo(p) <= shortestDistance) {
                nearest = point;
                shortestDistance = point.distanceTo(p);
            }
        }
        return nearest;
    }


    public static void main(String[] args) {
        PointSET pointSet = new PointSET();

        StdOut.println("Is empty: " + pointSet.isEmpty());

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        In file = new In(args[0]);
        while (!file.isEmpty()) {
            Point2D point = new Point2D(file.readFloat(), file.readFloat());
            if (pointSet.contains(point)) {
                StdOut.println("Duplicate detected: " + point.toString());
            }
            pointSet.insert(point);
        }
        StdOut.println("Size: " + pointSet.size());
        StdOut.println("Is empty: " + pointSet.isEmpty());
        pointSet.draw();

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius();

        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        rect.draw();
        StdOut.println("Points in rectangle " + rect.toString() + ":");
        for (Point2D p : pointSet.range(rect)) {
            StdOut.println(p.toString());
        }
        StdOut.println("-");

        Point2D point = new Point2D(0.1, 0.1);
        point.draw();
        StdOut.println("The nearest point to point " + point.toString() + ": ");
        StdOut.println(pointSet.nearest(point).toString());
        StdOut.println("-");
    }
}
