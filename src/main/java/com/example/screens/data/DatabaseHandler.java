package com.example.screens.data;

import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;

public class DatabaseHandler extends Configs {
    Connection dbConection = null;

    public Connection getDbConection()
            throws ClassNotFoundException, SQLException {
        try {
            //String connectionString = "jdbc:postgresql://dpg-cekr665a4991ihi5gj30-a.ohio-postgres.render.com:5432/registerlogin";
            Class.forName("com.mysql.cj.jdbc.Driver"); //org.postgresql.Driver
            //Class.forName("org.postgresql.Driver");
            //"jdbc:mysql://localhost:3306/registerLogin"
            String connectionString ="jdbc:mysql://localhost:3306/registerLogin";
            dbConection = DriverManager.getConnection(connectionString , dbUser, dbPass);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.out.println("Ошибка в подключении к бд");
        }

        return dbConection;
    }

    public void signUpUser(User user) {

        String insertUser = "insert into" + " " + Const.USER_TABLE + "(" +
                Const.USERS_FIRSTAME + "," + Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
                Const.USERS_ROLE + ")" + "VALUES(?,?,?,?,?)";

        try
        {
            PreparedStatement prSt = getDbConection().prepareStatement(insertUser);

            prSt.setString(1, user.getFirstname());
            prSt.setString(2, user.getLastname());
            prSt.setString(3, user.getUsername());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getRole());

            prSt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {e.printStackTrace();}

    }

    public void signUpEnrollee(Enrollee enrollee,String someId) {

        String insertEnrollee = "insert into enrollees" + "(" +
                "idEnrollee" + "," + "status" +  "," + "firstname" + "," + "lastname" +  "," + "dateOfBirth"+")" + "VALUES(?,?,?,?,?)";

        try
        {
            Connection conn = getDbConection();
            Statement statement2 = conn.createStatement();

//            statement2.executeUpdate("UPDATE enrollees SET dateOfBirth=" + "'" + enrollee.getDateOfBirth() + "'" +
//                    " WHERE idEnrollee='" + enrollee.getIdEnrollee() + "'"); //ставится не та дата

            PreparedStatement prSt = getDbConection().prepareStatement(insertEnrollee);
            System.out.println("Пытаюсь положить вот это: "+someId);

            prSt.setString(1, someId);
            prSt.setString(2, enrollee.getStatus());
            prSt.setString(3, enrollee.getEnrolleeFirstname());
            prSt.setString(4, enrollee.getEnrolleeLastname());
            prSt.setDate(5, Date.valueOf(enrollee.getDateOfBirth()));



            prSt.executeUpdate();
            enrollee.setIdEnrollee(Integer.parseInt(someId));
            //enrollee.setDateOfBirth(LocalDate.now());
            System.out.println("id зарегистрированного юзера "+someId);

        } catch (SQLException | ClassNotFoundException e) {e.printStackTrace();}

    }

    public ResultSet getUserLoginPassword(User user) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConection().prepareStatement(select);
            prSt.setString(1, user.getUsername());
            prSt.setString(2, user.getPassword());


            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return resSet;
    }


}
