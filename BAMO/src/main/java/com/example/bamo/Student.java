package com.example.bamo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ListIterator;

public class Student {
    String name;
    String surname;
    int group;
    static int currentIdNum = 200250;
    int id;

    private LinkedList<Date> dateList;
    private final BooleanProperty choosed = new SimpleBooleanProperty(false);


    public Student(String givenName, String givenSurname, int givenGroup){
        name = givenName;
        surname = givenSurname;
        group = givenGroup;
        id = currentIdNum++;
    }

    public Student(int idGiven, String givenName, String givenSurname, int givenGroup){
        name = givenName;
        surname = givenSurname;
        group = givenGroup;
        id = idGiven;
    }


    public void addDate(Date newDate) {
        // Check if the new date is in the past
        LocalDate currentDate = LocalDate.now();
        LocalDate newDateValue = LocalDate.parse(newDate.getDate());

        // Allow adding past dates
        if (newDateValue.isAfter(currentDate)) {
            System.out.println("Cannot add future dates for attendance.");
            return;
        }

        // Initialize the list if null
        if (dateList == null) {
            dateList = new LinkedList<>();
            dateList.add(newDate); // Add the new date if the list is empty
            return;
        }

        // Find the correct position to insert the new date in chronological order
        ListIterator<Date> iterator = dateList.listIterator();
        while (iterator.hasNext()) {
            LocalDate dateAtIndex = LocalDate.parse(iterator.next().getDate());
            if (newDateValue.isBefore(dateAtIndex)) {
                iterator.previous(); // Move back one step
                iterator.add(newDate); // Insert the new date
                return;
            }
        }

        // If the new date is the latest, add it at the end
        dateList.addLast(newDate);
    }

    public LinkedList<Date> getDateList() {
        return dateList;
    }


    public void setGroup(int group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getGroup() {
        return group;
    }

    public int getId() {
        return id;
    }

    public BooleanProperty choosedProperty() {
        return choosed;
    }

    public boolean isChoosed() {
        return choosed.get();
    }

    public void setChoosed(boolean choosed) {
        this.choosed.set(choosed);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }




}
