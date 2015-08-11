import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        StdDraw.setScale(0, 32768);
        StdDraw.setPenColor(StdDraw.BOOK_RED);

        In file = new In(args[0]);
        int n = file.readInt();
        Point[] pSort = new Point[n];
        Point[] pIter = new Point[n];
        for (int i = 0; i < n; i++) {
            Point point = new Point(file.readInt(), file.readInt());
            point.draw();
            pIter[i] = point;
            pSort[i] = point;
        }

        Arrays.sort(pIter);

        for (int i = 0; i < n; i++) {
            Arrays.sort(pSort, pIter[i].SLOPE_ORDER);
            double slope = pIter[i].slopeTo(pSort[1]);
            int j;
            for (j = 1; j < n; j++) {
                if (slope != pIter[i].slopeTo(pSort[j])) {
                    break;
                }
            }

            if (--j >= 3) {
                StdOut.print(pIter[i]);
                for (int k = 1; k <= j; k++) {
                    pIter[i].drawTo(pSort[k]);
                    StdOut.print(" --> " + pSort[k]);
                }
                StdOut.println();
            }
        }
    }
}
