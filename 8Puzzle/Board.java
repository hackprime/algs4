import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] blocks;
    private int N;

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        // number of blocks out of place
        int hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = targetValue(i, j);
                if (value != 0 && blocks[i][j] != targetValue(i, j)) {
                    hamming += 1;
                }
            }
        }
        return hamming;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                int[] place = targetPlace(blocks[i][j]);
                manhattan += Math.abs(place[0] - i) + Math.abs(place[1] - j);
            }
        }
        return manhattan;
    }

    private int targetValue(int i, int j) {
        return (i * N + j + 1) % (N * N);
    }

    private int[] targetPlace(int value) {
        int[] result = new int[2];
        if (value == 0) {
            result[0] = N - 1;
            result[1] = N - 1;
        } else {
            result[0] = (value - 1) / N;
            result[1] = (value - 1) % N;
        }
        return result;
    }

    public boolean isGoal() {
        // is this board the goal board?
        return manhattan() == 0;
    }

    public Board twin() {
        // a board that is obtained by exchanging two adjacent blocks in the same row
        int[][] twinBlocks = copy(blocks);
        int i = 0, j = 0;

        if (twinBlocks[i][j] != 0 && twinBlocks[i][j+1] != 0) {
            swap(twinBlocks, i, j, i, j + 1);
        } else {
            swap(twinBlocks, i + 1, j, i + 1, j+1);
        }
        return new Board(twinBlocks);
    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board yBoard = (Board) y;

        if (yBoard.blocks.length != N) {
            return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != yBoard.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        Stack<Board> stack = new Stack<Board>();
        int zeroI = 0, zeroJ = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    break;
                }
            }
        }

        int[][] b;
        // left
        if (zeroJ != 0) {
            b = copy(blocks);
            swap(b, zeroI, zeroJ, zeroI, zeroJ - 1);
            stack.push(new Board(b));
        }

        // right
        if (zeroJ != N - 1) {
            b = copy(blocks);
            swap(b, zeroI, zeroJ, zeroI, zeroJ + 1);
            stack.push(new Board(b));
        }

        // top
        if (zeroI != 0) {
            b = copy(blocks);
            swap(b, zeroI, zeroJ, zeroI - 1, zeroJ);
            stack.push(new Board(b));
        }

        // bottom
        if (zeroI != N - 1) {
            b = copy(blocks);
            swap(b, zeroI, zeroJ, zeroI + 1, zeroJ);
            stack.push(new Board(b));
        }

        return stack;
    }

    private int[][] copy(int[][] srcBlocks) {
        int[][] copyBlocks = new int[srcBlocks.length][srcBlocks.length];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copyBlocks[i][j] = srcBlocks[i][j];
            }
        }
        return copyBlocks;
    }

    private void swap(int[][] srcBlocks, int i1, int j1, int i2, int j2) {
        int tmp = srcBlocks[i1][j1];
        srcBlocks[i1][j1] = srcBlocks[i2][j2];
        srcBlocks[i2][j2] = tmp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    public static void main(String[] args) {
        int[][] data = {{1, 2, 3}, {0, 4, 5}, {7, 8, 6}};
        Board b = new Board(data);

        for (int i = 0; i < b.N; i++) {
            for (int j = 0; j < b.N; j++) {
                int targetValue = b.targetValue(i, j);
                int[] targetPlace = b.targetPlace(targetValue);
                StdOut.println(targetValue + " -> " + targetPlace[0] + ","
                               + targetPlace[1]);
            }
        }

        StdOut.println("hamming = " + b.hamming());
        StdOut.println("manhattan = " + b.manhattan());
        StdOut.println("isGoal = " + b.isGoal());
        StdOut.println("toString\n" + b.toString());
        StdOut.println("neighbors:\n");
        for (Board i : b.neighbors()) {
            StdOut.println(i.toString());
        }
        StdOut.println("twin\n");
        StdOut.println(b.twin().toString());
    }
}
