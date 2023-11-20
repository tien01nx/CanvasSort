package com.example.canvassort.Interfaces;

import com.example.canvassort.Implements.ArrayUpdateNotifier;
import com.example.canvassort.Implements.SortStrategy;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class MergeSort implements SortStrategy {
    @Override
    public void sort(int[] array, ArrayUpdateNotifier notifier) throws InterruptedException {
        int[] aux = array.clone();
        mergeSort(array, aux, 0, array.length - 1, notifier);
    }
    private void mergeSort(int[] a, int[] aux, int L, int R, ArrayUpdateNotifier updateNotifier) throws InterruptedException {
        if (L >= R) return;
        int mid = (L + R) / 2;
        mergeSort(aux, a, L, mid, updateNotifier);
        mergeSort(aux, a, mid + 1, R, updateNotifier);
        merge(a, aux, L, mid, R, updateNotifier);
    }
    private void merge(int[] a, int[] aux, int L, int mid, int R, ArrayUpdateNotifier updateNotifier) throws InterruptedException {
        int i = L, j = mid + 1, k = L;
        while (i <= mid && j <= R) {
            if (aux[i] <= aux[j]) {
                a[k++] = aux[i++];
            } else {
                a[k++] = aux[j++];
            }
            updateNotifier.update(a.clone(), Arrays.asList(i, j), Color.RED);
            Thread.sleep(500);
        }
        while (i <= mid) {
            a[k++] = aux[i++];
            updateNotifier.update(a.clone(), Arrays.asList(i), Color.RED);
            Thread.sleep(500);
        }
        while (j <= R) {
            a[k++] = aux[j++];
            updateNotifier.update(a.clone(), Arrays.asList(j), Color.RED);
            Thread.sleep(500);
        }
    }


//    private int[] mergeSort(int[] a, int L, int R) {
//        if (L > R) return new int[0];
//        if (L == R) {
//            return new int[] { a[L] };
//        }
//        int mid = (L + R) / 2;
//        int[] a1 = mergeSort(a, L, mid);
//        int[] a2 = mergeSort(a, mid + 1, R);
//        return merge(a1, a2);
//    }
//
//    private int[] merge(int[] a1, int[] a2) {
//        int n = a1.length + a2.length;
//
//        int[] result = new int[n];
//        int i = 0, i1 = 0, i2 = 0;
//        while (i < n) {
//            if (i1 < a1.length && i2 < a2.length) {
//                if (a1[i1] <= a2[i2]) {
//                    result[i] = a1[i1];
//                    i++;
//                    i1++;
//                } else {
//                    result[i] = a2[i2];
//                    i++;
//                    i2++;
//                }
//
//            } else {
//                if (i1 < a1.length) {
//                    result[i] = a1[i1];
//                    i++;
//                    i1++;
//                } else {
//                    result[i] = a2[i2];
//                    i++;
//                    i2++;
//                }
//            }
//
//        }
//        return result;
//    }

}
