package com.example.canvassort;

import javafx.scene.paint.Color;

import java.util.List;
public interface SortStrategy {
    void sort(int[] array, ArrayUpdateNotifier notifier) throws InterruptedException;

}
