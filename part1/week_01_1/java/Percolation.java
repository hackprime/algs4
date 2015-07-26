// import java.lang.IndexOutOfBoundsException;
// import java.lang.IllegalArgumentException;
// 15:50
public class Percolation {
    private int n;
    private int top_site;
    private int bottom_site;
    private boolean[][] sites;
    private WeightedQuickUnionUF uf;

    private void checkArgs(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }
    }
    private void checkNodesAround(int i, int j) {
        int item_index = ijToIndex(i, j);
        if (i > 1 && isOpen(i - 1, j)) uf.union(item_index, ijToIndex(i - 1, j)); // bottom
        if (i < n && isOpen(i + 1, j)) uf.union(item_index, ijToIndex(i + 1, j)); // top
        if (j > 1 && isOpen(i, j - 1)) uf.union(item_index, ijToIndex(i, j - 1)); // left
        if (j < n && isOpen(i, j + 1)) uf.union(item_index, ijToIndex(i, j + 1)); // right
    }
    private int ijToIndex(int i, int j) {
        return n * (i - 1) + j;
    }

    public Percolation(int N) {
        // create N-by-N grid, with all sites blocked
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        int uf_size = N * N + 2;
        n = N;
        uf = new WeightedQuickUnionUF(uf_size);
        bottom_site = 0;
        top_site = N * N + 1;
        sites = new boolean[n][];

        for (int i = 0; i < n; i++) {
            sites[i] = new boolean[n];
            for (int j = 0; j < n; j++) {
                sites[i][j] = false;
            }
        }
    }
    public void open(int i, int j) {
        // open site (row i, column j) if it is not open already
        checkArgs(i, j);
        int index = ijToIndex(i, j);
        sites[i - 1][j - 1] = true;
        checkNodesAround(i, j);
        if (i == 1) uf.union(index, top_site);
        if (i == n) uf.union(index, bottom_site);

    }
    public boolean isOpen(int i, int j) {
        // is site (row i, column j) open?
        checkArgs(i, j);

        return sites[i - 1][j - 1];
    }
    public boolean isFull(int i, int j) {
        // is site (row i, column j) full?
        checkArgs(i, j);

        return uf.connected(top_site, ijToIndex(i, j));
    }
    public boolean percolates() {
        // does the system percolate?
        return uf.connected(top_site, bottom_site);
    }

    public static void main(String[] args) {
        // test client (optional)
        int i, j, k;
        int N = Integer.parseInt(args[0]);
        int sitesCount = N * N;
        int[] sitesToOpen = new int[sitesCount];

        for (k = 0; k < sitesCount; k++) {
            sitesToOpen[k] = k + 1;
        }
        StdRandom.shuffle(sitesToOpen);

        Percolation percolation = new Percolation(N);

        k = 0;
        while (k < sitesCount) {
            i = (sitesToOpen[k] - 1) / N;
            j = (sitesToOpen[k] - 1) % N;
            percolation.open(i + 1, j + 1);
            k++;
            if (percolation.percolates()) {
                break;
            }
        }
        StdOut.println("System percolates after " + k + " sites opened");
    }
}
