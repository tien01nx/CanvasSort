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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class HelloController {

    @FXML
    private Canvas canvas;

    private static final int ARRAY_SIZE = 8;
    private static final int SPACING = 5; // Khoảng cách giữa các cột
    private static final int RECT_WIDTH = 15 + SPACING; // Độ rộng của mỗi cột, đã tính cả khoảng cách
    private static final int CANVAS_HEIGHT = 400;
    private int[] array;
    @FXML
    private TextField countTextField;
    private GraphicsContext gc;

    int count;
    @FXML
    private void loadDataFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Data File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            List<Integer> numbers = new ArrayList<>();
            try (Scanner scanner = new Scanner(selectedFile)) {
                while (scanner.hasNextInt()) {
                    numbers.add(scanner.nextInt());
                }
                array = numbers.stream().mapToInt(i -> i).toArray();
                if (gc == null) {
                    gc = canvas.getGraphicsContext2D();
                }
                drawArray(array);
            } catch (FileNotFoundException e) {
                showAlert("Error", "File not found.");
            }
        }
    }


    // Hàm tạo dữ liệu ngẫu nhiên và dữ liệu nằm trong khoảng 0 dến 100
    @FXML
    private void generateRandomNumbers(ActionEvent event) {
        try {
             count = Integer.parseInt(countTextField.getText());
            canvas.setWidth(count * (RECT_WIDTH + SPACING));
            canvas.setHeight(CANVAS_HEIGHT);

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

    // Hiện thị alert thông báo ra màn hình
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // hàm thực hiện chạy Merge sort

    // Hàm vẽ Canvas mặc định màu là màu xanh => BLUE
    private void drawArray(int[] arr) {
        drawArray(arr, Collections.emptyList(), Color.BLUE);
    }


    // Thiết lập các màu khi vẽ
    // BLUE mặc định và sắp xếp đúng
    // Black và RED là màu đang sắp xếp
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

    @FXML
    public void startSimulation() {
        new Thread(() -> {
            try {
                mergeSort(array,0,array.length-1);
                drawArray(array, Collections.emptyList(), Color.GREEN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    // hàm trộn merger sort
    private void mergeSort(int[] arr, int l, int r) throws InterruptedException {
        if (l < r) {
            int m = l + (r - l) / 2;

            mergeSort(arr, l, m);

            mergeSort(arr, m + 1, r);

            merge(arr, l, m, r);

//            Thread.sleep(500);
            drawArray(arr);
        }
    }

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

        System.out.println("Bắt đầu quá trình trộn từ chỉ số " + l + " đến " + r);

        while (i < n1 && j < n2) {
            indices.clear();
            indices.add(l + i);
            indices.add(m + 1 + j);
            System.out.println("So sánh phần tử " + L[i] + " với " + R[j]);
            Platform.runLater(() -> {
                drawArray(arr, indices, Color.RED); // Màu đỏ khi so sánh
            });
            Thread.sleep(500);  // Dừng một lúc để hiển thị so sánh

            if (L[i] <= R[j]) {
                System.out.println("Chọn phần tử " + L[i]);
                arr[k] = L[i];
                i++;
            } else {
                System.out.println("Chọn phần tử " + R[j]);
                arr[k] = R[j];
                j++;
            }
            k++;

            Platform.runLater(() -> {
                drawArray(arr, indices, Color.BLACK); // Màu đen sau khi gộp
            });
            Thread.sleep(500); // Dừng một lúc sau khi gộp
        }

        while (i < n1) {
            System.out.println("Chọn phần tử " + L[i] + " từ mảng còn lại L");
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            System.out.println("Chọn phần tử " + R[j] + " từ mảng còn lại R");
            arr[k] = R[j];
            j++;
            k++;
        }
        System.out.println("Kết thúc quá trình trộn từ chỉ số " + l + " đến " + r);
    }

    // quick sort
    @FXML
    public void startQuickSortSimulation() {
        new Thread(() -> {
            try {
                quicksort(array, 0, array.length - 1);
                drawArray(array, Collections.emptyList(), Color.GREEN);
                System.out.println("Sau khi sắp xếp bằng Quick Sort: \n" + Arrays.toString(array));
            } catch (Exception  e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void quicksort(int[] arr, int L, int R) throws InterruptedException {
        // Chọn khoá
        if (L >= R) return;
        int key = arr[(L + R) / 2];
        // Phân bố lại mảng
        int k = partition(arr, L, R, key);
        System.out.println("L= "+L+" R= "+R +" key = "+key + " k = "+k);
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr,L,R+1)));
        System.out.println("___________________");
        // thực hiện chọn cột
        drawArray(arr, Arrays.asList(L, R, k), Color.BLACK);
        Thread.sleep(500);
        quicksort(arr, L, k - 1);
        quicksort(arr, k, R);
        // màu đỏ thực hiện đổi chỗ 2 cột
        drawArray(arr, Arrays.asList(L, R, k), Color.RED);
        Thread.sleep(500);
    }
    public   int partition(int [] arr,int L,int R,int key){
        int iL =L;
        int iR =R;
        while (iL<=iR){
            //voi iL di tim phan tu >= key de doi cho
            while (arr[iL]<key )
                iL++;
            while (arr[iR]>key) iR--;
            if(iL<=iR){
                int temp = arr[iL];
                arr[iL] = arr[iR];
                arr[iR] = temp;
                iL++;iR--;
            }
        }
        return  iL;
    }
    // bubble sort
    @FXML
    public void startBubbleSortSimulation() {
        new Thread(() -> {
            try {
                bubbleSort(array);
                drawArray(array, Collections.emptyList(), Color.GREEN);
                System.out.println("Sau khi sắp xếp bằng Bubble Sort: \n" + Arrays.toString(array));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void bubbleSort(int[] arr) throws InterruptedException {
        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                // Đánh dấu sự so sánh bằng màu ĐEN
                drawArray(arr, Arrays.asList(j, j+1), Color.BLACK);
                System.out.println("So sánh phần tử ở chỉ số " + j + " (" + arr[j] + ") với phần tử ở chỉ số " + (j+1) + " (" + arr[j+1] + ")");

                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    // Đánh dấu các phần tử được hoán đổi bằng màu ĐỎ
                    drawArray(arr, Arrays.asList(j, j+1), Color.RED);
                    System.out.println("Đổi chỗ phần tử ở chỉ số " + j + " (" + arr[j+1] + ") với phần tử ở chỉ số " + (j+1) + " (" + arr[j] + ")");
                }
               // Thời gian chạy
                Thread.sleep(500);
            }
        }
    }


}

