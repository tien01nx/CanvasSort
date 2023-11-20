package com.example.canvassort;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Collections;

public class BubbleSort  implements SortStrategy{
    @Override
    public void sort(int[] array, ArrayUpdateNotifier notifier) throws InterruptedException {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                    notifier.update(Arrays.copyOf(array, array.length), Arrays.asList(j, j + 1), Color.RED);
                    Thread.sleep(500); // Độ trễ để người dùng theo dõi
                }
            }
            notifier.update(Arrays.copyOf(array, array.length), Collections.emptyList(), Color.BLUE);
        }
    }
}
