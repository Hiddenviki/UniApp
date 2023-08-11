package com.example.screens.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin extends DatabaseHandler
{
    private String firstNameAdmin;
    private String lastNameAdmin;
    private Integer idAdmin;

    public Admin()
    {
        //на всякий случай сначала присваиваю чета
        this.firstNameAdmin="Имя админа из конструктора";
        this.lastNameAdmin ="Фамилия админа из конструктора";
        this.idAdmin=1007;
    }

    public Admin(Integer id1)
    {
        this.idAdmin = id1;
        this.firstNameAdmin=FirstnameAdminFromTable();
        this.lastNameAdmin=LastnameAdminFromTable();

    }

    private String FirstnameAdminFromTable(){
        Statement statement = null;
        String foundRole = "";
        try {
            statement = getDbConection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT firstname FROM users WHERE " + "id" + " = " + "'"+ this.idAdmin + "'");
            if(resultSet.next())
            {
                foundRole = resultSet.getString("firstname");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return foundRole;
    }

    private String LastnameAdminFromTable(){
        Statement statement = null;
        String foundRole = "";
        try {
            statement = getDbConection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT lastname FROM users WHERE " + "id" + " = " + "'"+ this.idAdmin + "'");
            if(resultSet.next())
            {
                foundRole = resultSet.getString("lastname");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return foundRole;
    }


    public String getLastNameAdmin() {
        return lastNameAdmin;
    }

    public void setLastNameAdmin(String lastNameAdmin) {
        this.lastNameAdmin = lastNameAdmin;
    }

    public String getFirstNameAdmin() {
        return firstNameAdmin;
    }

    public void setFirstNameAdmin(String firstNameAdmin) {
        this.firstNameAdmin = firstNameAdmin;
    }

    public Integer getidAdmin() {
        return idAdmin;
    }

    public void setidAdmin(Integer newId) {
        this.idAdmin = newId;
    }


}
