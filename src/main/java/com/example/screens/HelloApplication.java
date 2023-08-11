package com.example.screens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Welcome.fxml")));
            Scene scene = new Scene(root, 900, 600);
            primaryStage.setTitle("Ведущий Межгаллактический Университет");
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}