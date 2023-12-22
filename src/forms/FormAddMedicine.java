package forms;

import manager.MedicineEntityManager;
import util.Medicine;
import util.Medicines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;


public class FormAddMedicine extends JFrame {

    public static FormAddMedicine instance;
    private JButton addNew;
    private JTextField nameMedicine;
    private JTextField illness;
    private JTextField price;
    private JTextField quantity;

    private JPanel formMedicine;
    private JButton returnButton;
    private JLabel errorPriceLable;
    private JLabel errorQuantityLable;


    public FormAddMedicine() {
        instance = this;
        setContentPane(formMedicine);
        setPreferredSize(new Dimension(700,500));
        setTitle("Добавление нового лекарства");
        setVisible(true);

        instance.addWindowListener(exitWindowListener);

        addNew.addActionListener(addNewMedicine);
        returnButton.addActionListener(returnListener);


        price.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                try{
                    int i = Integer.parseInt(price.getText());
                    errorPriceLable.setText("");

                }catch (NumberFormatException e1){
                    errorPriceLable.setText("Не верный формат данных!");
                }
            }
        });

        quantity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                try{
                    int i = Integer.parseInt(quantity.getText());
                    errorQuantityLable.setText("");

                }catch (NumberFormatException e1){
                    errorQuantityLable.setText("Не верный формат данных!");
                }
            }
        });
    }

    ActionListener addNewMedicine = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(!(Integer.parseInt(quantity.getText()) < 0 && Integer.parseInt(price.getText()) < 0)){
                if(!(Medicines.checkNameMedicineList(nameMedicine.getText()))){
                    Medicines.uniqueMedicines.add(nameMedicine.getText());
                    Medicine medicine = new Medicine(nameMedicine.getText(), illness.getText(), price.getText(), quantity.getText());
                    Medicines.medicines.add(medicine);
                    try {
                        MedicineEntityManager.insert(medicine);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    MainMenu.instance.setVisible(true);
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(instance,
                            "<html><h2 align=\"center\">Лекарство с таким навазинем уже существует.</h2><p align=\"center\"> Введите корректное название или исправьте данные об уже существуещем.</p>");
                }
            }else {
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
