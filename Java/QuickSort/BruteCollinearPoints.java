import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by egoless on 10/20/15.
 */
public class BruteCollinearPoints {

    private ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
    private LineSegment[] mLineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        ArrayList<Point> checkSet = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
            for (int j = 0; j < checkSet.size(); j++) {
                if (points[i].compareTo(checkSet.get(j)) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            checkSet.add(points[i]);
        }

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        for (int i = 0; i < pointsCopy.length; i++) {
            for (int j = i+1; j < pointsCopy.length; j++) {
                for (int k = j+1; k < pointsCopy.length; k++) {
                    for (int l = k+1; l < pointsCopy.length; l++) {
                        if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[k])
                                && pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[l])) {
                            lines.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
                        }
                    }
                }
            }
        }

        mLineSegments = new LineSegment[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            mLineSegments[i] = lines.get(i);
        }
    }

    public int numberOfSegments() {
        return mLineSegments.length;
    }

    public LineSegment[] segments() {
        return mLineSegments.clone();
    }

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        LineSegment[] a = collinear.segments();
        LineSegment tmp =  a[0];
        a[0] = a[a.length-1];
        a[a.length-1] = tmp;
        StdOut.println(">>>>>>>>>>>>>>>>>>>>");
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }

}
