import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * A solution to percolation problem using
 * Weighted quick union find algorithm.
 *
 * @author Xu Yu
 * @since  2015-10-7
 */
public class Percolation {
    /** top row in set */
    public static final int TOP_IN = 0x01;
    /** bottom row in set */
    public static final int BOTTOM_IN = 0x10;
    /** both top and bottom row in set */
    public static final int TOP_BOTTOM_IN = TOP_IN | BOTTOM_IN;
    /** enum for site closed */
    public static final int STATE_CLOSED = 0;
    /** enum for site open */
    public static final int STATE_OPEN = 1;
    /** union find instance */
    private WeightedQuickUnionUF mUnionFind;
    /** grid for mapping structure */
    private int[][] mGrid;
    /** size of grid */
    private int mSize;
    /** track of percolation */
    private int[] mElements;
    /** whether system is percolate */
    private boolean mIsPercolate;

    /**
     * @param N size of grid
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        mUnionFind = new WeightedQuickUnionUF((N+1)*(N+1));
        mGrid = new int[N+1][N+1];
        mElements = new int[(N+1)*(N+1)];
        mSize = N;
        mIsPercolate = false;
    }

    /**
     * Open site (row i, column j) if it is not open already.
     *
     * @param i row index
     * @param j column index
     */
    public void open(int i, int j) {
        checkIndexValid(i, j);
        mGrid[i][j] = STATE_OPEN;
        int k = mUnionFind.find(flattenIndex(i, j));
        if (i == 1) {
            mElements[k] |= TOP_IN;
        }
        if (i == mSize) {
            mElements[k] |= BOTTOM_IN;
        }

        link(i, j, i-1, j);
        link(i, j, i+1, j);
        link(i, j, i, j-1);
        link(i, j, i, j+1);

        k = mUnionFind.find(flattenIndex(i, j));
        if (!mIsPercolate && mElements[k] == TOP_BOTTOM_IN) {
            mIsPercolate = true;
        }
    }

    /**
     * Check if specific site is open.
     */
    public boolean isOpen(int i, int j) {
        checkIndexValid(i, j);
        return mGrid[i][j] == STATE_OPEN;
    }

    /**
     * Check if specific site is full.
     */
    public boolean isFull(int i, int j) {
        checkIndexValid(i, j);
        int k = mUnionFind.find(flattenIndex(i, j));
        if ((mElements[k] & TOP_IN) != 0x00) {
            return true;
        }
        return false;
    }

    /**
     * Check if system percolate.
     */
    public boolean percolates() {
        return mIsPercolate;
    }

    /**
     * Try to link open sites.
     */
    private void link(int x1, int y1, int x2, int y2) {
        if (isIndexValid(x2, y2) && isOpen(x2, y2)) {
            int z1 = mUnionFind.find(flattenIndex(x1, y1));
            int z2 = mUnionFind.find(flattenIndex(x2, y2));
            mUnionFind.union(z1, z2);
            int z3 = mUnionFind.find(z1);
            mElements[z3] = mElements[z1] | mElements[z2];
        }
    }
    /**
     * Throw exception if index is invalid.
     */
    private void checkIndexValid(int i, int j) {
       if (!isIndexValid(i, j)) {
           throw new IndexOutOfBoundsException();
       }
    }

    /**
     * Check index boundary.
     */
    private boolean isIndexValid(int i, int j) {
       if (i < 1 || i > mSize || j < 1 || j > mSize) {
           return false;
       }
       return true;
    }

    /**
     * Flatten 2D grid to 1D array.
     */
    private int flattenIndex(int i, int j) {
        return i * (mSize + 1) + j;
    }

    /**
     * Test client.
     */
    public static void main(String[] args) {
    }
}
