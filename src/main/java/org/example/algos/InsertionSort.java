package org.example.algos;

import org.example.util.Metrics;
public class InsertionSort {
    public static void sort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo+1; i <= hi; i++) {
            int x = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.cmp();
                if (a[j] <= x) break;
                a[j+1] = a[j];
                m.move();
                j--;
            }
            a[j+1] = x;
            m.move();
        }
    }
}
