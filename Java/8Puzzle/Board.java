import java.util.ArrayList;

/**
 * Created by egoless on 2015/11/7.
 */
public class Board {

    private boolean bGoal;
    private final int[][] tiles;
    private final int N;

    public Board(int[][] blocks) {
        N = blocks.length;
        tiles = new int[N][N];
        bGoal = true;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = blocks[i][j];
                if (bGoal && tiles[i][j] != goalValueAt(i, j)) bGoal = false;
            }
        }
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int h = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!isEnd(i, j) && tiles[i][j] != goalValueAt(i, j)) {
                    h++;
                }
            }
        }
        return h;
    }

    public int manhattan() {
        int m = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int v = tiles[i][j];
                if (v != 0 && v != goalValueAt(i, j)) {
                    int is = (v - 1) / N;
                    int js = v - is * N - 1;
                    int d = Math.abs(i-is) + Math.abs(j-js);
                    m += d;
                }
            }
        }
        return m;
    }

    public boolean isGoal() {
        return bGoal;
    }

    public Board twin() {

        int[][] twinTiles = copyTiles();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N-1; j++) {
                if (twinTiles[i][j] != 0 && twinTiles[i][j+1] != 0) {
                    exch(twinTiles, i, j, i, j+1);
                    return new Board(twinTiles);
                }
            }
        }
        return null;
    }

    private int goalValueAt(int i, int j) {
        if (isEnd(i, j)) {
            return 0;
        }
        return 1 + i * N + j;
    }

    private boolean isEnd(int i, int j) {
        return (i == N - 1) && (j == N -1);
    }

    private void exch(int[][] arr, int i, int j, int it, int jt) {
        int temp = arr[i][j];
        arr[i][j] = arr[it][jt];
        arr[it][jt] = temp;
    }

    private int[][] copyTiles() {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (this == y) {
            return true;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (this.N != that.N) {
            return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    public Iterable<Board> neighbors() {
        int i0 = 0;
        int j0 = 0;
        ArrayList<Board> boards = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                    break;
                }
            }
        }

        if (i0 > 0) {
            int[][] up = copyTiles();
            exch(up, i0, j0, i0 - 1, j0);
            boards.add(new Board(up));
        }
        if (i0 < N-1) {
            int[][] down = copyTiles();
            exch(down, i0, j0, i0 + 1, j0);
            boards.add(new Board(down));
        }
        if (j0 > 0) {
            int[][] left = copyTiles();
            exch(left, i0, j0, i0, j0 - 1);
            boards.add(new Board(left));
        }
        if (j0 < N-1) {
            int[][] right = copyTiles();
            exch(right, i0, j0, i0, j0 + 1);
            boards.add(new Board(right));
        }

        return boards;
    }



    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(String.format("%2d ", tiles[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
    }

}
