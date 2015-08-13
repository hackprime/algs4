import java.util.Arrays;

public class Fast {
    public static void drawCollinear(int endIndex, int count, Point point, Point[] sortedPoints) {
        Point[] resPoints = new Point[count];
        for (int k = endIndex - count + 1; k <= endIndex; k++) {
            ////////////////////////////
            point.drawTo(sortedPoints[k]);
            StdOut.print(" --> " + sortedPoints[k]);
        }
        StdOut.println();
    }

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
            pIter[i].index = i;
        }

        for (int i = 0; i < n; i++) {
            Arrays.sort(pSort, pIter[i].SLOPE_ORDER);
            StdOut.println("i = " + i + " " + "[" + pIter[i].index + "]" + pIter[i]);

            double slope = pIter[i].slopeTo(pSort[1]);
            double newSlope = slope;
            int count = 1;
            StdOut.println("   mj = " + 1 + " slope=[" + slope + "," + newSlope + "] " + "[" + pSort[1].index + "]" + pSort[1]);

            for (int j = 2; j < n; j++) {
                newSlope = pIter[i].slopeTo(pSort[j]);
                StdOut.println("    j = " + j + " slope=[" + slope + "," + newSlope + "] " + "[" + pSort[j].index + "]" + pSort[j]);

                if (newSlope == slope && pSort[j].index > i) {
                    count++;
                }

                if (count >= 3 && (newSlope != slope || j == n - 1)) {
                    StdOut.println("        DRAW!");
                    int endIndex;
                    if (newSlope != slope) {
                        endIndex = j - 1;
                    } else {
                        endIndex = j;
                    }
                    drawCollinear(endIndex, count, pIter[i], pSort);
                    count = 1;
                    slope = newSlope;
                }
            }
        }
    }
}
