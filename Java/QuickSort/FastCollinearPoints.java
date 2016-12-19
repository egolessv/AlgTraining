
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by egoless on 10/20/15.
 */
public class FastCollinearPoints {

    private LineSegment[] mLineSegments;

    private class Segment {
        private Point p;
        private Point q;
    }

    public FastCollinearPoints(Point[] points) {
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

        ArrayList<Point> pSet = new ArrayList<Point>();
        ArrayList<Segment> pLines = new ArrayList<Segment>();

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] cPoints = points.clone();
            Arrays.sort(cPoints, p.slopeOrder());
            int last = -1;
            int count = 0;
            for (int j = 0; j < cPoints.length; j++) {
                if (last != -1 && p.slopeTo(cPoints[j]) == p.slopeTo(cPoints[last])) {
                    count++;
                    if (count == 2) {
                        pSet.add(p);
                        pSet.add(cPoints[j]);
                        pSet.add(cPoints[j-1]);
                        pSet.add(cPoints[j-2]);
                    } else if (count > 2) {
                        pSet.add(cPoints[j]);
                    }
                } else {
                    if (pSet.size() != 0) {
                        Segment s = handleCollinear(pSet);
                        if (s != null) {
                            pLines.add(s);
                        }
                        pSet.clear();
                    }
                    count = 0;
                }
                last = j;
            }
            if (pSet.size() != 0) {
                Segment s = handleCollinear(pSet);
                if (s != null) {
                    pLines.add(s);
                }
                pSet.clear();
            }
        }

        // store to line segment array
        mLineSegments = new LineSegment[pLines.size()];
        for (int i = 0; i < pLines.size(); i++) {
            Segment s = pLines.get(i);
            mLineSegments[i] = new LineSegment(s.p, s.q);
        }

    }

    public int numberOfSegments() {
        return mLineSegments.length;
    }

    public LineSegment[] segments() {
        return mLineSegments.clone();
    }

    private Segment handleCollinear(ArrayList<Point> pl) {
        Point p = pl.get(0);
        Point pMax = p;
        Point pMin = p;
        boolean bAdd = true;
        for (int i = 1; i < pl.size(); i++) {
            if (p.compareTo(pl.get(i)) < 0) {
                bAdd = false;
                break;
            }
            if (pMax.compareTo(pl.get(i)) < 0) {
                pMax = pl.get(i);
            }
            if (pMin.compareTo(pl.get(i)) > 0) {
                pMin = pl.get(i);
            }
        }
        if (bAdd) {
            Segment s = new Segment();
            s.p = pMin;
            s.q = pMax;
            return s;
        }
        return null;
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

        /* draw the points */
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
        //    StdOut.println(segment);
            segment.draw();
        }
    }


}
