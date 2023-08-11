package com.example.screens.data.enrolleeControllers;

import com.example.screens.data.DatabaseHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.example.screens.HelloController.enrollee;

public class DirectionsController extends DatabaseHandler {
    @FXML
    private Button edit_button_3=new Button();
    @FXML
    private ChoiceBox<String> firstDirection =new ChoiceBox<String>();

    @FXML
    private ChoiceBox<String> secondDirection =new ChoiceBox<String>();

    @FXML
    private ChoiceBox<String> thirdDirection =new ChoiceBox<String>();

    private String selectedSubject1="1"; //пусть по умолчанию 1
    private String selectedSubject2="1";
    private String selectedSubject3="1";

    private ArrayList<String> selectedDirections = new ArrayList<>();
    private final String[] massiveOfDirections = new String[]{"Прикладной матлаб", "Любимый Линал", "Киберспорт","Счастливая жизнь","Исследование НЛО и ракет","Успешный успех"};

    //private HashSet<String> listOfDirections = new HashSet<String>();
    public void ClickToEditSaveDirections(ActionEvent event)
    {
        edit_button_3.setText("Сохранить");
        firstDirection.setDisable(false);
        secondDirection.setDisable(false);
        thirdDirection.setDisable(false);
        ArrayList<String> editedDirections = new ArrayList<>();

        edit_button_3.setOnAction(event1 ->
        {
            try {


                try {
                    selectedSubject1 = firstDirection.getValue();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


                try {
                    selectedSubject2 = secondDirection.getValue();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


                try {
                    selectedSubject3 = thirdDirection.getValue();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    if (Objects.equals(selectedSubject1, selectedSubject2) |
                            Objects.equals(selectedSubject1, selectedSubject3) |
                            Objects.equals(selectedSubject2, selectedSubject3)) {
                        throw new Exception();
                    }

                    System.out.println("Вот что в первом направлении: " + selectedSubject1);
                    System.out.println("Вот что во втором направлении: " + selectedSubject2);
                    System.out.println("Вот что в третьем направлении: " + selectedSubject3);

                    if (selectedSubject1 != null) {
                        editedDirections.add(selectedSubject1);
                    }
                    if (selectedSubject2 != null) {
                        editedDirections.add(selectedSubject2);
                    }
                    if (selectedSubject3 != null) {
                        editedDirections.add(selectedSubject3);
                    }

                    System.out.println("Вот что попало в черновик: " + editedDirections);

                }
                selectedDirections = editedDirections;
                System.out.println("Вот что сохранилось в чистовик: " + selectedDirections);

                edit_button_3.setText("Редактировать");
                firstDirection.setDisable(true);
                secondDirection.setDisable(true);
                thirdDirection.setDisable(true);

                edit_button_3.setOnAction(this::ClickToEditSaveDirections);

                try {
                    Connection conn = getDbConection();
                    Statement statement = conn.createStatement();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gsonObj = gsonBuilder.create();
                    String JSONString = gsonObj.toJson(selectedDirections);

                    System.out.println("вот такой айдишник у " + enrollee.getEnrolleeFirstname() + enrollee.getIdEnrollee());

                    statement.executeUpdate("UPDATE enrollees SET directions=" + "'" + JSONString + "'" +
                            " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");

                    System.out.println("Вика ты засунула ");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("Values are the same");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("smth go wrong");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Ошибка при выборе направления!");
                alert.showAndWait();
            }
        });



    }

    @FXML
    public void initialize()
    {
        if(java.sql.Date.valueOf(String.valueOf(enrollee.dateNow)).before(Date.valueOf(String.valueOf(enrollee.lastDate))))
        {

            System.out.println("Пустые направления? "+NullInDirections());
            if(NullInDirections()){

                firstDirection.getItems().addAll(massiveOfDirections);
                secondDirection.getItems().addAll(massiveOfDirections);
                thirdDirection.getItems().addAll(massiveOfDirections);

                firstDirection.setDisable(true);
                secondDirection.setDisable(true);
                thirdDirection.setDisable(true);
                edit_button_3.setOnAction(this::ClickToEditSaveDirections);
            }
            else{
                selectedDirections=ArrayListInDirections();

                try
                {
                    selectedSubject1=selectedDirections.get(0);
                    firstDirection.setValue(selectedDirections.get(0));
                }catch (Exception e){e.getMessage();}
                try
                {
                    selectedSubject2=selectedDirections.get(1);
                    secondDirection.setValue(selectedDirections.get(1));
                }catch (Exception e){e.getMessage();}
                try
                {
                    selectedSubject3=selectedDirections.get(2);
                    thirdDirection.setValue(selectedDirections.get(2));
                }catch (Exception e){e.getMessage();}


                //и не забываем что надо добавить массив
                firstDirection.getItems().addAll(massiveOfDirections);
                secondDirection.getItems().addAll(massiveOfDirections);
                thirdDirection.getItems().addAll(massiveOfDirections);

                //и выключаем наших битиэс
                firstDirection.setDisable(true);
                secondDirection.setDisable(true);
                thirdDirection.setDisable(true);

                edit_button_3.setOnAction(this::ClickToEditSaveDirections); //не забываем про кнопОчку
            }

        }
        else{

            System.out.println("Пустые направления? "+NullInDirections());
            if(NullInDirections()){

                firstDirection.getItems().addAll(massiveOfDirections);
                secondDirection.getItems().addAll(massiveOfDirections);
                thirdDirection.getItems().addAll(massiveOfDirections);

                firstDirection.setDisable(true);
                secondDirection.setDisable(true);
                thirdDirection.setDisable(true);
                edit_button_3.setOnAction(this::ClickToEditSaveDirections);
            }
            else{
                selectedDirections=ArrayListInDirections();

                try
                {
                    selectedSubject1=selectedDirections.get(0);
                    firstDirection.setValue(selectedDirections.get(0));
                }catch (Exception e){e.getMessage();}
                try
                {
                    selectedSubject2=selectedDirections.get(1);
                    secondDirection.setValue(selectedDirections.get(1));
                }catch (Exception e){e.getMessage();}
                try
                {
                    selectedSubject3=selectedDirections.get(2);
                    thirdDirection.setValue(selectedDirections.get(2));
                }catch (Exception e){e.getMessage();}


                //и не забываем что надо добавить массив
                firstDirection.getItems().addAll(massiveOfDirections);
                secondDirection.getItems().addAll(massiveOfDirections);
                thirdDirection.getItems().addAll(massiveOfDirections);

                //и выключаем наших битиэс
                firstDirection.setDisable(true);
                secondDirection.setDisable(true);
                thirdDirection.setDisable(true);

                edit_button_3.setOnAction(this::ClickToEditSaveDirections); //не забываем про кнопОчку
            }

            edit_button_3.setDisable(true); //блокирую кнопочку

        }


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


        //жоска вытаскивает экзамены из базы и сразу преобразовывает в хэшмэп
    public ArrayList<String> ArrayListInDirections(){
        Statement statement = null;
        String foundString = "";

        ArrayList<String> arrayListFromBase=new ArrayList<>();

        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT directions FROM enrollees WHERE idEnrollee='"+enrollee.getIdEnrollee()+"'");
            if(resultSet.next())
            {
                foundString = resultSet.getString("directions");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот наши BTS из базы слева направо... "+foundString);
        //а так обратно в хешмэп

        Type type = arrayListFromBase.getClass();

        GsonBuilder gsonBuilder = new GsonBuilder(); //библиотеки гугла
        Gson gsonObj = gsonBuilder.create(); //это тоже

        arrayListFromBase=gsonObj.fromJson(foundString,type); //все супер все достали и оно норм хешмеп
        //System.out.println("new hashMap: "+ newHashMap);
        System.out.println("Вот они причёсанные "+arrayListFromBase);

        return arrayListFromBase;

    }



}
