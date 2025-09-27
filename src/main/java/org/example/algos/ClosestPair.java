package org.example.algos;

import org.example.util.Metrics;
import java.util.*;

public class ClosestPair {
    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double solve(List<Point> pts, Metrics m) {
        if (pts == null || pts.size() < 2) return 0.0;
        if (m != null) m.startTimer();

        int n = pts.size();
        Point[] px = pts.toArray(new Point[0]);
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));

        Point[] py = Arrays.copyOf(px, n);
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));

        Point[] buf = new Point[n];
        double ans = rec(px, 0, n, py, buf, m);

        if (m != null) m.stopTimer();
        return ans;
    }

    private static double rec(Point[] px, int l, int r, Point[] py, Point[] buf, Metrics m) {
        final int n = py.length;
        if (n <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (m != null) m.cmp();
                    best = Math.min(best, dist(py[i], py[j]));
                }
            }
            return best;
        }

        final int mid = (l + r) >>> 1;
        final double midX = px[mid].x;

        HashSet<Point> leftSet = new HashSet<>(mid - l);
        for (int i = l; i < mid; i++) leftSet.add(px[i]);

        Point[] pyL = new Point[mid - l];
        Point[] pyR = new Point[r - mid];
        int li = 0, ri = 0;
        for (Point p : py) {
            if (leftSet.contains(p)) pyL[li++] = p;
            else                     pyR[ri++] = p;
        }
        if (li != pyL.length || ri != pyR.length) {
            ArrayList<Point> L = new ArrayList<>(pyL.length);
            ArrayList<Point> R = new ArrayList<>(pyR.length);
            for (Point p : py) if (leftSet.contains(p)) L.add(p); else R.add(p);
            pyL = L.toArray(new Point[0]);
            pyR = R.toArray(new Point[0]);
        }

        double dl = rec(px, l,   mid, pyL, buf, m);
        double dr = rec(px, mid, r,   pyR, buf, m);
        double d  = Math.min(dl, dr);

        int iL = 0, iR = 0, k = 0;
        while (iL < pyL.length && iR < pyR.length) {
            if (pyL[iL].y <= pyR[iR].y) py[k++] = pyL[iL++];
            else                         py[k++] = pyR[iR++];
        }
        while (iL < pyL.length) py[k++] = pyL[iL++];
        while (iR < pyR.length) py[k++] = pyR[iR++];

        int s = 0;
        for (int i = 0; i < n; i++) {
            if (Math.abs(py[i].x - midX) < d) buf[s++] = py[i];
        }
        for (int i = 0; i < s; i++) {
            for (int j = i + 1; j < s && (buf[j].y - buf[i].y) < d && j <= i + 7; j++) {
                if (m != null) m.cmp();
                d = Math.min(d, dist(buf[i], buf[j]));
            }
        }
        return d;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }
}
