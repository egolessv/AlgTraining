import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by egoless on 2015/11/7.
 */
public class Solver {

    private boolean bSolvable;
    private Stack<Board> mPath = new Stack<>();

    private class Node implements Comparable<Node> {

        private Board board;
        private int moves;
        private Node prev;
        private int cachedPriority = -1;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        private int priority() {
            if (cachedPriority == -1) {
                cachedPriority = moves + board.manhattan();
            }
            return cachedPriority;
        }

        @Override
        public int compareTo(Node another) {
            if (this.priority() > another.priority()) {
                return 1;
            }
            if (this.priority() < another.priority()) {
                return -1;
            }
            return 0;
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        int moves = 0;
        MinPQ<Node> orgQueue = new MinPQ<>();
        MinPQ<Node> twinQueue = new MinPQ<>();
        Node initialNode = new Node(initial, 0, null);
        Node twinNode = new Node(initial.twin(), 0, null);
        orgQueue.insert(initialNode);
        twinQueue.insert(twinNode);

        Node orgCur = null;
        Node twinCur = null;

        while (orgQueue.size() > 0 && twinQueue.size() > 0) {
            orgCur = orgQueue.delMin();
            twinCur = twinQueue.delMin();
            if (orgCur.board.isGoal()) {
                bSolvable = true;
                break;
            } else if (twinCur.board.isGoal()) {
                bSolvable = false;
                break;
            }

            for (Board b : orgCur.board.neighbors()) {
                if (orgCur.prev == null || !b.equals(orgCur.prev.board)) {
                    orgQueue.insert(new Node(b, orgCur.moves+1, orgCur));
                }
            }
            for (Board b : twinCur.board.neighbors()) {
                if (twinCur.prev == null || !b.equals(twinCur.prev.board)) {
                    twinQueue.insert(new Node(b, twinCur.moves+1, twinCur));
                }
            }
        }

        if (bSolvable) {
            while (orgCur != null) {
                mPath.push(orgCur.board);
                orgCur = orgCur.prev;
            }
        }
    }

    public boolean isSolvable() {
        return bSolvable;
    }

    public int moves() {
        return mPath.size() - 1;
    }

    public Iterable<Board> solution() {
        if (bSolvable) {
            return mPath;
        }
        return null;
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
