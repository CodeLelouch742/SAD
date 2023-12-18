package forms;

import util.CustomTableModelMedTime;
import util.Sells;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormShowAllSellsTime extends JFrame{

    public static FormShowAllSellsTime instance;
    private JTable allSellTimeTable;
    private JButton returnButton;
    private JPanel allSellTimePanel;


    public FormShowAllSellsTime()
    {
        instance = this;
        setContentPane(allSellTimePanel);
        setPreferredSize(new Dimension(1200,800));
        setTitle("Таблица со всеми продажами за определённое время.");
        setVisible(true);

        instance.addWindowListener(exitWindowListener);

        allSellTimeTable.setModel(new CustomTableModelMedTime());
        allSellTimeTable.getTableHeader().setReorderingAllowed(false);

        returnButton.addActionListener(returnListener);
    }

    ActionListener returnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Sells.sellsMedicinesTimeInterval.clear();
            MainMenu.instance.setVisible(true);
            dispose();
        }
    };

    WindowAdapter exitWindowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            Sells.sellsMedicinesTimeInterval.clear();
            MainMenu.instance.setVisible(true);
            dispose();
        }
    };
}
