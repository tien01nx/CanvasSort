package com.example.canvassort.Implements;

public interface SortStrategy {
    void sort(int[] array, ArrayUpdateNotifier notifier) throws InterruptedException;
}
