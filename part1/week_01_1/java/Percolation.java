// import java.lang.IndexOutOfBoundsException;
// import java.lang.IllegalArgumentException;
// 15:50
public class Percolation {
    private int n;
    private boolean percolated;
    private boolean[] sites;
    private boolean[] linkedWithTop;
    private boolean[] linkedWithBottom;
    private WeightedQuickUnionUF uf;

    public Percolation(int N) {
        // create N-by-N grid, with all sites blocked
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        percolated = false;
        n = N;
        int count = N * N;
        uf = new WeightedQuickUnionUF(N * N + 1);
        sites = new boolean[count + 1];
        linkedWithTop = new boolean[count + 1];
        linkedWithBottom = new boolean[count + 1];

        for (int i = 1; i <= count; i++) {
            sites[i] = false;
            linkedWithTop[i] = i >= 1 && i <= n;
            linkedWithBottom[i] = i >= count - n && i <= count;
        }
    }

    private void checkArgs(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }
    }
    private void union(int p, int q) {
        int pTreeTop = uf.find(p);
        int qTreeTop = uf.find(q);
        boolean hasTop = linkedWithTop[pTreeTop] || linkedWithTop[qTreeTop];
        boolean hasBottom = linkedWithBottom[pTreeTop] || linkedWithBottom[qTreeTop];
        uf.union(p, q);
        int newTop = uf.find(p);
        linkedWithTop[newTop] = hasTop;
        linkedWithTop[newTop] = hasBottom;
        if (hasTop && hasBottom) {
            percolated = true;
        }
    }
    private void checkNodesAround(int i, int j) {
        int itemIndex = ijToIndex(i, j);
        if (i > 1 && isOpen(i - 1, j))
            union(itemIndex, ijToIndex(i - 1, j)); // bottom
        if (i < n && isOpen(i + 1, j))
            union(itemIndex, ijToIndex(i + 1, j)); // top
        if (j > 1 && isOpen(i, j - 1))
            union(itemIndex, ijToIndex(i, j - 1)); // left
        if (j < n && isOpen(i, j + 1))
            union(itemIndex, ijToIndex(i, j + 1)); // right
    }
    private int ijToIndex(int i, int j) {
        return n * (i - 1) + j;
    }


    public void open(int i, int j) {
        // open site (row i, column j) if it is not open already
        checkArgs(i, j);
        int index = ijToIndex(i, j);
        sites[index] = true;
        checkNodesAround(i, j);
    }
    public boolean isOpen(int i, int j) {
        // is site (row i, column j) open?
        checkArgs(i, j);
        return sites[ijToIndex(i, j)];
    }
    public boolean isFull(int i, int j) {
        // is site (row i, column j) full?
        checkArgs(i, j);
        return linkedWithTop[uf.find(ijToIndex(i, j))];
    }
    public boolean percolates() {
        // does the system percolate?
        return percolated;
    }

    public static void main(String[] args) {
        // test client (optional)
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }
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
                System.out.println("System percolates at " + k + " opened site");
                break;
            }
        }
    }
}
