package com.example.bamo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExportDataController implements ImportExportService {

    @FXML
    private Button exportAsCSVBTN;

    @FXML
    private Button exportAsExcelBTN;

    @FXML
    private TextField fileNameInput;

    @Override
    public void importData(String fileName) throws IOException {
    }

    @Override
    public void exportData(String fileName) throws IOException {
        if (fileName.endsWith(".csv")) {
            exportAsCSV(fileName);
        } else if (fileName.endsWith(".xls")) {
            exportAsExcel(fileName);
        } else {
            throw new IOException("Unsupported file format");
        }
    }

    private void exportAsCSV(String fileName) throws FileNotFoundException {
        File csvFile = new File(fileName);
        try (PrintWriter out = new PrintWriter(csvFile)) {
            int i = 1;
            out.println("Nr, ID, Name, Surname, Group");
            for (Student student : StudentList.getInstance().getStudentObservableList()) {
                out.println(i + ", " + student.getId() + ", " + student.getName() + ", " + student.getSurname() + ", " + student.getGroup());
                ++i;
            }
        }
    }

    private void exportAsExcel(String fileName) throws FileNotFoundException {
        File excelFile = new File(fileName);
        try (PrintWriter out = new PrintWriter(excelFile)) {
            out.println("Nr\tID\tName\tSurname\tGroup");
            List<Student> students = StudentList.getInstance().getStudentObservableList();
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                out.println((i + 1) + "\t" + student.getId() + "\t" + student.getName() + "\t" + student.getSurname() + "\t" + student.getGroup());
            }
        }
    }

    @FXML
    void onClickExportAsCSVBTN() {
        handleExportClick(".csv");
    }

    @FXML
    void onClickExportAsExcelBTN() {
        handleExportClick(".xls");
    }

    private void handleExportClick(String extension) {
        String fileName = fileNameInput.getText() + extension;
        if (!fileName.isEmpty()) {
            try {
                exportData(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
