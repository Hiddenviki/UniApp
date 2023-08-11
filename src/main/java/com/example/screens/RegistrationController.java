package com.example.screens;


import java.io.IOException;
import java.sql.Date;
import java.util.ResourceBundle;

import com.example.screens.data.DatabaseHandler;
import com.example.screens.data.Enrollee;
import com.example.screens.data.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.example.screens.HelloController.enrollee;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;


    @FXML
    private Button authUpButton1 = new Button();
    @FXML
    private Button backUp = new Button();
    @FXML
    private TextField login_f = new TextField();
    @FXML
    private PasswordField password_f = new PasswordField();

    @FXML
    private TextField signUpLastName = new TextField();

    @FXML
    private TextField signUpName = new TextField();


    @FXML
    void initialize() {


        authUpButton1.setOnAction(event -> {
            try {
                if (!signUpName.getText().matches("[а-яА-Я]+") | !signUpLastName.getText().matches("[а-яА-Я]+") |
                        !login_f.getText().matches("[a-zA-Z0-9]+") | password_f.getText().matches("[a-zA-Z]")) {
                    throw new Exception();
                }
                signUpNewUser();
                newWindow("/com/example/screens/Welcome.fxml"); ///////////после регистрации возвращаемся на 1‑е окно

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка регистрации");
                alert.setHeaderText("Ошибка ввода!");
                alert.setContentText("Проверьте правильность введенных данных");

                alert.showAndWait();
            }
        });
        backUp.setOnAction(event -> {
            backUp.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/screens/Welcome.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root,900,600));
            stage.showAndWait();
        });


    }

    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String firstname2 = signUpName.getText();
        String lastname2 = signUpLastName.getText();
        String username2 = login_f.getText();
        String password2 = password_f.getText();
        String role2 = "Абитуриент"; //потому что регистрируется сам всегда абитуриент. Ну,типа, админам уже дают их логин и пароль

        User user = new User(firstname2, lastname2, username2, password2, role2);
        Enrollee enrollee = new Enrollee();
        enrollee.setStatus("Зарегистрирован");
        enrollee.setEnrolleeFirstname(firstname2);
        enrollee.setEnrolleeLastname(lastname2);


        dbHandler.signUpUser(user); //СНАЧАЛА НАДО ЗАРЕГАТЬ В ТАБЛИЦУ ЮЗЕР ЧТОБ ВЫТЯНУТЬ ОТТУДА АЙДИШНИК

        ///типа теперь у нас есть строчка с айдишником в базе в таблице user и потэтому мы можем юзать это поле
        //можно ли проще?
        //да,
        //но я устала думать, уже ночь, так тоже работает
        System.out.println("Вот такая суета: " + user.GetUserIdFromTable());
        enrollee.setIdEnrollee(user.GetUserIdFromTable()); //поправив хотя я кладу сразу этот айдишник

        dbHandler.signUpEnrollee(enrollee, String.valueOf(user.GetUserIdFromTable())); //и потом уже енролле



    }

    public void newWindow(String window) {
        authUpButton1.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,900,600));
        //stage.setFullScreen(true);
        stage.show();
    }

}
