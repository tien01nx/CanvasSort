module com.example.canvassort {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.canvassort to javafx.fxml;
    exports com.example.canvassort;
    exports com.example.canvassort.Interfaces;
    opens com.example.canvassort.Interfaces to javafx.fxml;
    exports com.example.canvassort.Implements;
    opens com.example.canvassort.Implements to javafx.fxml;
}