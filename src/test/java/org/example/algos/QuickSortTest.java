package org.example.algos;

import org.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test
    void randomAndAdversarial() {
        Random rnd = new Random(2);
        for (int t = 0; t < 20; t++) {
            int n = 1 + rnd.nextInt(50_000);
            int[] a = rnd.ints(n, -1_000_000, 1_000_000).toArray();
            int[] b = a.clone();
            Metrics m = new Metrics();
            QuickSort.sort(a, m);
            Arrays.sort(b);
            assertArrayEquals(b, a);
            int bound = (int)(2 * (Math.log(Math.max(1, n))/Math.log(2))) + 20;
            assertTrue(m.maxDepth <= bound);
        }

        int[] c = new int[25_000];
        for (int i = 0; i < c.length; i++) c[i] = i;
        int[] d = c.clone();
        Metrics m2 = new Metrics();
        QuickSort.sort(c, m2);
        Arrays.sort(d);
        assertArrayEquals(d, c);
    }
}
