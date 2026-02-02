package com.example.bamo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateNewStudentController {

    boolean nameB = false;
    boolean surnameB = false;
    boolean groupB = false;

    @FXML
    private TextField groupInput;

    @FXML
    private TextField nameInput;

    @FXML
    private Button newStudentBtn;

    @FXML
    private TextField surnameInput;

    public String getNameInput() {
        String name = ".";
        name = nameInput.getText();
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

    public String getSurnameInput() {
        String surname = ".";
        surname = surnameInput.getText();
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

    public int getGroupInput() {
        int number = 0;
        String groupNr = groupInput.getText();
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

    @FXML
    void addNewStudent(ActionEvent event) {
        String name = getNameInput();
        String surname = getSurnameInput();
        int GroupNumber = getGroupInput();

        if(nameB && surnameB && groupB){
            Student newStudent = new Student(name, surname, GroupNumber);
            StudentList.getInstance().addStudent(newStudent);
        } else if (nameB && surnameB) {
            Student newStudent = new Student(name, surname, 0);
            StudentList.getInstance().addStudent(newStudent);
        }
    }

}
