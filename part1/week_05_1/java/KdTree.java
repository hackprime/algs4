import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private int n;
        private boolean vertical;

        public Node(Point2D p, int n, boolean vertical, RectHV rect) {
            this.p = p;
            this.n = n;
            this.rect = rect;
            this.vertical = vertical;
        }
    }

    private Node root = null;
    private RectHV plain;

    public KdTree() {
        // construct an empty set of points
        plain = new RectHV(0, 0, 1, 1);
    }

    public boolean isEmpty() {
        // is the set empty?
        return size() == 0;
    }

    public int size() {
        // number of points in the set
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.n;
        }
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        root = insert(root, p, true, plain);
    }

    private Node insert(Node x, Point2D p, boolean vertical, RectHV rect) {
        if (x == null) {
            return new Node(p, 1, vertical, rect);
        }
        if (x.p.equals(p)) {
            return x;
        }

        boolean cmp;
        if (vertical) {
            cmp = p.x() < x.p.x();
        } else {
            cmp = p.y() < x.p.y();
        }
        RectHV nextRect;
        if (cmp) {
            if (x.lb == null) {
                double x1 = rect.xmin();
                double y1 = rect.ymin();
                double x2, y2;
                if (vertical) {
                    x2 = x.p.x();
                    y2 = rect.ymax();
                } else {
                    x2 = rect.xmax();
                    y2 = x.p.y();
                }
                nextRect = new RectHV(x1, y1, x2, y2);
            } else {
                nextRect = x.lb.rect;
            }
            x.lb = insert(x.lb,  p, !vertical, nextRect);
            x.n = 1 + size(x.lb) + size(x.rt);
        } else {
            if (x.rt == null) {
                double x1, y1;
                if (vertical) {
                    x1 = x.p.x();
                    y1 = rect.ymin();
                } else {
                    x1 = rect.xmin();
                    y1 = x.p.y();
                }
                double x2 = rect.xmax();
                double y2 = rect.ymax();
                nextRect = new RectHV(x1, y1, x2, y2);
            } else {
                nextRect = x.rt.rect;
            }
            x.rt = insert(x.rt, p, !vertical, nextRect);
            x.n = 1 + size(x.lb) + size(x.rt);
        }
        return x;
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) {
            return false;
        }
        if (x.p.equals((Object) p)) {
            return true;
        }
        boolean cmp;
        if (x.vertical) {
            cmp = p.x() < x.p.x();
        } else {
            cmp = p.y() < x.p.y();
        }
        if (cmp) {
            return contains(x.lb, p);
        } else {
            return contains(x.rt, p);
        }
    }

    public void draw() {
        // draw all points to standard draw
        draw(root, true);
    }

    private void draw(Node x, boolean vertical) {
        if (x == null) {
            throw new java.lang.NullPointerException();
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        x.p.draw();
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        if (x.lb != null) {
            draw(x.lb, !vertical);
        }
        if (x.rt != null) {
            draw(x.rt, !vertical);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }
        Queue<Point2D> points = new Queue<Point2D>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) {
                continue;
            }
            if (rect.contains(x.p)) {
                points.enqueue(x.p);
            }
            if (x.lb != null) {
                queue.enqueue(x.lb);
            }
            if (x.rt != null) {
                queue.enqueue(x.rt);
            }
        }
        return points;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        Point2D champion = null;
        double championDistance = Double.MAX_VALUE;
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) {
                continue;
            }
            double distance = p.distanceTo(x.p);
            if (distance < championDistance) {
                champion = x.p;
                championDistance = distance;
            }
            queue.enqueue(x.lb);
            queue.enqueue(x.rt);
        }
        return champion;
    }


    public static void main(String[] args) {
        KdTree tree = new KdTree();

        StdOut.println("Is empty: " + tree.isEmpty());

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        In file = new In(args[0]);
        while (!file.isEmpty()) {
            Point2D point = new Point2D(file.readDouble(), file.readDouble());
            if (tree.contains(point)) {
                StdOut.println("Duplicate detected: " + point.toString());
            }
            tree.insert(point);
        }
        StdOut.println("Size: " + tree.size());
        StdOut.println("Is empty: " + tree.isEmpty());
        tree.draw();

        Point2D point = new Point2D(0.5, 1.0);
        StdOut.println("tree contains " + point.toString() + ": "
                       + tree.contains(point));

        // StdDraw.setPenRadius();

        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        // rect.draw();
        StdOut.println("Points in rectangle " + rect.toString() + ":");
        for (Point2D p : tree.range(rect)) {
            StdOut.println(p.toString());
        }
        StdOut.println("-");

        Point2D nearPoint = new Point2D(0.1, 0.1);
        // point.draw();
        StdOut.println("The nearest point to point " + nearPoint.toString() + ": ");
        StdOut.println(tree.nearest(nearPoint).toString());
        StdOut.println("-");

    }
}
