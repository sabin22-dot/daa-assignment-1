package org.example.algos;

import org.example.util.Metrics;
import java.util.Random;

public class Select {
    private static final Random RND = new Random(1);
    public static int kth(int[] a, int k, Metrics m) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        if (m != null) m.startTimer();
        int res = quickselect(a.clone(), 0, a.length - 1, k, m);
        if (m != null) m.stopTimer();
        return res;
    }
    public static int select(int[] a, int k, Metrics m) {
        return kth(a, k, m);
    }

    private static int quickselect(int[] a, int lo, int hi, int k, Metrics m) {
        while (lo <= hi) {
            int p = partition(a, lo, hi, m);
            if (p == k) return a[p];
            if (k < p) hi = p - 1;
            else lo = p + 1;
        }
        return a[lo];
    }

    private static int partition(int[] a, int lo, int hi, Metrics m) {
        int pivotIdx = lo + RND.nextInt(hi - lo + 1);
        swap(a, pivotIdx, hi);
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (m != null) m.cmp();
            if (a[j] <= pivot) swap(a, i++, j);
        }
        swap(a, i, hi);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
