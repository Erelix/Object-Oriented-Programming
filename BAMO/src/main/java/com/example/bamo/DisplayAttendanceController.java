package com.example.bamo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayAttendanceController extends EditStudentDataController implements SearchService{
    @FXML
    private TextField fileNameInput;

    @FXML
    private DatePicker firstDate;

    @FXML
    private Button saveBTN;

    @FXML
    private Button searchBTN;

    @FXML
    private Button searchDateBTN;

    @FXML
    private TextField searchGroupInp;

    @FXML
    private TextField searchNameInp;

    @FXML
    private TextField searchSurnameInp;

    @FXML
    private DatePicker secondDate;

    private boolean nameB;
    private boolean surnameB;


    public String getNameInput(TextField searchNameInput) {
        String name = ".";
        name = searchNameInput.getText();
        try{
            if (name.isEmpty())
                nameB = false;
            else
                nameB = true;

        } catch (NumberFormatException e){
            nameB = false;
        }
        return name;
    }

    public String getSurnameInput(TextField searchSurnameInput) {
        String surname = ".";
        surname = searchSurnameInput.getText();
        try{
            if (surname.isEmpty())
                surnameB = false;
            else
                surnameB = true;

        } catch (NumberFormatException e){
            surnameB = false;
        }
        return surname;
    }

    @FXML
    void onClickSearchBTNThis() {
        searchForSpecificStudents();
    }
    private List<LocalDate> allDates = new ArrayList<>();

    @Override
    void initialize() {
        // Initialize TableView columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        groupColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getGroup()).asObject());
        // Fill TableView
        updateTableView();
    }

    private void updateTableView() {
        studentTable.getColumns().clear(); // Clear existing columns

        // Get the list of all students
        ObservableList<Student> allStudents = StudentList.getInstance().getStudentObservableList();

        // Add the student columns
        studentTable.getColumns().addAll(idColumn, nameColumn, surnameColumn, groupColumn);
        studentTable.setItems(allStudents);

        // Populate the list of unique dates
        allDates.clear();
        for (Student student : allStudents) {
            if (student.getDateList() != null) {
                for (Date date : student.getDateList()) {
                    LocalDate dateValue = LocalDate.parse(date.getDate());
                    if (!allDates.contains(dateValue)) {
                        allDates.add(dateValue);
                    }
                }
            }
        }

        // Sort the list of dates
        Collections.sort(allDates);

        // Add columns for each date
        for (LocalDate date : allDates) {
            TableColumn<Student, String> dateColumn = new TableColumn<>(date.toString());
            dateColumn.setCellValueFactory(cellData -> {
                StringProperty attendance = new SimpleStringProperty(" ");
                if (cellData.getValue().getDateList() != null) {
                    for (Date studentDate : cellData.getValue().getDateList()) {
                        if (LocalDate.parse(studentDate.getDate()).isEqual(date)) {

                            if (studentDate.isAttended()) {
                                attendance.set("+");
                            } else
                                attendance.set(" ");

                            break;
                        }
                    }
                }
                return attendance;
            });
            studentTable.getColumns().add(dateColumn);
        }

    }


    @FXML
    private void onClickSearchBTNDate() {
        studentTable.getColumns().clear(); // Clear existing columns

        // Get the list of all students
        ObservableList<Student> allStudents = StudentList.getInstance().getStudentObservableList();

        // Add the student columns
        studentTable.getColumns().addAll(idColumn, nameColumn, surnameColumn, groupColumn);

        // Filter dates within the bounds of firstDate and secondDate
        LocalDate startDate = firstDate.getValue();
        LocalDate endDate = secondDate.getValue();

        // Check if both start and end dates are selected
        if (startDate != null && endDate != null) {
            // Populate the list of unique dates within the bounds
            allDates.clear();
            for (Student student : allStudents) {
                if (student.getDateList() != null) {
                    for (Date date : student.getDateList()) {
                        LocalDate dateValue = LocalDate.parse(date.getDate());
                        if (!allDates.contains(dateValue) && !dateValue.isBefore(startDate) && !dateValue.isAfter(endDate)) {
                            allDates.add(dateValue);
                        }
                    }
                }
            }
        }

        // Sort the list of dates
        Collections.sort(allDates);

        // Add columns for each date within the bounds
        for (LocalDate date : allDates) {
            TableColumn<Student, String> dateColumn = new TableColumn<>(date.toString());
            dateColumn.setCellValueFactory(cellData -> {
                StringProperty attendance = new SimpleStringProperty(" ");
                if (cellData.getValue().getDateList() != null) {
                    for (Date studentDate : cellData.getValue().getDateList()) {
                        LocalDate studentDateValue = LocalDate.parse(studentDate.getDate());
                        if (studentDateValue.isEqual(date)) {
                            if (studentDate.isAttended()) {
                                attendance.set("+");
                            } else {
                                attendance.set(" ");
                            }
                            break;
                        }
                    }
                }
                return attendance;
            });
            studentTable.getColumns().add(dateColumn);
        }
    }


    @FXML
    void onClickSaveBTN() {
        String pdfName = getSurnameInput(fileNameInput);
        if (surnameB){
            PDFExporter.exportToPDF(StudentList.getInstance().getStudentObservableList(), pdfName);
            System.out.println("IN");
        }
        else
            System.out.println("ELSE");
    }


    @Override
    public void searchForSpecificStudents() {
        String name = getNameInput(searchNameInp);
        String surname = getSurnameInput(searchSurnameInp);
        groupB =  false;
        int group = getGroupInput(searchGroupInp);
        if (nameB || surnameB || groupB) {
            ObservableList<Student> filteredStudents;
            ObservableList<Student> allStudents = StudentList.getInstance().getStudentObservableList();
            if (nameB && surnameB && groupB) {
                filteredStudents = allStudents.filtered(student -> student.getName().equals(name));
                filteredStudents = filteredStudents.filtered(student -> student.getSurname().equals(surname));
                filteredStudents = filteredStudents.filtered(student -> student.getGroup()==group);
            } else if(nameB && surnameB){
                filteredStudents = allStudents.filtered(student -> student.getName().equals(name));
                filteredStudents = filteredStudents.filtered(student -> student.getSurname().equals(surname));
            } else if(surnameB && groupB){
                filteredStudents = allStudents.filtered(student -> student.getSurname().equals(surname));
                filteredStudents = filteredStudents.filtered(student -> student.getGroup()==group);
            } else if(nameB && groupB){
                filteredStudents = allStudents.filtered(student -> student.getName().equals(name));
                filteredStudents = filteredStudents.filtered(student -> student.getGroup()==group);
            } else if (nameB) {
                filteredStudents = allStudents.filtered(student -> student.getName().equals(name));
            } else if (groupB) {
                filteredStudents = allStudents.filtered(student -> student.getGroup() == group);
            }else {
                filteredStudents = allStudents.filtered(student -> student.getSurname().equals(surname));
            }
            fillTable(filteredStudents);
        } else {
            studentTable.refresh();
            fillTable(StudentList.getInstance().getStudentObservableList());
        }
    }
}



