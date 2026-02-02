package com.brains.dataimport;

import com.brains.dataimport.User;
import com.brains.dataimport.UserList;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    String dataFileName_1 = "MOCK_DATA1.csv";
    String dataFileName_2 = "MOCK_DATA2.csv";
    String dataFileName_3 = "MOCK_DATA3.csv";

    @FXML
    private TableColumn<User, Date> birthDateCol;

    @FXML
    private TableColumn<User, String> countryCol;

    @FXML
    private TableView<User> dataTable;

    @FXML
    private TableColumn<User, String> domainNameCol;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private DatePicker endDate;

    @FXML
    private Label errorLabel;

    @FXML
    private Label fileNameLabel_1;

    @FXML
    private Label fileNameLabel_2;

    @FXML
    private Label fileNameLabel_3;

    @FXML
    private Button filterABCleftBTN;

    @FXML
    private Button filterABCrightBTN;

    @FXML
    private Button filterDateBTN;

    @FXML
    private Button reloadBTN;

    @FXML
    private Button filterMaxMinBTN;

    @FXML
    private Button filterMinMaxBTN;

    String errors = "";

    String selectedColumnName;

    UserList userList;

    @FXML
    private TableColumn<User, String> firstNameCol;

    @FXML
    private TableColumn<User, String> genderCol;

    @FXML
    private TableColumn<User, Integer> idCol;

    @FXML
    private TableColumn<User, Integer> extraCol;

    @FXML
    private Button importDataBTN;

    @FXML
    private TableColumn<User, String> lastNameCol;

    @FXML
    private ProgressBar progressBar_1;

    @FXML
    private ProgressBar progressBar_2;

    @FXML
    private ProgressBar progressBar_3;

    @FXML
    private DatePicker startDate;

    public void initialize() {
        fileNameLabel_1.setText(dataFileName_1);
        fileNameLabel_2.setText(dataFileName_2);
        fileNameLabel_3.setText(dataFileName_3);
        errorLabel.setText("");
        errorLabel.setWrapText(true);

        // Configure the TableView columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        domainNameCol.setCellValueFactory(new PropertyValueFactory<>("domain"));
        birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        extraCol.setCellValueFactory(new PropertyValueFactory<>("extra"));


        dataTable.setOnMouseClicked(event -> {
            for (TableColumn<User, ?> column : dataTable.getColumns()) {
                if (isColumnSelected(column)) {
                    selectedColumnName = column.getText();
                    System.out.println("Selected column: " + selectedColumnName);
                }
            }
        });

        dataTable.setItems(UserList.getInstance().getUsers());
    }

    private boolean isColumnSelected(TableColumn<User, ?> column) {
        return column.equals(dataTable.getSelectionModel().getSelectedCells().get(0).getTableColumn());
    }

    @FXML
    void filterABCleft(ActionEvent event) {
        addExtraCol(UserList.getInstance().getUsers());
        //dataTable.setItems(UserList.getInstance().getUsers());
        System.out.println("CIA");


        if(selectedColumnName!=null) {
            filterAndSortUsersByABCAscending();
        }
    }

    @FXML
    void filterABCright(ActionEvent event) {
        if(selectedColumnName!=null) {
            filterAndSortUsersByABCDescending();
        }
    }



    private void filterAndSortUsersByABCAscending() {
        if (selectedColumnName.equals("first_name")) {
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getName))
                    .collect(Collectors.toList()));
        }  else if (selectedColumnName.equals("last_name")) {
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getSurname))
                    .collect(Collectors.toList()));
        } else if (selectedColumnName.equals("email")){
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getEmail))
                    .collect(Collectors.toList()));
        } else if (selectedColumnName.equals("gender")){
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getGender))
                    .collect(Collectors.toList()));
        } else if (selectedColumnName.equals("country")){
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getCountry))
                    .collect(Collectors.toList()));
        } else if (selectedColumnName.equals("domain_name")) {
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getDomain))
                    .collect(Collectors.toList()));
        }

    }

    private void filterAndSortUsersByABCDescending() {
        if (selectedColumnName.equals("first_name")) {
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getName).reversed())
                    .collect(Collectors.toList()));
        }  else if (selectedColumnName.equals("last_name")) {
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getSurname).reversed())
                    .collect(Collectors.toList()));
        } else if (selectedColumnName.equals("email")){
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getEmail).reversed())
                    .collect(Collectors.toList()));
        } else if (selectedColumnName.equals("gender")){
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getGender).reversed())
                    .collect(Collectors.toList()));
        } else if (selectedColumnName.equals("country")){
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getCountry).reversed())
                    .collect(Collectors.toList()));
        } else if (selectedColumnName.equals("domain_name")){
            dataTable.getItems().setAll(UserList.getInstance().getUsers().stream()
                    .sorted(Comparator.comparing(User::getDomain).reversed())
                    .collect(Collectors.toList()));
        }

    }


    @FXML
    void filterDate(ActionEvent event) {
        addExtraCol(UserList.getInstance().getUsers());

        LocalDate startDateValue = startDate.getValue();
        LocalDate endDateValue = endDate.getValue();

        if (startDateValue != null && endDateValue != null) {
            List<User> filteredUsers = filterUsersByBirthDate(UserList.getInstance().getUsers(), startDateValue, endDateValue);

            dataTable.getItems().setAll(filteredUsers);
        } else {
            errorLabel.setText("Nepasirinkta pradzios data arba pabaigos data.");
        }
    }

    private List<User> filterUsersByBirthDate(List<User> users, LocalDate startDate, LocalDate endDate) {
        return users.stream()
                .filter(user -> user.getBirthDate().compareTo(startDate) >= 0 && user.getBirthDate().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
    }

    @FXML
    void filterMaxMin(ActionEvent event) {
        if(selectedColumnName!=null) {
            if (selectedColumnName.equals("id")) {
                List<User> sortedUsers = sortUsersByIdDescending();
                refreshAndDisplaySortedUsers(sortedUsers);
            } else if (selectedColumnName.equals("birth_date")) {
                List<User> sortedUsers = sortUsersByDateDescending();
                refreshAndDisplaySortedUsers(sortedUsers);
            }
        }
    }

    @FXML
    void filterMinMax(ActionEvent event) {
        if(selectedColumnName!=null) {
            if (selectedColumnName.equals("id")) {
                List<User> sortedUsers = sortUsersByIdAscending();
                refreshAndDisplaySortedUsers(sortedUsers);
            } else if (selectedColumnName.equals("birth_date")) {
                List<User> sortedUsers = sortUsersByDateAscending();
                refreshAndDisplaySortedUsers(sortedUsers);
            }
        }
    }

    private void addExtraCol(List<User> users) {
        List<User> updatedUsers = users.stream()
                .map(user -> {
                    if ("Male".equals(user.getGender())) {
                      user.setExtraCol(user.getName() + " " + user.getEmail());
                    } else if ("Female".equals(user.getGender())) {
                        user.setExtraCol(user.getSurname() + " " + user.getDomain());
                    } else {
                        user.setExtraCol(" ");
                    }
                    return user;
                })
                .collect(Collectors.toList());

        dataTable.getItems().setAll(updatedUsers);

        System.out.println("Set");
    }



    private void refreshAndDisplaySortedUsers(List<User> sortedUsers) {
        dataTable.getItems().clear();
        dataTable.getItems().addAll(sortedUsers);
    }
    private List<User> sortUsersByDateAscending() {
        return UserList.getInstance().getUsers().stream()
                .sorted(Comparator.comparing(User::getBirthDate))
                .collect(Collectors.toList());
    }
    private List<User> sortUsersByDateDescending() {
        return UserList.getInstance().getUsers().stream()
                .sorted(Comparator.comparing(User::getBirthDate).reversed())
                .collect(Collectors.toList());
    }

    private List<User> sortUsersByIdAscending() {
        return UserList.getInstance().getUsers().stream()
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }

    private List<User> sortUsersByIdDescending() {
        return UserList.getInstance().getUsers().stream()
                .sorted(Comparator.comparingInt(User::getId).reversed())
                .collect(Collectors.toList());
    }

    @FXML
    public void importDataBTNClick() {
        startImportThread(dataFileName_1, progressBar_1, fileNameLabel_1);
        startImportThread(dataFileName_2, progressBar_2, fileNameLabel_2);
        startImportThread(dataFileName_3, progressBar_3, fileNameLabel_3);

    }

    private void startImportThread(String fileName, ProgressBar progressBar, Label label) {
        new Thread(() -> {
            String line;
            String csvSplitBy = ",";

            userList = UserList.getInstance();

            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                // Skip header line
                br.readLine();

                int totalLines = (int) br.lines().count();
                br.close();

                BufferedReader br2 = new BufferedReader(new FileReader(fileName));

                br2.readLine();

                int lineNumber = 0;

                while ((line = br2.readLine()) != null) {
                    String[] userData = line.split(csvSplitBy);
                    if (userData.length == 8) {
                        try {
                            int id = Integer.parseInt(userData[0]);
                            String firstName = userData[1];
                            String lastName = userData[2];
                            String email = userData[3];
                            String gender = userData[4];
                            String country = userData[5];
                            String domain = userData[6];
                            LocalDate birthDate = LocalDate.parse(userData[7]);


                            User user = new User(id, firstName, lastName, email, gender, country, domain, birthDate);

                            // Update ObservableList on the JavaFX Application Thread
                            Platform.runLater(() -> userList.addUser(user));
                            Thread.sleep(0);

                            lineNumber++;
                            final int progress = lineNumber;
                            Platform.runLater(() -> progressBar.setProgress((double) progress / totalLines));
                        } catch (NumberFormatException e) {
                            errors += "Klaida saugant eilute: " + line + "\n\n";
                            Platform.runLater(() -> errorLabel.setText(errors));

                            System.err.println("Error parsing line: " + line);
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        errors += "Eilute netinkamo formato: " + line + "\n\n";
                        Platform.runLater(() -> errorLabel.setText(errors));

                        System.err.println("Invalid line format: " + line);
                    }
                }

                Platform.runLater(() -> label.setText(fileName + "                    " + "Baigta."));
                userList = UserList.getInstance();
                br2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
