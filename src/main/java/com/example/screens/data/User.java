package com.example.screens.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User extends DatabaseHandler {
    private Integer id; //id
    private String firstname; //имя
    private String lastname; //фамилия
    private String username; //login
    private String password; //password
    private String role; //роль

    public User(String firstname1, String lastname1, String username1, String password1, String role1)
    {

        this.firstname = firstname1;
        this.lastname = lastname1;
        this.username = username1;
        this.password = password1;
        this.role = role1;
    }
    public User()
    {
        id = 1000007;
        firstname = "Имя конструктора";
        lastname = "Фамилия конструктора";
        username ="Логин(юзернейм) конструктора";
        password = "ПарольКонструктора123";
        role = "Роль конструктора";
    }

    public String getFirstname() {
        return firstname;
    }

    public Integer GetUserIdFromTable(){
        Statement statement = null;
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT id FROM users WHERE " + "username" + " = " + "'"+ this.username + "'");
            if(resultSet.next())
            {
                setId(resultSet.getInt("id")); //добавили айди юзеру
            }


            System.out.println("вот айди нового юзера rjnjhsq pfhtufkcz");
            System.out.println(this.id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return this.id;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role1) {
        this.role = role1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void IdFromTable()//это просто функция для кайфа она ничего не возвращает
    {
        Statement statement = null;
        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT id FROM users WHERE " + "username" + " = " + "'"+ this.username + "'");
            if(resultSet.next())
            {
                setId(resultSet.getInt("id")); //добавили айди юзеру
            }


            System.out.println("вот айди юзера ");
            System.out.println(this.id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public String RoleFromTable(String login){
        Statement statement = null;
        String foundRole = "";
        try {
            statement = getDbConection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT role FROM users WHERE " + "username" + " = " + "'"+ login + "'");
            if(resultSet.next())
            {
                foundRole = resultSet.getString("role");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return foundRole;
    }

    public String FirstnameTable(String login) //взять имя из таблицы USER
    {
        Statement statement = null;
        String foundName = "";

        try {
            statement = getDbConection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT firstname FROM users WHERE " + "username" + " = " + "'"+ login + "'");

            if(resultSet.next())
            {
                foundName = resultSet.getString("firstname");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return foundName;
    }

    public String LastnameTable(String login) //взять фамилию из таблицы USER
    {
        Statement statement = null;
        String foundLastname = "";

        try {
            statement = getDbConection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT lastname FROM users WHERE " + "username" + " = " + "'"+ login + "'");
            if(resultSet.next())
            {
                foundLastname = resultSet.getString("lastname");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return foundLastname;
    }





}
