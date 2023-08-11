package com.example.screens.data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Enrollee extends DatabaseHandler
{
    public LocalDate dateNow; //это для сроков подачи
    public LocalDate lastDate; //это тоже

    protected Integer idEnrollee;
    private int id;

    public int getId() {
        return idEnrollee;
    }

    private String enrolleeFirstname;
    private String enrolleeLastname;
    private String fio = enrolleeFirstname + " " + enrolleeLastname;


    public String getFio() {
        return enrolleeFirstname + " " + enrolleeLastname;
    }
    protected LocalDate dateOfBirth;
    protected String status;
    protected String whyUs; //тут текст который в общей инфе почему студент хочет учиться именно там
    protected String email;

    public String getWhyUs() {
        return whyUs;
    }

    public void setWhyUs(String whyUs) {
        this.whyUs = whyUs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected ArrayList<String> notifications = new ArrayList<>(); //тут типа массив уведомлений которые бдут грущитбся на экране уведрмлеий
    protected HashMap<Integer, String> exams = new HashMap<>();


    public Enrollee()
    {
        this.idEnrollee=9000;

        this.enrolleeFirstname="имя конструкторАб";
        this.enrolleeLastname="Фамилия конструкторАб";

        this.whyUs = "Потому что я самый лучший в мире nсудент и вы самый лучший в мире вуз и все такое ";
        this.notifications.add("Добро пожаловать! Заполните информацию во всех разделах до окончания срока приёма документов!");

        this.dateOfBirth = LocalDate.now();
        this.status = "Статус конструктора";

        this.exams.put(1,"Предмет конструктора1");
        this.exams.put(10,"Предмет конструктора2");

        this.dateNow = dateNowFromTable(); //сразу прыгает сюда дата при так что все ок
        this.lastDate = lastDateFromTable(); //и сюда

    }


    public Enrollee(Integer newId, String newFirstname, String newLastname, LocalDate dateOfBirth1,String email1,
                    String status1, String whyUs1)
    {

        this.idEnrollee=newId;
        this.enrolleeFirstname=newFirstname;
        this.enrolleeLastname=newLastname;
        this.dateOfBirth = dateOfBirth1;
        this.status = status1;
        this.whyUs = whyUs1;
        this.email =email1;



    }

    @Override
    public String toString() {
        return "id - " + idEnrollee
                + " firstname - " + getEnrolleeFirstname()
                + " firstname - " + getEnrolleeFirstname()
                + " dateOfBirth - " + getDateOfBirth().toString()
                + " status - " + getStatus()
                + " whyUs - " + whyUs
                + " email - " + email;
    }


    public LocalDate getDateOfBirth() {return dateOfBirth;}
    public void setDateOfBirth(LocalDate dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}


    public HashMap getExams() {return exams;}
    public void setExams(HashMap<Integer,String> newExams) {this.exams=newExams;}

    public ArrayList getNotifications() {return notifications;}
    public void setNotification(String newNotification) {this.notifications.add(newNotification);}

    public void setIdEnrollee(Integer id){idEnrollee=id;}
    public Integer getIdEnrollee(){return this.idEnrollee;}


    public LocalDate dateNowFromTable()
    {

        Statement statement = null;
        LocalDate foundDate = null;
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT datenow FROM notificationsForAll WHERE text = 'Добро пожаловать!'");
            //WHERE idEnrollee='"+enrollee.getIdEnrollee()+"'"
            if(resultSet.next())
            {
                foundDate = resultSet.getDate("datenow").toLocalDate();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Вот наша воображаемая дата сейчас: "+foundDate);

        return foundDate;
    }

    public LocalDate lastDateFromTable()
    {

        Statement statement = null;
        LocalDate foundDate=null;
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT lastdate FROM notificationsForAll WHERE text = 'Добро пожаловать!' ");
            if(resultSet.next())
            {
                foundDate = resultSet.getDate("lastdate").toLocalDate();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Абитура может редактировать инфу до: "+foundDate);

        return foundDate;
    }

    public String getEnrolleeFirstname() {
        return enrolleeFirstname;
    }

    public void setEnrolleeFirstname(String enrolleeFirstname) {
        this.enrolleeFirstname = enrolleeFirstname;
    }

    public String getEnrolleeLastname() {
        return enrolleeLastname;
    }

    public void setEnrolleeLastname(String enrolleeLastname) {
        this.enrolleeLastname = enrolleeLastname;
    }




}
