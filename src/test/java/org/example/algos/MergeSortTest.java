package org.example.algos;

import org.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {
    @Test
    void randomArrays() {
        Random rnd = new Random(1);
        for (int t = 0; t < 30; t++) {
            int n = 1 + rnd.nextInt(100_000);
            int[] a = rnd.ints(n, -1_000_000, 1_000_000).toArray();
            int[] b = a.clone();
            Metrics m = new Metrics();
            MergeSort.sort(a, m);
            Arrays.sort(b);
            assertArrayEquals(b, a);
            assertTrue(m.maxDepth > 0);
        }
    }
}
