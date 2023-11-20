package com.example.canvassort.Interfaces;

import javafx.scene.paint.Color;

import java.util.List;

public interface ArrayUpdateNotifier {
    void update(int[] arr, List<Integer> activeIndices, Color color);
}
