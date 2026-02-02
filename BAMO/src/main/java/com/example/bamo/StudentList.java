package com.example.bamo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentList {
    private static StudentList instance = null;
    private ObservableList<Student> studentObservableList;
    private StudentList() {
        studentObservableList = FXCollections.observableArrayList();
    }
    public static StudentList getInstance() {
        if (instance == null) {
            instance = new StudentList();
        }
        return instance;
    }
    public void addStudent(Student student) {
        studentObservableList.add(student);
        System.out.println();
        //printAllStudents();
    }
    public void removeStudent(Student student) {
        studentObservableList.remove(student);
    }
    public ObservableList<Student> getStudentObservableList() {
        return studentObservableList;
    }

}
