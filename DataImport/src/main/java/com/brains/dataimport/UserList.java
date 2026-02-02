package com.brains.dataimport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserList {
    // Singleton instance
    private static UserList instance;


    private ObservableList<User> users;


    private UserList() {
        users = FXCollections.observableArrayList();
    }

    public static UserList getInstance() {
        if (instance == null) {
            instance = new UserList();
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean removeUser(int id) {
        return users.removeIf(user -> user.getId() == id);
    }

    public ObservableList<User> getUsers() {
        return users;
    }
}
