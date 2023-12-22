package forms;

import application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Auth extends JFrame {

    public static Auth authInstance;
    private JPanel mainPanel;
    private JTextField login;
    private JButton auth;
    private JPasswordField pass;
    private JLabel massageLogin;
    private JLabel massagePass;


    public Auth(){
        authInstance = this;
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(600,500));
        setTitle("Вход в систему");

        authInstance.addWindowListener(exitWindowListener);
        auth.addActionListener(authListener);
    }
    ActionListener authListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (login.getText().equals("111") && pass.getText().equals("111")){
                try {
                    checkTables();
                } catch (SQLException e) {
                }
                MainMenu mainmenu = new MainMenu();
                mainmenu.pack();
                mainmenu.setVisible(true);
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(authInstance,
                        "<html><h2 align=\"center\">Не верный логин или пароль.</h2><p align=\"center\"> Введите их снова.</p>");
            }
        }
    };

    WindowAdapter exitWindowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            System.exit(0);
        }
    };

    public static void checkTables() throws SQLException {
        try(Connection connection = Application.getConnection()){
            Statement statement = connection.createStatement();
            String addDataBase = "CREATE DATABASE IF NOT EXISTS pharmacy";
            statement.execute(addDataBase);
            statement.execute("use pharmacy");
            String addTableMedicine = "CREATE TABLE IF NOT EXISTS Medicine(NameMedicine text NOT NULL, NameDisease text NOT NULL, Price text NOT NULL, Quantity text NOT NULL)";
            statement.execute(addTableMedicine);
            String addTableSell = "CREATE TABLE IF NOT EXISTS Sell(NameMedicine text NOT NULL, QuantitySell text NOT NULL, DaySell text NOT NULL, MonthSell text NOT NULL, YearSell text NOT NULL)";
            statement.execute(addTableSell);
        }
    }
}
