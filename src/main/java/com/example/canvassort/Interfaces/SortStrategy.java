package com.example.canvassort.Interfaces;

public interface SortStrategy {
    void sort(int[] array, ArrayUpdateNotifier notifier) throws InterruptedException;
}
