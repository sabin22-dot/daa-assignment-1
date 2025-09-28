package org.example.algos;

import org.example.util.Metrics;
import java.util.Arrays;

public class MergeSort {
    private static final int CUTOFF = 32;

    public static void sort(int[] a, Metrics m) {
        m.startTimer();
        int[] buf = new int[a.length];
        m.alloc(a.length);
        m.incDepth();
        sortRec(a, 0, a.length-1, buf, m);
        m.decDepth();
        m.stopTimer();
    }
// Stable merge sort with reusable buffer and small-N cutoff (InsertionSort)

    private static void sortRec(int[] a, int lo, int hi, int[] buf, Metrics m) {
        if (hi - lo + 1 <= CUTOFF) {
            InsertionSort.sort(a, lo, hi, m);
            return;
        }
        int mid = (lo + hi) >>> 1;
        m.incDepth();
        sortRec(a, lo, mid, buf, m);
        sortRec(a, mid+1, hi, buf, m);
        m.decDepth();
        if (a[mid] <= a[mid+1]) return; // already sorted
        merge(a, lo, mid, hi, buf, m);
    }

    private static void merge(int[] a, int lo, int mid, int hi, int[] buf, Metrics m) {
        int n = hi - lo + 1;
        System.arraycopy(a, lo, buf, lo, n);
        m.move(); // single bulk copy counted once
        int i = lo, j = mid+1, k = lo;
        while (i <= mid && j <= hi) {
            m.cmp();
            if (buf[i] <= buf[j]) {
                a[k++] = buf[i++]; m.move();
            } else {
                a[k++] = buf[j++]; m.move();
            }
        }
        while (i <= mid) { a[k++] = buf[i++]; m.move(); }
        while (j <= hi)  { a[k++] = buf[j++]; m.move(); }
    }
}
