package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
}
