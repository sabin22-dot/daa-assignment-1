package org.example.bench;

import org.example.util.Csv;
import org.example.util.Metrics;
import org.example.algos.*;
import java.util.ArrayList;
import java.util.List;
import org.example.algos.ClosestPair;
import java.util.Random;


public class Runner {
    private static final int[] SIZES = {1_000, 5_000, 10_000, 50_000, 100_000, 200_000};

    public static void main(String[] args) throws Exception {
        String algo = args.length > 0 ? args[0].toLowerCase() : "all";
        String out  = args.length > 1 ? args[1] : "bench/results.csv";
        try (Csv csv = new Csv(out, "algo,n,ms,maxDepth,comparisons,allocs,seed")) {
            if (algo.equals("all") || algo.equals("mergesort")) benchSort(csv, "mergesort");
            if (algo.equals("all") || algo.equals("quicksort")) benchSort(csv, "quicksort");
            if (algo.equals("all") || algo.equals("select"))    benchSelect(csv);
            if (algo.equals("all") || algo.equals("closest"))   benchClosest(csv);
        }
        System.out.println("Wrote results to " + out);
    }

    private static void benchSort(Csv csv, String which) {
        Random seedRnd = new Random(1);
        for (int n : SIZES) {
            long seed = seedRnd.nextLong();
            Random rnd = new Random(seed);
            int[] a = rnd.ints(n, -1_000_000, 1_000_000).toArray();
            int[] b = a.clone();

            Metrics m = new Metrics();
            long t0 = System.nanoTime();
            if (which.equals("mergesort")) MergeSort.sort(a, m);
            else                           QuickSort.sort(a, m);
            long ms = Math.round((System.nanoTime() - t0) / 1e6);

            java.util.Arrays.sort(b);
            if (!java.util.Arrays.equals(a, b)) throw new AssertionError(which + " failed");

            csv.row(which, n, ms, m.maxDepth, field(m,"comparisons"), field(m,"allocs"), seed);
        }
    }

    private static void benchSelect(Csv csv) {
        Random seedRnd = new Random(2);
        for (int n : SIZES) {
            long seed = seedRnd.nextLong();
            Random rnd = new Random(seed);
            int[] a = rnd.ints(n, -1_000_000, 1_000_000).toArray();
            int k = rnd.nextInt(n);
            Metrics m = new Metrics();

            long t0 = System.nanoTime();
            int kth = Select.kth(a, k, m);
            long ms = Math.round((System.nanoTime() - t0) / 1e6);

            int[] b = a.clone(); java.util.Arrays.sort(b);
            if (kth != b[k]) throw new AssertionError("select failed");

            csv.row("select", n, ms, m.maxDepth, field(m,"comparisons"), field(m,"allocs"), seed);
        }
    }

    private static void benchClosest(Csv csv) {
        Random seedRnd = new Random(3);
        for (int n : SIZES) {
            long seed = seedRnd.nextLong();
            Random rnd = new Random(seed);

            // строим список точек нужного типа
            List<ClosestPair.Point> pts = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                double x = rnd.nextDouble() * 2e6 - 1e6;
                double y = rnd.nextDouble() * 2e6 - 1e6;
                pts.add(new ClosestPair.Point(x, y)); // конструктор (x,y)
            }

            Metrics m = new Metrics();
            long t0 = System.nanoTime();
            double d = ClosestPair.solve(pts, m);
            long ms = Math.round((System.nanoTime() - t0) / 1e6);

            if (!(d > 0)) throw new AssertionError("closest pair failed");

            csv.row("closest", n, ms, m.maxDepth, field(m, "comparisons"), field(m, "allocs"), seed);
        }
    }

    private static long field(Metrics m, String name) {
        try {
            var f = m.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.getLong(m);
        } catch (Exception e) {
            return -1;
        }
    }
}

