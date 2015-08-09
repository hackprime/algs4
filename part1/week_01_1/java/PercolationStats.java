// import .Percolation;
public class PercolationStats {
    private int n;
    private int t;
    private double[] thresholds;

    public PercolationStats(int N, int T) {
        // perform T independent experiments on an N-by-N grid
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }
        n = N;
        t = T;
        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            thresholds[i] = getThreshold();
        }
    }
    private double getThreshold() {
        // test client (optional)
        int i, j, k;
        int sitesCount = n * n;
        int[] sitesToOpen = new int[sitesCount];

        for (k = 0; k < sitesCount; k++) {
            sitesToOpen[k] = k + 1;
        }
        StdRandom.shuffle(sitesToOpen);

        Percolation percolation = new Percolation(n);

        k = 0;
        while (k < sitesCount) {
            i = (sitesToOpen[k] - 1) / n;
            j = (sitesToOpen[k] - 1) % n;
            percolation.open(i + 1, j + 1);
            k++;
            if (percolation.percolates()) {
                break;
            }
        }
        return (double) k / (double) sitesCount;
    }
    public double mean() {
        // sample mean of percolation threshold
        double sum = 0;
        for (int i = 0; i < t; i++) {
            sum = sum + thresholds[i];
        }
        return sum / t;
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        if (t == 1) return Double.NaN;

        double sum = 0;
        double mean = mean();
        for (int i = 0; i < t; i++) {
            double delta = thresholds[i] - mean;
            sum = sum + delta * delta;
        }
        return sum / (t - 1);
    }
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean() - (1.96 * stddev() / Math.sqrt(t));
    }
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + (1.96 * stddev() / Math.sqrt(t));
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException();
        }
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);
        StdOut.println("mean" + "\t" + ps.mean());
        StdOut.println("stddev" + "\t" + ps.stddev());
        StdOut.println("95% confidence interval" + "\t"
                       + ps.confidenceLo() + " " + ps.confidenceHi());
    }
}
