package com.example.canvassort;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class HelloController {

    @FXML
    private Canvas canvas;

    private static final int ARRAY_SIZE = 8;
    private static final int SPACING = 5; // Khoảng cách giữa các cột
    private static final int RECT_WIDTH = 15 + SPACING; // Độ rộng của mỗi cột, đã tính cả khoảng cách
    private static final int CANVAS_WIDTH = ARRAY_SIZE * (RECT_WIDTH + SPACING);
    private static final int CANVAS_HEIGHT = 400;
    private int[] array;
    //    private int[] array = new int[ARRAY_SIZE];
    @FXML
    private TextField countTextField;
    private GraphicsContext gc;


    @FXML
    private void generateRandomNumbers(ActionEvent event) {
        try {
            int count = Integer.parseInt(countTextField.getText());
            canvas.setWidth(count * (RECT_WIDTH + SPACING));
            canvas.setHeight(CANVAS_HEIGHT);

//            gc = canvas.getGraphicsContext2D();
            if (gc == null) {
                gc = canvas.getGraphicsContext2D();
            }

            // Xóa nội dung trên Canvas
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            array = new int[count];
            for (int i = 0; i < count; i++) {
                array[i] = (int) (Math.random() * 101);
            }
            System.out.println(Arrays.toString(array));

            drawArray(array);
        } catch (NumberFormatException e) {
            showAlert("Error", "Vui lòng nhập một số hợp lệ.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void startSimulation() {
        new Thread(() -> {
            try {
                mergeSort(array, 0, array.length - 1);
                System.out.println("Sau khi sắp xếp: \n" +
                        Arrays.toString(array));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //    private void drawArray(int[] arr) {
////        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        for (int i = 0; i < arr.length; i++) {
//            // Vẽ cột với màu xanh
//            gc.setFill(Color.BLUE);
//            gc.fillRect(i * RECT_WIDTH, CANVAS_HEIGHT - arr[i], RECT_WIDTH - SPACING, arr[i]);
//
//            // Vẽ giá trị của cột
//            gc.setFill(Color.BLACK);
//            gc.fillText(String.valueOf(arr[i]), i * RECT_WIDTH, CANVAS_HEIGHT - arr[i] - 5);
//        }
//    }
    private void drawArray(int[] arr) {
        drawArray(arr, Collections.emptyList(), Color.BLUE);
    }

    private void drawArray(int[] arr, List<Integer> indices, Color color) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < arr.length; i++) {
            if (indices.contains(i)) {
                gc.setFill(color);
            } else {
                gc.setFill(Color.BLUE);
            }
            gc.fillRect(i * RECT_WIDTH, CANVAS_HEIGHT - arr[i], RECT_WIDTH - SPACING, arr[i]);

            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(arr[i]), i * RECT_WIDTH, CANVAS_HEIGHT - arr[i] - 5);
        }
    }


    private void mergeSort(int[] arr, int l, int r) throws InterruptedException {
        if (l < r) {
            int m = l + (r - l) / 2;

            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            merge(arr, l, m, r);

            Thread.sleep(500);
            drawArray(arr);
        }
    }

//    private void merge(int[] arr, int l, int m, int r) {
//        int n1 = m - l + 1;
//        int n2 = r - m;
//
//        int[] L = new int[n1];
//        int[] R = new int[n2];
//
//        for (int i = 0; i < n1; i++) {
//            L[i] = arr[l + i];
//        }
//        for (int j = 0; j < n2; j++) {
//            R[j] = arr[m + 1 + j];
//        }
//
//        int i = 0, j = 0;
//
//        int k = l;
//        while (i < n1 && j < n2) {
//            if (L[i] <= R[j]) {
//                arr[k] = L[i];
//                i++;
//            } else {
//                arr[k] = R[j];
//                j++;
//            }
//            k++;
//        }
//
//        while (i < n1) {
//            arr[k] = L[i];
//            i++;
//            k++;
//        }
//
//        while (j < n2) {
//            arr[k] = R[j];
//            j++;
//            k++;
//        }
////        drawArray(arr);
//        Platform.runLater(() -> {
//            drawArray(arr);
//        });
//    }

    private void merge(int[] arr, int l, int m, int r) throws InterruptedException {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0;

        int k = l;
        List<Integer> indices = new ArrayList<>();

        while (i < n1 && j < n2) {
            indices.clear();
            indices.add(l + i);
            indices.add(m + 1 + j);
            Platform.runLater(() -> {
                drawArray(arr, indices, Color.RED); // Màu đỏ khi so sánh
            });
            Thread.sleep(1000);  // Dừng một lúc để hiển thị so sánh

            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;

            Platform.runLater(() -> {
                drawArray(arr, indices, Color.BLACK); // Màu đen sau khi gộp
            });
            Thread.sleep(1000); // Dừng một lúc sau khi gộp
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

}

