package forms;

import util.Medicines;
import util.Sells;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

public class FormTimeSells extends JFrame{

        public static FormTimeSells instance;
        private JButton showSellsTimeButton;
        private JButton returnButton;
        private JTextField dayStartSell;
        private JTextField monthStartSell;
        private JTextField yearStartSell;
        private JTextField dayEndSell;
        private JTextField monthEndSell;
        private JTextField yearEndSell;
        private JPanel formShowSellsTime;
        private JComboBox<String> nameMedicineTime;


    public FormTimeSells() {
        instance = this;

        for(String nameMedicineTimeString : Medicines.uniqueMedicines){
            nameMedicineTime.addItem(nameMedicineTimeString);
        }
            setContentPane(formShowSellsTime);
            setPreferredSize(new Dimension(700,500));
            setTitle("Добавление новой продажи");
            setVisible(true);

            instance.addWindowListener(exitWindowListener);

            showSellsTimeButton.addActionListener(showSellsTimeListener);
            returnButton.addActionListener(returnListener);

        }

        ActionListener showSellsTimeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LocalDate start = LocalDate.of(Integer.parseInt(yearStartSell.getText()), Integer.parseInt(monthStartSell.getText()), Integer.parseInt(dayStartSell.getText()));
                LocalDate end = LocalDate.of(Integer.parseInt(yearEndSell.getText()), Integer.parseInt(monthEndSell.getText()), Integer.parseInt(dayEndSell.getText()));

                if(Sells.isDateValid(dayStartSell.getText(), monthStartSell.getText(), yearStartSell.getText())
                        && (Sells.isDateValid(dayEndSell.getText(), monthEndSell.getText(), yearEndSell.getText())
                        && (end.isAfter(start)))){

                    Sells.getAllSellsMedicinesTimeInterval(dayStartSell.getText(),monthStartSell.getText(),yearStartSell.getText(),
                            dayEndSell.getText(), monthEndSell.getText(), yearEndSell.getText(), String.valueOf(nameMedicineTime.getSelectedItem()));
                        FormShowAllSellsTime formTimeSells = new FormShowAllSellsTime();
                        formTimeSells.setVisible(true);
                        formTimeSells.pack();
                        setVisible(false);
                        dispose();
                }else{
                    JOptionPane.showMessageDialog(instance,
                            "<html><h2 align=\"center\">Дата введена не корректно.</h2><p align=\"center\"> Введите новую дату.</p>");
                }
            }
        };

    ActionListener returnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MainMenu.instance.setVisible(true);
            dispose();
        }
    };

    WindowAdapter exitWindowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            MainMenu.instance.setVisible(true);
            dispose();
        }
    };
}

