package banking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static void saveData(String fileName, String data) {
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    public static List<String> readAllLines(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) return lines;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }

    // FIX: Added missing method called by Main.java (case 5)
    public static void readData(String fileName) {
        List<String> lines = readAllLines(fileName);
        if (lines.isEmpty()) {
            System.out.println("No transactions found in file.");
            return;
        }
        System.out.println("\n--- Transaction Log from File ---");
        for (String line : lines) {
            System.out.println(line);
        }
    }
}