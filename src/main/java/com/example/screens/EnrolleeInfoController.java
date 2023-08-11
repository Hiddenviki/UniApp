package com.example.screens;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.screens.data.DatabaseHandler;
import com.example.screens.data.Enrollee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EnrolleeInfoController extends DatabaseHandler {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    private int id;

    public void setId(int id) {
        this.id = id;
        String name = String.valueOf(id);
        File file = new File("src/main/java/com/example/screens/data/enrolleeControllers/diplomas/"+name+".pdf");
        if (file.exists()) {
//            buttonToOpenDiploma.setText("Открыть");
            buttonToOpenDiploma.setDisable(false);
            diploma.setText("Файл загружен");
//            ClickToAddDiplomaVer2();
        } else {
            buttonToOpenDiploma.setDisable(true);
        }
        file = new File("src/main/java/com/example/screens/data/enrolleeControllers/graduations/"+name+".pdf");
        if (file.exists()) {
            buttonToOpenGraduation.setDisable(false);
            graduation.setText("Файл загружен");
//            clickToAddGraduationVer2();
        } else {
            buttonToOpenGraduation.setDisable(true);
        }
    }

    @FXML
    private TextField date = new TextField();

    @FXML
    private TextArea directions = new TextArea();

    public String getDirections() {
        return directions.getText();
    }

    public void setDirections(String directions) {
        this.directions.setText(directions);
    }

    @FXML
    private TextField email = new TextField();

    @FXML
    private TextArea exams = new TextArea();


    @FXML
    private TextField fio = new TextField();

    public String getStatus() {
        return status.getText();
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    @FXML
    private TextField status = new TextField();

    public TextField getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.setText(date);
    }


    public String getEmail() {
        return email.getText();
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public String getExams() {
        return exams.getText();
    }

    public void setExams(String exams) {
        this.exams.setText(exams);
    }

    public String getFio() {
        return fio.getText();
    }

    public void setFio(String fio) {
        this.fio.setText(fio);
    }

    public String getWhyus() {
        return whyus.getText();
    }

    @FXML
    private TextField whyus = new TextField();

    @FXML
    private Text diploma = new Text();

    @FXML
    private Button buttonToOpenDiploma;


    @FXML
    private void clickToOpenDiploma() throws IOException {
        String name = String.valueOf(id);
        File selectedFile = new File("src/main/java/com/example/screens/data/enrolleeControllers/diplomas/"+name+".pdf");
        Desktop desktop =Desktop.getDesktop();
        desktop.open(selectedFile);

    }
    @FXML
    private Text graduation = new Text();
    @FXML
    private Button buttonToOpenGraduation = new Button();
    @FXML
    private void clickToOpenGraduation() throws IOException {
        String name = String.valueOf(id);
        File selectedFile = new File("src/main/java/com/example/screens/data/enrolleeControllers/graduations/"+name+".pdf");
        Desktop desktop =Desktop.getDesktop();
        desktop.open(selectedFile);

    }

    @FXML
    private Button editButton = new Button();
    @FXML
    private void clickToEditButton(ActionEvent event) {
        editButton.setText("СОХРАНИТЬ");
        fio.setEditable(true);
        date.setEditable(true);
        status.setEditable(true);
        email.setEditable(true);
//        exams.setEditable(false);
//        directions.setEditable(false);
        whyus.setEditable(true);
        editButton.setOnAction(e -> {
            editButton.setText("ИЗМЕНИТЬ");
            try {
                Connection conn = getDbConection();
                Statement statement = conn.createStatement();
                statement.executeUpdate("UPDATE `registerlogin`.`enrollees` SET `firstname` = '"+fio.getText().split(" ")[0]+"' WHERE (`idEnrollee` = '"+id+"');");
                statement.executeUpdate("UPDATE `registerlogin`.`enrollees` SET `lastname` = '"+fio.getText().split(" ")[1]+"' WHERE (`idEnrollee` = '"+id+"');");
                statement.executeUpdate("UPDATE `registerlogin`.`enrollees` SET `status` = '"+status.getText()+"' WHERE (`idEnrollee` = '"+id+"');");
                statement.executeUpdate("UPDATE `registerlogin`.`enrollees` SET `email` = '"+email.getText()+"' WHERE (`idEnrollee` = '"+id+"');");
                statement.executeUpdate("UPDATE `registerlogin`.`enrollees` SET `whyus` = '"+whyus.getText()+"' WHERE (`idEnrollee` = '"+id+"');");
                statement.executeUpdate("UPDATE `registerlogin`.`enrollees` SET `dateofbirth` = '"+date.getText()+"' WHERE (`idEnrollee` = '"+id+"');");
//                editButton.setText("СОХРАНИТЬ");
                fio.setEditable(false);
                date.setEditable(false);
                status.setEditable(false);
                email.setEditable(false);
//        exams.setEditable(false);
//        directions.setEditable(false);
                whyus.setEditable(false);
                editButton.setOnAction(this::clickToEditButton);
//                statement.executeUpdate("INSERT INTO notificationsforall(text) VALUES" + "(" + "'" + notificationText + "'" + ")");
            } catch (SQLException | ClassNotFoundException as) {
                System.out.println(as.getCause());
            }


//                System.out.println("пытаюсь положить "+notificationText); //тут все норм


            System.out.println("Инфа обновлена");
        });
    }

    @FXML
    void initialize() {
        fio.setEditable(false);
        date.setEditable(false);
        status.setEditable(false);
        email.setEditable(false);
        exams.setEditable(false);
        directions.setEditable(false);
        whyus.setEditable(false);
//        exams.se
        String name = String.valueOf(id);
        File file = new File("src/main/java/com/example/screens/data/enrolleeControllers/diplomas/"+name+".pdf");
        if (!file.exists()) {
            buttonToOpenDiploma.setDisable(true);

        }

        file = new File("src/main/java/com/example/screens/data/enrolleeControllers/graduations/"+name+".pdf");
        if (!file.exists()) {
            buttonToOpenGraduation.setDisable(true);
        }


    }

    public void setWhyus(String whyus) {
        this.whyus.setText(whyus);
    }

    public void setAll(Enrollee enrollee) {
    }

}
