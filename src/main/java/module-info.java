module com.example.x {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;
    requires mysql.connector.j;
    requires com.google.gson;
    requires java.desktop;
//    requires jdk.internal.le;


    opens com.example.screens to javafx.fxml;
    exports com.example.screens;
    exports com.example.screens.data;
    opens com.example.screens.data to javafx.fxml;
    exports com.example.screens.data.enrolleeControllers;
    opens com.example.screens.data.enrolleeControllers to javafx.fxml;
    exports com.example.screens.finalThings;
    opens com.example.screens.finalThings to javafx.fxml;
}