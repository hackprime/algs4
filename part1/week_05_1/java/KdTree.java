import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int n;        // the right/top subtree
        private boolean vertical;        // the right/top subtree

        public Node(Point2D p, int n, boolean vertical) {
            this.p = p;
            this.n = n;
            this.vertical = vertical;
        }
    }

    Node root = null;
    int count = 0;

    public KdTree() {
        // construct an empty set of points
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
            return;
        }
        root = insert(root, p, false);
    }

    private Node insert(Node x, Point2D p, boolean vertical) {
        if (x == null) {
            return new Node(p, 1, true);
        }
        int cmp = p.compareTo(x.p);
        if (cmp < 0) {
            x.lb = insert(x.lb,  p, !vertical);
        } else if (cmp > 0) {
            x.rt = insert(x.rt, p, !vertical);
        } else {
            x.vertical = vertical;
            x.p = p;
        }
        x.n = 1 + size(x.lb) + size(x.rt);
        return x;
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) {
            return false;
        }
        int cmp = p.compareTo(x.p);
        if (cmp < 0) {
            return contains(x.lb, p);
        } else if (cmp > 0) {
            return contains(x.rt, p);
        } else {
            return true;
        }
    }

    public void draw() {
        // draw all points to standard draw
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) {
            return;
        }
        draw(node.lb);
        draw(node.rt);
        StdOut.println(node.p.toString());
        node.p.draw();
    }

    // public Iterable<Point2D> range(RectHV rect) {
    //     // all points that are inside the rectangle
    //     StdDraw.setPenColor(StdDraw.RED);
    //     StdDraw.setPenColor(StdDraw.BLUE);
    //     Queue<Point2D> queue = new Queue<Point2D>();
    //     return queue
    // }

    public Iterable<Point2D> range(RectHV rect) {
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
            queue.enqueue(x.lb);
            queue.enqueue(x.rt);
        }
        return points;
    }

    // public Point2D nearest(Point2D p) {
    //     // a nearest neighbor in the set to point p; null if the set is empty
    // }

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
        StdOut.println("tree contains " + point.toString() + ": " + tree.contains(point));

        StdDraw.setPenRadius();

        RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        rect.draw();
        StdOut.println("Points in rectangle " + rect.toString() + ":");
        for (Point2D p : tree.range(rect)) {
            StdOut.println(p.toString());
        }
        StdOut.println("-");

        // Point2D point = new Point2D(0.1, 0.1);
        // point.draw();
        // StdOut.println("The nearest point to point " + point.toString() + ": ");
        // StdOut.println(tree.nearest(point).toString());
        // StdOut.println("-");

    }
}
