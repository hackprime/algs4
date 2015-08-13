import java.util.Arrays;

public class Fast {
    public static void drawCollinear(int index, int count, Point point, Point[] sortedPoints) {
        StdOut.print(point);
        for (int k = 0; k < count; k++) {
            point.drawTo(sortedPoints[index - k]);
            StdOut.print(" => " + sortedPoints[k]);
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        StdDraw.setScale(0, 32768);
        StdDraw.setPenColor(StdDraw.BOOK_RED);

        In file = new In(args[0]);
        int n = file.readInt();
        Point[] pSort = new Point[n];
        for (int i = 0; i < n; i++) {
            Point point = new Point(file.readInt(), file.readInt());
            point.draw();
            pSort[i] = point;
        }
        Arrays.sort(pSort);
        for (int i = 0; i < n; i++) {
            pSort[i].index = i;
        }

        for (int i = 0; i < n - 1; i++) {
            Point[] collinearPoints = new Point[n - (i + 1)];
            int initialIndex = i + 1;
            for (int j = initialIndex; j < n; j++) {
                collinearPoints[j - initialIndex] = pSort[j];
            }

            Arrays.sort(collinearPoints, pSort[i].SLOPE_ORDER);
            StdOut.println("i = " + i + " " + "[" + pSort[i].index + "]" + pSort[i]);

            double slope = pSort[i].slopeTo(collinearPoints[0]);
            double newSlope;
            int count = 0;

            for (int j = 0; j < collinearPoints.length; j++) {
                newSlope = pSort[i].slopeTo(collinearPoints[j]);
                StdOut.println("    j = " + j + " slope=[" + slope + "," + newSlope + "] " + "[" + collinearPoints[j].index + "]" + collinearPoints[j]);
                if (newSlope == slope) {
                    count++;
                }

                if (count >= 3 && (newSlope != slope || j == collinearPoints.length - 1)) {
                    StdOut.println("        DRAW! " + count);

                    int endIndex;
                    if (newSlope != slope) {
                        endIndex = j - 1;
                    } else {
                        endIndex = j;
                    }
                    drawCollinear(endIndex, count, pSort[i], collinearPoints);
                    count = 1;
                    slope = newSlope;
                }
            }
        }
    }
}
