package org.example.algos;

import org.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SelectTest {
    @Test
    void compareWithSort() {
        Random rnd = new Random(3);
        for (int t = 0; t < 50; t++) {
            int n = 1 + rnd.nextInt(50_000);
            int[] a = rnd.ints(n, -1_000_000, 1_000_000).toArray();
            int[] b = a.clone();
            Arrays.sort(b);
            int k = rnd.nextInt(n);
            Metrics m = new Metrics();
            int x = Select.kth(a, k, m);
            assertEquals(b[k], x);
        }
    }
}
