package com.example.bamo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;

public class AssignStudentsToGroupsController extends TableStudentClass{
    @FXML
    private TableColumn<Student, Boolean> checkBoxColumn;

    @FXML
    private TextField groupNrInput;

    @FXML
    private Button deleteStudentsBTN;

    @FXML
    private Button groupBTN;

    @FXML
    private Button refreshBTN;

    @FXML
    private Button displayGroupsBTN;

    @FXML
    void initialize() {
        setUpTable();
    }

    void setUpTable(){
        // Initialize TableView columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        groupColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getGroup()).asObject());

        nrColumn.setCellValueFactory(cellData -> {
            int rowIndex = studentTable.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleIntegerProperty(rowIndex).asObject();
        });

        checkBoxColumn.setCellValueFactory(cellData -> cellData.getValue().choosedProperty());
        checkBoxColumn.setCellFactory(column -> {
            CheckBoxTableCell<Student, Boolean> cell = new CheckBoxTableCell<>();
            cell.setSelectedStateCallback(param -> {
                Student student = studentTable.getItems().get(param);
                return student.choosedProperty();
            });

            // Listener for checkboxes
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    Student student = cell.getTableView().getItems().get(cell.getIndex());
                    student.setChoosed(!student.isChoosed()); // Toggle checkbox
                }
            });
            return cell;
        });

        studentTable.setEditable(true);

        fillTable(StudentList.getInstance().getStudentObservableList());

    }

    //studentTable.setEditable(true);
    // checkBoxColumn.setEditable(true);

    @FXML
    void onClickGroupBTN() {
        groupB = false;
        int newGroup = getGroupInput(groupNrInput);
        if (groupB) {
            ObservableList<Student> selectedStudents = studentTable.getItems().filtered(Student::isChoosed);
            if (!selectedStudents.isEmpty()) {
                // Change the group of selected students
                for (Student student : selectedStudents) {
                    student.setGroup(newGroup);
                }
                System.out.println("Group updated for selected students.");

                onClickRefreshBTN();

            } else {
                System.out.println("No students selected.");
            }
        }
    }

    @FXML
    void onClickDisplayGroupsBTN(){
        int group = getGroupInput(groupNrInput);
        if (groupB) {
            ObservableList<Student> allStudents = StudentList.getInstance().getStudentObservableList();
            ObservableList<Student> filteredStudents = allStudents.filtered(student -> student.getGroup() == group);
            fillTable(filteredStudents);
        }
    }

    @FXML
    void onClickRefreshBTN() {
        studentTable.refresh();
        fillTable(StudentList.getInstance().getStudentObservableList());
        for (Student student : studentTable.getItems()) {
            student.setChoosed(false);
        }
    }

    @FXML
    void onClickDeleteStudentsBTN() {
        ObservableList<Student> selectedStudents = studentTable.getItems().filtered(Student::isChoosed);
        if (!selectedStudents.isEmpty()) {
            StudentList.getInstance().getStudentObservableList().removeAll(selectedStudents);
            System.out.println("Selected students deleted.");
            studentTable.refresh();
        } else {
            System.out.println("No students selected.");
        }

    }

}