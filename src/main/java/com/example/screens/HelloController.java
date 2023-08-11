package com.example.screens;

import java.io.IOException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import com.example.screens.authAnimation.Jump;
import com.example.screens.data.Admin;
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

public class HelloController extends DatabaseHandler {

    @FXML
    private Button authButton;

    @FXML
    private Button goRegButton;

    @FXML
    private TextField login_f;

    @FXML
    private PasswordField password_f;

    private User user=new User();
    public static  Enrollee enrollee=new Enrollee();
    public static Admin admin=new Admin();

    @FXML
    public void initialize()
    {
        authButton.setOnAction(event ->{
            String loginText = login_f.getText().trim();
            String loginPass = password_f.getText().trim();
            if(!loginText.equals("") && !loginPass.equals(""))
                loginUser(loginText,loginPass);
            else
                System.out.println("Login and password is empty");
        });

        if(java.sql.Date.valueOf(String.valueOf(enrollee.dateNow)).before(Date.valueOf(String.valueOf(enrollee.lastDate))))
        {
            goRegButton.setOnAction(event -> {
                newWindow("/com/example/screens/Registration.fxml");
            });
        }
        else
        {
            goRegButton.setOnAction(event ->
            {
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Время регистрации прошло");
                alert.setHeaderText("Невозможно зарегистрироваться");
                alert.setContentText("К сожалению срок регистрации и подачи документов прошел");
                alert.showAndWait();
            });

        }

    }

    private void loginUser(String loginText, String loginPass) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        //User user = new User();
        user.setUsername(loginText);
        user.setPassword(loginPass);
        dbHandler.getUserLoginPassword(user);
        ResultSet result1 = dbHandler.getUserLoginPassword(user);
        user.IdFromTable(); //добавляю в класс айди каждый раз при входе вдруг там чета поменялось

        user.setFirstname(user.FirstnameTable(loginText)); //зафигачиваем имя юзера и фамилию в класс
        user.setLastname(user.LastnameTable(loginText));

        int counter = 0;
        while (true) {
            try {
                if (!result1.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        if ((counter >= 1)) {
            String currentRole = user.RoleFromTable(loginText);

            String firstname11 = user.FirstnameTable(loginText); //узнаю имя чела или чувихи
            String lastname11 = user.LastnameTable(loginText); //ну и фамилию надо!

            if (Objects.equals(currentRole, "Администратор"))
            {
                newWindow("/com/example/screens/admin.fxml");
                admin.setFirstNameAdmin(firstname11);
                admin.setLastNameAdmin(lastname11);
                admin.setidAdmin(user.GetUserIdFromTable());
                System.out.println("Айди админа "+admin.getidAdmin());

            }
            else
            {
                //mainInfoController.setHoe_fio(user.FirstnameTable(loginText)+" "+user.LastnameTable(loginText));

                System.out.println(firstname11 + " " + lastname11);
                enrollee.setEnrolleeFirstname(firstname11); //пихаю
                enrollee.setEnrolleeLastname(lastname11); // ))
                System.out.println(enrollee.getEnrolleeFirstname() + " " + enrollee.getEnrolleeLastname());
                //enrollee.getFromBase(enrollee);

                enrollee.setIdEnrollee(user.GetUserIdFromTable());


                //EnrolleeController enrolleeController = new EnrolleeController(enrollee);
                //System.out.println("HelloController: "+enrolleeController.enrollee1.getEnrolleeFirstname()+" "+enrolleeController.enrollee1.getEnrolleeLastname());
                newWindow("/com/example/screens/enrollee.fxml"); //проблема видимо тут//наверное эту штуку надо запихать в контроллер и выхывать эту функцию из созданого экземпляра контроллера

            }
            //newWindow("/com/example/screens/enrollee.fxml");
            //Enrollee enrolle=new Enrollee();
        }

        else {
            Jump userLoginAn = new Jump(login_f);
            Jump userPassinAn = new Jump(password_f);
            userLoginAn.play();
            userPassinAn.play();
        }
    }
    public void newWindow(String window){
        goRegButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
//        System.out.println("1");

        loader.setLocation(getClass().getResource(window));
//        System.out.println("2");
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Это " + e.getMessage() + " причина почему экран не загрузился? ");
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
        //stage.setFullScreen(true);
        //stage.showAndWait();
    }
}
