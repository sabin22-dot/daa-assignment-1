package org.example.algos;

import org.example.util.Metrics;
import java.util.Random;

public class QuickSort {
    private static final Random RND = new Random(1);
    private static final int CUTOFF = 24;

    public static void sort(int[] a, Metrics m) {
        m.startTimer();
        quick(a, 0, a.length-1, m);
        if (CUTOFF > 0) InsertionSort.sort(a, 0, a.length-1, m);
        m.stopTimer();
    }

    private static void quick(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) return;
            int p = partition(a, lo, hi, m);
            int left = p - lo, right = hi - p;
            // recurse into smaller side, iterate into larger
            if (left < right) {
                m.incDepth();
                quick(a, lo, p - 1, m);
                m.decDepth();
                lo = p + 1;
            } else {
                m.incDepth();
                quick(a, p + 1, hi, m);
                m.decDepth();
                hi = p - 1;
            }
        }
    }

    private static int partition(int[] a, int lo, int hi, Metrics m) {
        int pivotIdx = lo + RND.nextInt(hi - lo + 1);
        swap(a, pivotIdx, hi, m);
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            m.cmp();
            if (a[j] <= pivot) swap(a, i++, j, m);
        }
        swap(a, i, hi, m);
        return i;
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t; m.move(); m.move(); m.move();
    }
}
