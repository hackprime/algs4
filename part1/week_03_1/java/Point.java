import java.util.Comparator;

public class Point implements Comparable<Point> {

    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;
    private final int y;
    public int index;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double slopeToA = slopeTo(a);
            double slopeToB = slopeTo(b);
            if (slopeToA < slopeToB)      return -1;
            else if (slopeToA > slopeToB) return 1;
            else                          return a.compareTo(b);
        }
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            return 0;
        } else {
            return ((double)that.y - (double)this.y) / ((double)that.x - (double)this.x);
        }
    }

    public int compareTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return 0;
        } else if (this.y < that.y || this.y == that.y && this.x < that.x) {
            return -1;
        } else {
            return 1;
        }
    }

    public static void print(String s) {
        StdOut.println(s);
    }
    public static void check(boolean cond) {
        if (cond) {
            print("Correct\n");
        } else {
            print("FAIL\n");
        }
    }
    public static void main(String[] args) {
        StdDraw.setScale(0, 100);
        Point a = new Point(20, 20);
        Point b = new Point(80, 80);
        Point c = new Point(40, 60);
        Point d = new Point(60, 100);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        a.draw();
        b.draw();
        c.draw();
        d.draw();
        a.drawTo(b);
        a.drawTo(d);
        StdDraw.setPenColor(StdDraw.BLACK);
        a.drawTo(c);

        print("Slope to vertical line");
        check(a.slopeTo(new Point(a.x, 40)) == Double.POSITIVE_INFINITY);

        print("Slope to horisontal line");
        check(a.slopeTo(new Point(40, a.y)) == 0);

        print("Slope to itself");
        check(a.slopeTo(a) == Double.NEGATIVE_INFINITY);

        print("Slope to an arbitrary point");
        check(a.slopeTo(b) == 1.0);
        check(a.slopeTo(c) == 2.0);

        print("Equal slopes");
        check(a.slopeTo(c) == a.slopeTo(d));
    }
}
