package com.example.canvassort;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.*;

import static com.example.canvassort.Visualization.*;

public class Main {

    @FXML
    private Canvas canvas;
    private int[] array;
    @FXML
    private TextField countTextField;
    private Thread sortingThread;
    private GraphicsContext gc;
    private Visualization visualization;
    @FXML
    private void huy(ActionEvent event) {
        if (sortingThread != null && sortingThread.isAlive()) {
            sortingThread.interrupt();
        }
    }
    private void initializeGraphicsContext() {
        if (gc == null) {
            gc = canvas.getGraphicsContext2D();
            visualization = new Visualization(gc);
        }
    }
    @FXML
    private void loadDataFromFile(ActionEvent event) {
        List<int[]> arrays = new DataHandler().readFromFile();
        if (arrays.size() > 0) {
            array = arrays.get(0);
            canvas.setWidth(array.length * (RECT_WIDTH + SPACING));
            canvas.setHeight(CANVAS_HEIGHT);
            initializeGraphicsContext();
            visualization.drawArray(array);
        } else {
            showAlert("Error", "Định dạng file không hợp lệ");
        }
    }
    @FXML
    private void saveDataToFile(ActionEvent event) {
        if (array == null || array.length == 0) {
            showAlert("Error", "Không có dữ liệu để lưu.");
            return;
        }
        new DataHandler().saveToFile(array);
    }
    // Hàm tạo dữ liệu ngẫu nhiên và dữ liệu nằm trong khoảng 0 dến 100
    @FXML
    private void generateRandomNumbers(ActionEvent event) {
        try {
            int count = Integer.parseInt(countTextField.getText());
            canvas.setWidth(count * (RECT_WIDTH + SPACING));
            canvas.setHeight(CANVAS_HEIGHT);
            initializeGraphicsContext();
            array = new DataHandler().generateRandomData(count, 250);
            System.out.println(Arrays.toString(array));
            visualization.drawArray(array);
        } catch (NumberFormatException e) {
            showAlert("Error", "Vui lòng nhập một số hợp lệ.");
        }
    }
    // Hiện thị alert thông báo ra màn hình
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void startMergeSortSimulation() {
        startSorting(new MergeSort());
    }
    // quick sort
    @FXML
    public void startQuickSortSimulation() {
        startSorting(new QuickSort());
    }
    // bubble sort
    @FXML
    public void startBubbleSortSimulation() {
        startSorting(new BubbleSort());
    }

    private void startSorting(SortStrategy sortStrategy) {
        if (array == null || array.length == 0) {
            showAlert("Error", "Không có dữ liệu để sắp xếp.");
            return;
        }
        if (sortingThread != null && sortingThread.isAlive()) {
            sortingThread.interrupt();
        }
        sortingThread = new Thread(() -> {
            try {
                sortStrategy.sort(array, (arr, indices, color) ->
                        Platform.runLater(() -> visualization.drawArray(arr, indices, color)));
                Platform.runLater(() -> visualization.drawArray(array, Collections.emptyList(), Color.BLUE));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        sortingThread.start();
    }
}

