package com.example.bamo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public abstract class TableStudentClass {
    @FXML
    public TableColumn<Student, Integer> nrColumn;

    @FXML
    public TableColumn<Student, Integer> groupColumn;

    @FXML
    public TableColumn<Student, Integer> idColumn;

    @FXML
    public TableColumn<Student, String> nameColumn;

    @FXML
    public TableView<Student> studentTable;
    @FXML
    public TableColumn<Student, String> surnameColumn;
    public boolean groupB = false;

    @FXML
    abstract void initialize();

    public int getGroupInput(TextField groupNrInput) {
        int number = 0;
        String groupNr = groupNrInput.getText();
        try{
            number = Integer.parseInt(groupNr);
            if (number > 0)
                groupB = true;
            else
                groupB = false;

        } catch (NumberFormatException e){
            groupB = false;
        }
        return number;
    }

    public void fillTable(ObservableList<Student> students) {
        studentTable.setItems(students);
    }
}
