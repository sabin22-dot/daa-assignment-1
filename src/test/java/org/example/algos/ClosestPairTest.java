package org.example.algos;

import org.example.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {
    private static double brute(List<ClosestPair.Point> pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.size(); i++) {
            for (int j = i + 1; j < pts.size(); j++) {
                ClosestPair.Point a = pts.get(i), b = pts.get(j);
                double dx = a.x - b.x, dy = a.y - b.y;
                best = Math.min(best, Math.hypot(dx, dy));
            }
        }
        return best;
    }

    @Test
    void smallMatchesBruteForce() {
        Random rnd = new Random(1);
        ArrayList<ClosestPair.Point> pts = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            pts.add(new ClosestPair.Point(rnd.nextDouble() * 1000.0, rnd.nextDouble() * 1000.0));
        }
        Metrics m = new Metrics();
        double d1 = ClosestPair.solve(pts, m);
        double d2 = brute(pts);
        assertEquals(d2, d1, 1e-9);
    }
}
