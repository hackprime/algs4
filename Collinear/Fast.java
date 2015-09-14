import java.util.Arrays;

public class Fast {
    private static void drawCollinear(int index, int count,
                                     Point point, Point[] sortedPoints) {
        StdOut.print(point);
        for (int k = 0; k < count; k++) {
            point.drawTo(sortedPoints[index - k]);
            StdOut.print(" -> " + sortedPoints[k]);
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        StdDraw.setScale(0, 32768);
        StdDraw.setPenColor(StdDraw.BOOK_RED);

        In file = new In(args[0]);
        int n = file.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            Point point = new Point(file.readInt(), file.readInt());
            point.draw();
            points[i] = point;
        }
        Arrays.sort(points);
        // for (int i = 0; i < n; i++) {
        //     points[i].index = i;
        // }

        for (int i = 0; i < n - 1; i++) {
            Point[] collinearPoints = new Point[n - (i + 1)];
            int initialIndex = i + 1;
            for (int j = initialIndex; j < n; j++) {
                collinearPoints[j - initialIndex] = points[j];
            }

            Arrays.sort(collinearPoints, points[i].SLOPE_ORDER);
            // StdOut.println("i = " + i + " " + "[" + points[i].index
            //                + "]" + points[i]);

            double slope = points[i].slopeTo(collinearPoints[0]);
            double newSlope;
            int count = 0;

            for (int j = 0; j < collinearPoints.length; j++) {
                newSlope = points[i].slopeTo(collinearPoints[j]);
                // StdOut.println("    j = " + j + " slope=[" + slope + ","
                //                + newSlope + "] " + "[" + collinearPoints[j].index
                //                + "]" + collinearPoints[j]);
                if (newSlope == slope) {
                    count++;
                    if (count >= 3 && j == collinearPoints.length - 1) {
                        drawCollinear(j, count, points[i], collinearPoints);
                    }
                } else {
                    if (count >= 3) {
                        // StdOut.println("        DRAW! " + count);
                        drawCollinear(j - 1, count, points[i], collinearPoints);
                    }
                    count = 1;
                    slope = newSlope;
                }

            }
        }
    }
}
