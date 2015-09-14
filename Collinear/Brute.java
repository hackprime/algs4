import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        StdDraw.setScale(0, 32768);
        StdDraw.setPenColor(StdDraw.BOOK_RED);

        In file = new In(args[0]);
        int n = file.readInt();
        Point[] p = new Point[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Point(file.readInt(), file.readInt());
            p[i].draw();
        }
        Arrays.sort(p);

        for (int i = 0; i < n; i++) {

            for (int j = i+1; j < n; j++) {
                double slopeJ = p[i].slopeTo(p[j]);

                for (int k = j+1; k < n; k++) {
                    double slopeK = p[i].slopeTo(p[k]);

                    for (int l = k+1; l < n; l++) {
                        double slopeL = p[i].slopeTo(p[l]);

                        if (slopeJ == slopeK && slopeK == slopeL
                                && slopeL != Double.NEGATIVE_INFINITY) {
                            p[i].drawTo(p[j]);
                            p[j].drawTo(p[k]);
                            p[k].drawTo(p[l]);
                            StdOut.println(p[i] + " -> " + p[j]
                                           + " -> " + p[k] + " -> " + p[l]);
                        }
                    }
                }
            }
        }
    }
}
