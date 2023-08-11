package com.example.screens;


import com.example.screens.data.enrolleeControllers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


public class EnrolleeController extends HelloController {


    protected MainInfoController mainInfoController;
    protected DocumentsController documentsController;
    protected SubjectsController subjectsController;
    protected DirectionsController directionsController;
    protected NotificationsController notificationsController;
    @FXML
    private Button exitButton;

    public EnrolleeController()
    {

        System.out.println("Должно передасться сюда"); //передалось! все хорошо тут
        System.out.println(enrollee.getEnrolleeFirstname()+" "+enrollee.getEnrolleeLastname());
        mainInfoController = new MainInfoController(); // а сюда в конструктор из абитуриента все кладется все что есть в базе
        documentsController = new DocumentsController();
        subjectsController = new SubjectsController();
        directionsController = new DirectionsController();
        notificationsController = new NotificationsController();

    }


    @FXML
    public void initialize()
    {
        if (!hasReadglobal()) //если не прочел то  alert чтоб прочитал
        {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Новое уведомление от системы");
            alert.setHeaderText("Появилось новое уведомление от системы");
            alert.setContentText("Просмотрите общие уведомления!");
            alert.showAndWait();
        }
        if (hasNewPersonalNotification()) //если не прочел то alert чтоб прочитал
        {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Новое персональное уведомление от администратора");
            alert.setHeaderText("Появилось новое персональное уведомление от администратора");
            alert.setContentText("Просмотрите персональные уведомления!");
            alert.showAndWait();
        }
        if (finalListExists()) //если не прочел то alert чтоб прочитал
        {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Списки сформированы");
            alert.setHeaderText("Появилось финальные списки прошедших на направления");
            alert.setContentText("Просмотрите уведомления! Появились списки прошедших на бюджетную и на платную основу");
            alert.showAndWait();

        }

    }

    public void ClickToExit(ActionEvent event) {
        System.out.println("Нажата кнопка выхода");
        //проверяем какой статус у абитуриента был
        String someStatus = "статус";
        try {
            System.out.println("попали в блок трай");
            Connection conn = getDbConection();
            Statement statement = null;
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT status FROM enrollees WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");
            if (resultSet.next()) {
                someStatus = resultSet.getString("status");
            }
            System.out.println("закончили блок со статусом " + someStatus + " у айди " + enrollee.getIdEnrollee());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (Objects.equals(someStatus, "Зарегистрирован")) {
            System.out.println("Обнаружили статус Зарегистрирован");
            //проверяем заполнены ли экзамены и документы
            if (hasDocument() && !NullInExams() && !NullInDirections()) {
                try {
                    Connection conn = getDbConection();
                    Statement statement = conn.createStatement();
                    String newStatus = "В конкурсе";

                    statement.executeUpdate("UPDATE enrollees SET status=" + "'" + newStatus + "'" +
                            " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");

                    System.out.println("Поменяли статус абитуриента на В конкурсе");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        //не доверяем! вдруг удалили документы? тогда меяем статус из В конкурсе обратно на Зарегистрирован

        if (Objects.equals(someStatus, "В конкурсе"))
        {
            if (!hasDocument() || NullInExams() || NullInDirections()) {
                try {
                    Connection conn = getDbConection();
                    Statement statement = conn.createStatement();
                    String newStatus = "Зарегистрирован";

                    statement.executeUpdate("UPDATE enrollees SET status=" + "'" + newStatus + "'" +
                            " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");

                    System.out.println("Поменяли статус абитуриента на Зарегистрирован потому что что-то отсутствует");

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Вы не в конкурсе");
                alert.setContentText(enrollee.getFio()+", обратите ваше внимание: вы не заполнили информацию до конца!!! Не забудьте сделать это до "+enrollee.lastDate);
                alert.showAndWait();

            }

            if(someStatus.equals("В конкурсе")){
                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Все данные сохранены");
                alert.setHeaderText("Вы в конкурсе");
                alert.setContentText(enrollee.getFio()+", все данные сохранены, вы находитесь в конкурсных списках! Не забывайте, что редактирование данных возможно до "+enrollee.lastDate);
                alert.showAndWait();
            }


            exitButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/screens/Welcome.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 900, 600));
            stage.showAndWait();

            //теперь тут проверим все ли поля в аккаунте заполнены


        }
    }
    public boolean hasNewPersonalNotification()
    {
        Statement statement = null;
        String found1 = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT hasNewPersonalNotification FROM enrollees WHERE idEnrollee='"+enrollee.getIdEnrollee()+"'");
            if(resultSet.next())
            {
                found1 = resultSet.getString("hasNewPersonalNotification");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("есть новые персональные - 1  /  0 - нет : "+found1);
        return Objects.equals(found1, "1");

    }

    public boolean hasReadglobal()
    {
        Statement statement = null;
        String found1 = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT hasReadGlobalNotifications FROM enrollees WHERE idEnrollee='"+enrollee.getIdEnrollee()+"'");
            if(resultSet.next())
            {
                found1 = resultSet.getString("hasReadGlobalNotifications");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Прочел глобальные = 1 / не прочел = 0 : "+found1);
        return Objects.equals(found1, "1");

    }

    public boolean finalListExists()
    {
        Statement statement = null;
        String found1 = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT finalList FROM notificationsForAll WHERE text='Добро пожаловать!'");
            if(resultSet.next())
            {
                found1 = resultSet.getString("finalList");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("есть финальные списки = 1 / нет = 0 : "+found1);
        return Objects.equals(found1, "1");

    }

    public boolean NullInDirections() //смотрит пустая клеточка экзаменов в базе или нет
    {
        Statement statement = null;
        String foundNULL = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT directions FROM enrollees WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");
            if (resultSet.next()) {
                foundNULL = resultSet.getString("directions");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот вижу что тут направления: " + foundNULL);
        return Objects.equals(foundNULL, "");

    }

    public boolean NullInExams() //смотрит пустая клеточка экзаменов в базе или нет
    {
        Statement statement = null;
        String foundNULL = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT exams FROM enrollees WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");
            if (resultSet.next()) {
                foundNULL = resultSet.getString("exams");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот вижу что тут " + foundNULL);
        return Objects.equals(foundNULL, null);

    }

    public boolean hasDocument()
    {
        String name = enrollee.getIdEnrollee().toString();

        File file = new File("src/main/java/com/example/screens/data/enrolleeControllers/diplomas/"+name+".pdf"); //саша ты все перепутал боже зачем ты вообще это делаешь если ты делаешь на отвали

        return (file.exists());
    }


}

