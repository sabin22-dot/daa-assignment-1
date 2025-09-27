package org.example.util;

public class Metrics {
    public long comparisons;
    public long moves;
    public long allocations;
    public long nanos;
    public int depth;
    public int maxDepth;

    private long t0;

    public void cmp() { comparisons++; }
    public void move() { moves++; }
    public void alloc(int elements) { allocations += Math.max(0, elements); }

    public void startTimer() { t0 = System.nanoTime(); }
    public void stopTimer() { nanos += System.nanoTime() - t0; }

    public void incDepth() { depth++; if (depth > maxDepth) maxDepth = depth; }
    public void decDepth() { depth--; }
}
