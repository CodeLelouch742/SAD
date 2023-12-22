package application;

import forms.Auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application
{
    public static void main(String[] args)
    {
        Auth auth = new Auth();
        try {
            getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        auth.pack();
        auth.setVisible(true);
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306","root","root");
    }
}
