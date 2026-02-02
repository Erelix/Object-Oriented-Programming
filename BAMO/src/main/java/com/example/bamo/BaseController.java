package com.example.bamo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    @FXML
    private Button changeStudentDataBTN;

    @FXML
    private Button importBTN;

    @FXML
    private Button registerStudentsNBTN;

    @FXML
    private Button exportBTN;

    @FXML
    private Button filterListsBTN;

    @FXML
    private Button getStudentListBTN;

    @FXML
    private Button listUsedDatesBTN;

    @FXML
    private Button newStudentBTN;

    @FXML
    private Button studentGroupsBTN;

    public void openNewStage(String name, String title, int width, int height){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);

            stage.setWidth(width);
            stage.setHeight(height);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickNewStudentBTN(){
        openNewStage("create-new-student.fxml", "Create new Student", 600, 275);
    }

    @FXML
    public void onClickStudentGroupsBTN() {
        openNewStage("assign-students-to-groups-view.fxml", "Assign students to groups", 775, 720);
    }
    @FXML
    public void onClickChangeStudentDataBTN(){
        openNewStage("edit-student-data.fxml", "Edit student data", 1000, 415);
    }
    @FXML
    public void onClickRegisterStudentsNBTN(){
        openNewStage("fill-students-attendance.fxml", "Fill student attendance", 1000, 525);
    }
    @FXML
    public void onClickFilterListsBTN(){
        openNewStage("display-attendance.fxml", "Display attendance", 1165, 770);
    }

    @FXML
    public void onClickImportBTN(){
        openNewStage("import-data.fxml", "Import Data", 365, 235);
    }

    @FXML
    public void onClickExportBTN(){
        openNewStage("export-data.fxml", "Export data", 365, 235);
    }




}
