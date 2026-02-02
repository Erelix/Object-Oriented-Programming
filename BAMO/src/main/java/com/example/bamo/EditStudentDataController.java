package com.example.bamo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

public class EditStudentDataController extends TableStudentClass implements SearchService{

    @FXML
    private Button changeBTN;

    @FXML
    private TextField newInputID;

    @FXML
    private TextField newNameInput;

    @FXML
    private TextField newSurnameInput;

    @FXML
    private TextField newGroupInput;

    @FXML
    private Button searchBTN;

    @FXML
    private TextField searchNameInput;

    @FXML
    private TextField searchSurnameInput;

    private boolean nameB;
    private boolean surnameB;


    @FXML
    void initialize() {
        // Initialize TableView columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        groupColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getGroup()).asObject());

        nrColumn.setCellValueFactory(cellData -> {
            int rowIndex = studentTable.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleIntegerProperty(rowIndex).asObject();
        });

        studentTable.setEditable(true);

        // Fill TableView
        fillTable(StudentList.getInstance().getStudentObservableList());


        studentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Add listener for selection changes
        studentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if (newValue != null) {
                    // Update text fields with the data of the selected student
                    newInputID.setText(String.valueOf(newValue.getId()));
                    newNameInput.setText(newValue.getName());
                    newSurnameInput.setText(newValue.getSurname());
                    newGroupInput.setText(String.valueOf(newValue.getGroup()));
                }
            }
        });

    }


    @FXML
    void onClickChangeBTN(ActionEvent event) {
        // Retrieve the selected student
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        // Check if a student is selected
        if (selectedStudent != null) {
            // Update the selected student's data with the values entered in the text fields
            selectedStudent.setId(Integer.parseInt(newInputID.getText()));
            selectedStudent.setName(newNameInput.getText());
            selectedStudent.setSurname(newSurnameInput.getText());
            selectedStudent.setGroup(Integer.parseInt(newGroupInput.getText()));

            // Refresh the TableView to reflect the changes
            studentTable.refresh();

            // Optionally, clear the text fields after updating the student's data
            newInputID.clear();
            newNameInput.clear();
            newSurnameInput.clear();
            newGroupInput.clear();
        } else {
            // Display an error message if no student is selected
            // You can customize this message as needed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a student to update.");
            alert.showAndWait();
        }
    }
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
    void onClickSearchBTN() {
        searchForSpecificStudents();
    }

    @Override
    public void searchForSpecificStudents() {
        String name = getNameInput(searchNameInput);
        String surname = getSurnameInput(searchSurnameInput);
        if (nameB || surnameB) {
            ObservableList<Student> filteredStudents;
            ObservableList<Student> allStudents = StudentList.getInstance().getStudentObservableList();
            if (nameB && surnameB) {
                filteredStudents = allStudents.filtered(student -> student.getName().equals(name));
                filteredStudents = filteredStudents.filtered(student -> student.getSurname().equals(surname));
            } else if (nameB) {
                filteredStudents = allStudents.filtered(student -> student.getName().equals(name));
            } else {
                filteredStudents = allStudents.filtered(student -> student.getSurname().equals(surname));
            }
            fillTable(filteredStudents);
        } else {
            studentTable.refresh();
            fillTable(StudentList.getInstance().getStudentObservableList());
        }
    }
}

