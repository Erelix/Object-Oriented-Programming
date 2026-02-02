package com.example.bamo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.*;

public class ImportDataController implements ImportExportService {

    @FXML
    private TextField fileNameInput;

    @FXML
    private Button importCSVBTN;

    @FXML
    private Button importExcelBTN;

    @FXML
    private Text labelNotSuccess;

    @FXML
    private Text labelSuccess;

    @Override
    public void importData(String fileName) throws IOException {
        if (fileName.endsWith(".csv")) {
            importFromCSV(fileName);
        } else if (fileName.endsWith(".xls")) {
            importFromXLS(fileName);
        } else {
            throw new IOException("Unsupported file format");
        }
    }

    @Override
    public void exportData(String fileName) throws IOException {

    }

    private void importFromCSV(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(", ");
                try {
                    int id = Integer.parseInt(fields[1].trim());
                    String name = fields[2].trim();
                    String surname = fields[3].trim();
                    int group = Integer.parseInt(fields[4].trim());
                    Student student = new Student(id, name, surname, group);
                    StudentList.getInstance().addStudent(student);
                } catch (NumberFormatException e) {
                    System.err.println("ERROR importing students.");
                }
            }
        }
    }

    private void importFromXLS(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\t");
                try {
                    int id = Integer.parseInt(fields[1].trim());
                    String name = fields[2].trim();
                    String surname = fields[3].trim();
                    int group = Integer.parseInt(fields[4].trim());
                    Student student = new Student(id, name, surname, group);
                    StudentList.getInstance().addStudent(student);
                } catch (NumberFormatException e) {
                    System.err.println("ERROR importing students.");
                }
            }
        }
    }

    @FXML
    void onClickImportCSVBTN() {
        handleImportClick(".csv");
    }

    @FXML
    void onClickImportExcelBTN() {
        handleImportClick(".xls");
    }

    private void handleImportClick(String extension) {
        String fileName = fileNameInput.getText() + extension;
        if (!fileName.isEmpty()) {
            try {
                importData(fileName);
                labelNotSuccess.setText("");
                labelSuccess.setText("*Pavyko");
            } catch (IOException e) {
                labelSuccess.setText("");
                labelNotSuccess.setText("*Nepavyko");
                e.printStackTrace();
            }
        }
    }
}
