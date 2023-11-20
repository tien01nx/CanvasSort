package com.example.canvassort;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.List;

public class Visualization {
    public static final int SPACING = 5; // Khoảng cách giữa các cột
    public static final int RECT_WIDTH = 15 + SPACING; // Độ rộng của mỗi cột, đã tính cả khoảng cách
    public static final int CANVAS_HEIGHT = 400; // Chiều cao của canvas
    private GraphicsContext gc;
    public Visualization(GraphicsContext gc) {
        this.gc = gc;
    }
    public void drawArray(int[] arr) {
        drawArray(arr, Collections.emptyList(), Color.BLUE);
    }
    public void drawArray(int[] arr, List<Integer> indices, Color color) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (int i = 0; i < arr.length; i++) {
            if (indices.contains(i)) {
                gc.setFill(color); // Sử dụng màu được chỉ định
            } else {
                gc.setFill(Color.BLUE); // Màu mặc định cho các phần tử chưa được chọn
            }
            gc.fillRect(i * (RECT_WIDTH + SPACING), CANVAS_HEIGHT - arr[i], RECT_WIDTH, arr[i]);
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(arr[i]), i * (RECT_WIDTH + SPACING), CANVAS_HEIGHT - arr[i] - 5);
        }
    }

}
