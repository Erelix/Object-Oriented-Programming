package com.example.bamo;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class FillStudentsAttendanceController extends AssignStudentsToGroupsController implements SearchService{

        @FXML
        private Button registerBTN;

        @FXML
        private Button searchBTN;

        @FXML
        private TextField searchGroupInput;

        @FXML
        private TextField searchNameInput;

        @FXML
        private TextField searchSurnameInput;

        @FXML
        private TextField selectGroupInput;

        @FXML
        private Button selectAllTableStudentsBTN;

        @FXML
        private DatePicker selectData;

        @FXML
        private Button selectGroupBTN;

        @FXML
        private Button deselectAllTableStudentsBTN;

        private Boolean nameB = false;

        private Boolean surnameB = false;


    @FXML
    void onClickRegisterBTN() {
        // Get the selected date from the DatePicker
        LocalDate selectedDate = selectData.getValue();

        if (selectedDate != null) {
            // Convert the selected date to a string format
            String dateString = selectedDate.toString();

            // Create a Date object with the selected date and mark attendance as true
            Date newDate = new Date(dateString, true);

            // Get the list of selected students
            ObservableList<Student> selectedStudents = studentTable.getItems().filtered(Student::isChoosed);

            // Register attendance for each selected student
            for (Student student : selectedStudents) {
                student.addDate(newDate);
            }

            // Get all students
            ObservableList<Student> allStudents = StudentList.getInstance().getStudentObservableList();

            // Register attendance for all students not selected
            for (Student student : allStudents) {
                if (!selectedStudents.contains(student)) {
                    student.addDate(new Date(dateString, false));
                }
            }

            // Optionally, print a message to indicate that the attendance has been registered
            System.out.println("Date: " + dateString+" registrated.");

            // Clear the selection after registering attendance
            onClickDeselectAllTableStudentsBTN();
        } else {
            System.out.println("Please select a date.");
        }
    }

    @FXML
    void onClickSearchBTN() {
        searchForSpecificStudents();
    }

        @FXML
        void onClickSelectAllTableStudentsBTN() {
            for (Student student : studentTable.getItems()) {
                student.setChoosed(true);
            }
        }

        @FXML
        void onClickDeselectAllTableStudentsBTN() {
            for (Student student : studentTable.getItems()) {
                student.setChoosed(false);
            }
        }

        @FXML
        void onClickSelectGroupBTN() {
            groupB =  false;
            int group = getGroupInput(selectGroupInput);
            if (groupB) {
                for (Student student : studentTable.getItems()) {
                    if (student.getGroup() == group)
                        student.setChoosed(true);
                }
            }
        }

    public String getNameInput() {
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

    public String getSurnameInput() {
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

    @Override
    void initialize() {
        setUpTable();
    }

    @Override
    public void searchForSpecificStudents() {
        String name = getNameInput();
        String surname = getSurnameInput();
        groupB =  false;
        int group = getGroupInput(searchGroupInput);
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
