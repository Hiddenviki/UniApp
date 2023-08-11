package com.example.screens.data.enrolleeControllers;

import com.example.screens.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;


public class MainInfoController extends HelloController
{

    @FXML
    private Text fioField; //ЭТО РЕДАКТИРОВАТЬ НЕЛЬЗЯ


    @FXML
    private Button edit_button_1;
    @FXML
    private DatePicker dateOfBirthDatePicker;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea whyUsField;

    private LocalDate newDate = null;



    public void clickToEditSaveMainInfo(ActionEvent event) {
        dateOfBirthDatePicker.setEditable(true);
        //asd
        dateOfBirthDatePicker.setDisable(false);
        emailField.setEditable(true);
        whyUsField.setEditable(true);
        edit_button_1.setText("Сохранить");

        dateOfBirthDatePicker.setOnAction(actionEvent1 ->
        {
            if (dateOfBirthDatePicker != null) {
                newDate = dateOfBirthDatePicker.getValue();

                System.out.println(newDate);
            }



        });
//
        edit_button_1.setOnAction(actionEvent ->
        {
            try {
                if (Objects.equals(emailField.getText(), "") | !emailField.getText().contains("@") | !emailField.getText().contains(".")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error in email field");
                    alert.setHeaderText("Email field exception");
                    alert.setContentText("Please type correct email");
                    alert.showAndWait();
                    throw new Exception("em");
                }
                if (Objects.equals(whyUsField.getText(), "")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка в поле О себе");
                    alert.setHeaderText("Ошибка в поле О себе");
                    alert.setContentText("Введено пустое поле");
                    alert.showAndWait();
                    throw new Exception();
                }
                edit_button_1.setText("Редактировать");
                try {
                    Connection conn = getDbConection();
                    Statement statement = conn.createStatement();

                    System.out.println("вот такой айдишник у " + enrollee.getEnrolleeFirstname() + enrollee.getIdEnrollee());

                    statement.executeUpdate("UPDATE enrollees SET dateOfBirth=" + "'" + dateOfBirthDatePicker.getValue() + "'" +
                            " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'"); //ставится не та дата

                    statement.executeUpdate("UPDATE enrollees SET whyUs=" + "'" + whyUsField.getText() + "'" +
                            " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");

                    statement.executeUpdate("UPDATE enrollees SET email=" + "'" + emailField.getText() + "'" +
                            " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");

                    System.out.println("Вика ты засунула в mainInfo почту "+emailField.getText()+" почему мы "+whyUsField.getText()+" дата рождения "+dateOfBirthDatePicker.getValue());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                emailField.setEditable(false);
                whyUsField.setEditable(false);

                dateOfBirthDatePicker.setEditable(false);
                dateOfBirthDatePicker.setDisable(true);

                edit_button_1.setOnAction(this::clickToEditSaveMainInfo);
            } catch (Exception e) {
                System.out.println("err in em or whyus textfield");
            }
        });


        try {
            Connection conn = getDbConection();
            Statement statement = conn.createStatement();

            System.out.println("вот такой айдишник у " + enrollee.getEnrolleeFirstname() + enrollee.getIdEnrollee());

            statement.executeUpdate("UPDATE enrollees SET dateOfBirth=" + "'" + dateOfBirthDatePicker.getValue() + "'" +
                    " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'"); //ставится не та дата

            System.out.println(whyUsField.getText()); //в переменные ничего не записано
            System.out.println(emailField.getText());

            //поэтому тут не работает
            statement.executeUpdate("UPDATE enrollees SET whyUs=" + "'" + whyUsField.getText() + "'" +
                    " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");

            statement.executeUpdate("UPDATE enrollees SET email=" + "'" + emailField.getText() + "'" +
                    " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'");

            System.out.println("Вика ты засунула в mainInfo почту "+emailField.getText()+" почему мы "+whyUsField.getText()+" дата рождения "+dateOfBirthDatePicker.getValue());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public MainInfoController()
    {
        System.out.println("а теперь смотрим что у нас тут творится...");
        //System.out.println("Передали в аргументе: "+AnyEnrollee.getEnrolleeFirstname());
        //enrollee=AnyEnrollee;
        System.out.println("Передали ИЗ аргумента в переменную: "+enrollee.getEnrolleeFirstname());
        //вроде все
        fioField=new Text();


    }

    @FXML
    public void initialize()
    {


        if(Date.valueOf(String.valueOf(enrollee.dateNow)).before(Date.valueOf(String.valueOf(enrollee.lastDate))))
        {


            fioField.setText(enrollee.getEnrolleeFirstname()+" "+enrollee.getEnrolleeLastname());

            if (NullInDateOfBirth()==true){
                dateOfBirthDatePicker.setValue(LocalDate.now());
            }
            else{
                newDate= DateFromBase();
                dateOfBirthDatePicker.setValue(newDate);
            }

            if (NullInEmail()==false)
            {
                emailField.setText(EmailFromBase());
            }

            if (NullInWhyUs()==false){
                whyUsField.setText(WhyUsFromBase());
            }

//        dateOfBirthDatePicker.setEditable(false);
            dateOfBirthDatePicker.setDisable(true);
            emailField.setEditable(false);
            whyUsField.setEditable(false);

        }
        else
        {
            fioField.setText(enrollee.getEnrolleeFirstname()+" "+enrollee.getEnrolleeLastname());

            if (NullInDateOfBirth()==true){
                dateOfBirthDatePicker.setValue(LocalDate.now());
            }
            else{
                newDate= DateFromBase();
                dateOfBirthDatePicker.setValue(newDate);
            }

            if (NullInEmail()==false)
            {
                emailField.setText(EmailFromBase());
            }

            if (NullInWhyUs()==false){
                whyUsField.setText(WhyUsFromBase());
            }

            //если дата уже все то редактировать нельзя
            dateOfBirthDatePicker.setDisable(true);
            emailField.setEditable(false);
            whyUsField.setEditable(false);

            edit_button_1.setDisable(true); //выключаем кнопку

        }



    }


    public boolean NullInWhyUs()
    {
        Statement statement = null;
        String foundNULL = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT whyUs FROM enrollees WHERE idEnrollee='"+enrollee.getIdEnrollee()+"'");
            if(resultSet.next())
            {
                foundNULL = resultSet.getString("whyUs");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот вижу что тут в 'why us?': "+foundNULL);
        return Objects.equals(foundNULL, "");

    }

    public boolean NullInEmail()
    {
        Statement statement = null;
        String foundNULL = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT email FROM enrollees WHERE idEnrollee='"+enrollee.getIdEnrollee()+"'");
            if(resultSet.next())
            {
                foundNULL = resultSet.getString("email");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот вижу что тут в email: "+foundNULL);
        return Objects.equals(foundNULL, "");

    }


    public boolean NullInDateOfBirth() //смотрит пустая клеточка даты рождения в базе или нет
    {
        Statement statement = null;
        String foundNULL = "";
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT dateOfBirth FROM enrollees WHERE idEnrollee='"+enrollee.getIdEnrollee()+"'");
            if(resultSet.next())
            {
                foundNULL = resultSet.getString("dateOfBirth");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот вижу что тут в дате рождения: "+foundNULL);
        return Objects.equals(foundNULL, null);

    }


    public LocalDate DateFromBase(){
        Statement statement = null;
        LocalDate foundDate = null;


        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT dateOfBirth FROM enrollees WHERE idEnrollee='"+enrollee.getIdEnrollee()+"'");
            if(resultSet.next())
            {
                foundDate = resultSet.getDate("dateOfBirth").toLocalDate();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот наш Чимин (дата рождения)... "+foundDate);



        return foundDate;

    }

    public String WhyUsFromBase(){
        Statement statement = null;
        String foundString = "";

        String whyUsFromBase;

        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT whyUs FROM enrollees WHERE idEnrollee='"+enrollee.getIdEnrollee()+"'");
            if(resultSet.next())
            {
                foundString = resultSet.getString("whyUs");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот наш Реп монстр (почему мы)... "+foundString);
        whyUsFromBase= foundString;


        return whyUsFromBase;

    }

    public String EmailFromBase()
    {
        Statement statement = null;
        String foundString = "";

        String emailFromBase;

        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT email FROM enrollees WHERE idEnrollee='"+enrollee.getIdEnrollee()+"'");
            if(resultSet.next())
            {
                foundString = resultSet.getString("email");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Вот наш Чогук (email)... "+foundString);
        emailFromBase= foundString;


        return emailFromBase;

    }


}