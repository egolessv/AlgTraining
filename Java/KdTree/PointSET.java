import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by egoless on 15/11/13.
 */
public class PointSET {

    private SET<Point2D> mPoints;

    public PointSET() {
        mPoints = new SET<>();
    }

    public boolean isEmpty() {
        return mPoints.isEmpty();
    }

    public int size() {
        return mPoints.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        mPoints.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return mPoints.contains(p);
    }

    public void draw() {
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Point2D p : mPoints) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        ArrayList<Point2D> res = new ArrayList<>();
        //StdOut.println(rect.xmin() + " " + rect.xmax() + " " + rect.ymin() + " " + rect.ymax());
        for (Point2D p : mPoints) {
            //StdOut.println(p.x() + " " + p.y());
            if (rect.contains(p)) {
                res.add(p);
            }
        }
        return res;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        double minDis = Double.POSITIVE_INFINITY;
        Point2D res = null;
        for (Point2D q : mPoints) {
            double dis = q.distanceTo(p);
            if (minDis > dis) {
                minDis = dis;
                res = q;
            }
        }
        return res;
    }

    public static void main(String[] args) {

    }


}
