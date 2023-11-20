package com.example.canvassort.Interfaces;

import com.example.canvassort.Implements.ArrayUpdateNotifier;
import com.example.canvassort.Implements.SortStrategy;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class QuickSort implements SortStrategy {
    @Override
    public void sort(int[] array, ArrayUpdateNotifier updateNotifier) throws InterruptedException {
        quicksort(array, 0, array.length - 1, updateNotifier);
    }
    private void quicksort(int[] arr, int L, int R, ArrayUpdateNotifier updateNotifier) throws InterruptedException {
        if (L >= R) return;
        if (Thread.currentThread().isInterrupted()) return;
        int pivotIndex = (L + R) / 2;
        int pivotValue = arr[pivotIndex];
        updateNotifier.update(Arrays.copyOf(arr, arr.length), Arrays.asList(pivotIndex), Color.BLACK); // Đánh dấu pivot
        Thread.sleep(500); // Độ trễ
        System.out.println("L= " + L + " R= " + R + " key = " + pivotIndex + " k = " + pivotValue);
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, L, R + 1)));
        System.out.println("___________________");
        int partitionIndex = partition(arr, L, R, pivotValue, updateNotifier);
        quicksort(arr, L, partitionIndex - 1, updateNotifier);
        quicksort(arr, partitionIndex, R, updateNotifier);
    }
    private int partition(int[] arr, int L, int R, int pivot, ArrayUpdateNotifier updateNotifier) throws InterruptedException {
        int i = L, j = R;
        while (i <= j) {
            while (arr[i] < pivot) i++;
            while (arr[j] > pivot) j--;

            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                updateNotifier.update(Arrays.copyOf(arr, arr.length), Arrays.asList(i, j), Color.RED); // Đánh dấu đổi chỗ
                Thread.sleep(500); // Độ trễ
                i++;
                j--;
            }
        }
        return i;
    }
}
