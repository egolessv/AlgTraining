import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by egoless on 15/11/13.
 */
public class KdTree {

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private class Node {
        private Point2D p;
        private Node left;
        private Node right;
        private boolean dir;
    }

    private Node mRoot = null;
    private int size = 0;

    public KdTree() {

    }

    public boolean isEmpty() {
        return mRoot == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (find(mRoot, p)) return;
        Node node = new Node();
        node.p = p;
        mRoot = add(mRoot, p, VERTICAL);
        size++;
    }

    private Node add(Node root, Point2D p, boolean dir) {
        if (root == null) {
            Node node = new Node();
            node.p = p;
            node.dir = dir;
            return node;
        }
        if (dir == VERTICAL) {
            if (p.x() < root.p.x()) {
                root.left = add(root.left, p, !dir);
            } else {
                root.right = add(root.right, p, !dir);
            }
        } else if (dir == HORIZONTAL) {
            if (p.y() < root.p.y()) {
                root.left = add(root.left, p, !dir);
            } else {
                root.right = add(root.right, p, !dir);
            }
        }
        return root;
    }

    private  boolean find(Node root, Point2D p) {
        if (root == null) {
            return false;
        }
        if (root.p.equals(p)) {
            return true;
        }
        if (root.dir == VERTICAL) {
            if (root.p.x() > p.x()) {
                return find(root.left, p);
            } else {
                return find(root.right, p);
            }
        } else {
            if (root.p.y() > p.y()) {
                return find(root.left, p);
            } else {
                return find(root.right, p);
            }
        }
    }


    public boolean contains(Point2D p) {
        if (p == null) throw  new NullPointerException();
        return find(mRoot, p);
    }

    public void draw() {
        //printTree(mRoot);
        drawNode(mRoot, 0.0, 1.0, 0.0, 1.0);
    }

    private void printTree(Node root) {
        if (root == null) return;
        StdOut.println("print tree " + root.p.x() + " " + root.p.y() + " " + root.dir);
        printTree(root.left);
        printTree(root.right);
    }

    private void drawNode(Node root, double startX, double endX, double startY, double endY) {
        if (root == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(root.p.x(), root.p.y());
        StdDraw.setPenRadius(0.01);
        if (root.dir == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(root.p.x(), startY, root.p.x(), endY);
            drawNode(root.left, startX, root.p.x(), startY, endY);
            drawNode(root.right, root.p.x(), endX, startY, endY);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(startX, root.p.y(), endX, root.p.y());
            drawNode(root.left, startX, endX, startY, root.p.y());
            drawNode(root.right, startX, endX, root.p.y(), endY);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        ArrayList<Point2D> arr = new ArrayList<>();
        search(arr, rect, mRoot, 0.0, 1.0, 0.0, 1.0);
        return arr;
    }

    private void search(ArrayList<Point2D> arr, RectHV rect, Node root,
            double startX, double endX, double startY, double endY) {
        if (root == null) {
            return;
        }
        RectHV hv = new RectHV(startX, startY, endX, endY);
        if (!rect.intersects(hv)) return;
        if (rect.contains(root.p)) arr.add(root.p);
        if (root.dir == VERTICAL) {
            search(arr, rect, root.left, startX, root.p.x(), startY, endY);
            search(arr, rect, root.right, root.p.x(), endX, startY, endY);
        } else {
            search(arr, rect, root.left, startX, endX, startY, root.p.y());
            search(arr, rect, root.right, startX, endX, root.p.y(), endY);
        }
    }



    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (mRoot == null) return null;
        Node best = new Node();
        best.p = mRoot.p;
        findNearest(best, p, mRoot, 0.0, 1.0, 0.0, 1.0);
        return best.p;
    }

    private void findNearest(Node best, Point2D p, Node root, double startX, double endX, double startY, double endY) {
        if (root == null) return;
        RectHV hv = new RectHV(startX, startY, endX, endY);
        if (hv.distanceSquaredTo(p) > best.p.distanceSquaredTo(p)) return;
        if (root.p.distanceSquaredTo(p) < best.p.distanceSquaredTo(p)) {
            best.p = root.p;
        }
        if (root.dir == VERTICAL) {
            if (p.x() > root.p.x()) {
                findNearest(best, p, root.right, root.p.x(), endX, startY, endY);
                findNearest(best, p, root.left, startX, root.p.x(), startY, endY);
            } else {
                findNearest(best, p, root.left, startX, root.p.x(), startY, endY);
                findNearest(best, p, root.right, root.p.x(), endX, startY, endY);
            }
        } else {
            if (p.y() > root.p.y()) {
                findNearest(best, p, root.right, startX, endX, root.p.y(), endY);
                findNearest(best, p, root.left, startX, endX, startY, root.p.y());
            } else {
                findNearest(best, p, root.left, startX, endX, startY, root.p.y());
                findNearest(best, p, root.right, startX, endX, root.p.y(), endY);
            }
        }

    }



    public static void main(String[] args) {

    }
}
