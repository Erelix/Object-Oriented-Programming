module com.brains.dataimport {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.brains.dataimport to javafx.fxml;
    exports com.brains.dataimport;
}