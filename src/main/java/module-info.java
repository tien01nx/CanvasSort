module com.example.canvassort {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.canvassort to javafx.fxml;
    exports com.example.canvassort;
}