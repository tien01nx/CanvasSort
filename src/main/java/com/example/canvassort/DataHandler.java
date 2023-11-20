package com.example.canvassort;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class DataHandler {

    public DataHandler() {
    }
    public int[] generateRandomData(int size, int bound) {
        Random random = new Random();
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(bound);
        }
        return data;
    }
    public List<int[]> readFromFile() {
        List<int[]> arrays = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Data File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
//                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        try {
            File selectedFile = fileChooser.showOpenDialog(null);
            Scanner scanner = new Scanner(selectedFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                int[] array = new int[values.length];
                for (int i = 0; i < values.length; i++) {
                    array[i] = Integer.parseInt(values[i]);
                }
                arrays.add(array);
                scanner.close();
            }
        } catch (Exception e) {
            System.out.println("File khong ton tai: file không nhận" );
        }
        return arrays;
    }
    public void saveToFile(int[] data) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File file = fileChooser.showSaveDialog(null);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            for (int i = 0; i < data.length; i++) {
                printWriter.print(data[i]);
                if (i < data.length - 1) {
                    printWriter.print(",");
                }
            }
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
