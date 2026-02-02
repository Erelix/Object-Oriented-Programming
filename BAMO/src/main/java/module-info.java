module com.example.bamo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox; // Add this line to declare dependency on Apache PDFBox

    opens com.example.bamo to javafx.fxml;
    exports com.example.bamo;
}
