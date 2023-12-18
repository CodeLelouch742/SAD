package forms;

import util.Medicine;
import util.Medicines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditFormMedicine extends JFrame {

    public static EditFormMedicine instance;
    private JButton editMedicine;
    private JTextField editNameDisease;
    private JTextField editPrice;
    private JTextField editQuantity;
    private JPanel editFormMedicine;
    private JButton returnButton;
    private JTextField editNameMedicine;
    private JLabel errorPriceLable;
    private JLabel errorQuantityLable;

    public static String nameMedicine;

    private Medicine medicine;

    public EditFormMedicine(String name){
        instance = this;

        nameMedicine = name;

        setContentPane(editFormMedicine);
        setPreferredSize(new Dimension(700,500));
        setTitle("Редактирование лекарства");
        medicine = Medicines.getUserByNickname(name);

        instance.addWindowListener(exitWindowListener);

        editMedicine.addActionListener(editMedicineListener);
        returnButton.addActionListener(returnListener);

        editPrice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                try{
                    int i = Integer.parseInt(editPrice.getText());
                    errorPriceLable.setText("");

                }catch (NumberFormatException e1){
                    errorPriceLable.setText("Не верный формат данных!");
                }
            }
        });

        editQuantity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                try{
                    int i = Integer.parseInt(editQuantity.getText());
                    errorQuantityLable.setText("");

                }catch (NumberFormatException e1){
                    errorQuantityLable.setText("Не верный формат данных!");
                }
            }
        });

    }

    ActionListener editMedicineListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if(!(editNameDisease.getText().isEmpty())){
                medicine.setNameMedicine(editNameMedicine.getText());
            }

            if(!(editNameDisease.getText().isEmpty())){
                medicine.setNameDisease(editNameDisease.getText());
            }

            if(!(editPrice.getText().isEmpty())){
                medicine.setPrice(editPrice.getText());
            }

            if(!(editQuantity.getText().isEmpty())){
                medicine.setQuantity(editQuantity.getText());
            }
            if(!(Integer.parseInt(editQuantity.getText()) < 0 && Integer.parseInt(editPrice.getText()) < 0)){
                Medicines.setMedicineByName(medicine);
                int index = 0;
                for(String string : Medicines.uniqueMedicines){
                    if (nameMedicine.equalsIgnoreCase(string)){
                        Medicines.uniqueMedicines.set(index, editNameMedicine.getText());
                    }
                    index++;
                }
                MainMenu.instance.setVisible(true);
                dispose();
            }else{
                JOptionPane.showMessageDialog(instance,
                        "<html><h2 align=\"center\">Ввод данных меньше нуля.</h2><p align=\"center\"> Введите коректные данные.</p>");
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
