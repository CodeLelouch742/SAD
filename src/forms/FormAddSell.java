package forms;

import manager.MedicineEntityManager;
import manager.SellEntityManager;
import util.Medicine;
import util.Medicines;
import util.Sell;
import util.Sells;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;


public class FormAddSell extends JFrame {

    public static FormAddSell instance;
    private JButton addNewSellButton;
    private JButton returnButton;
    private JTextField quantitySell;
    private JTextField daySell;
    private JTextField monthSell;
    private JTextField yearSell;

    private JComboBox<String> nameMedicine;

    private JPanel formSell;
    private JLabel errorQuantitySallLable;


    public FormAddSell() {
        instance = this;
        for(String nameMedicineUnique : Medicines.uniqueMedicines){
            nameMedicine.addItem(nameMedicineUnique);
        }

        setContentPane(formSell);
        setPreferredSize(new Dimension(700,500));
        setTitle("Добавление новой продажи");
        setVisible(true);

        instance.addWindowListener(exitWindowListener);

        addNewSellButton.addActionListener(addNewSell);
        returnButton.addActionListener(returnListener);

        quantitySell.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                try{
                    int i = Integer.parseInt(quantitySell.getText());
                    errorQuantitySallLable.setText("");

                }catch (NumberFormatException e1){
                    errorQuantitySallLable.setText("Не верный формат данных!");
                }
            }
        });


    }

    ActionListener addNewSell = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(Sells.isDateValid(daySell.getText(), monthSell.getText(), yearSell.getText())
                    && (Sells.getNowDate().isAfter(LocalDate.of(Integer.parseInt(yearSell.getText()), Integer.parseInt(monthSell.getText()), Integer.parseInt(daySell.getText())))
                    || Sells.getNowDate().isEqual(LocalDate.of(Integer.parseInt(yearSell.getText()), Integer.parseInt(monthSell.getText()), Integer.parseInt(daySell.getText()))))){
                if (!(Integer.parseInt(quantitySell.getText()) < 0)){
                    Sell sell = new Sell(String.valueOf(nameMedicine.getSelectedItem()), quantitySell.getText(), daySell.getText(), monthSell.getText(), yearSell.getText());
                    for (Medicine m : Medicines.medicines) {
                        if (m.getNameMedicine().equalsIgnoreCase(String.valueOf(nameMedicine.getSelectedItem()))){
                            int newQuantity = Integer.parseInt(m.getQuantity()) - Integer.parseInt(quantitySell.getText());
                            if(newQuantity < 0){
                                JOptionPane.showMessageDialog(instance,
                                        "<html><h2 align=\"center\">Ошибка количества лекарств.</h2><p align=\"center\"> На складе нет введённого количества лекарств.</p>");
                                break;
                            }else{
                                m.setQuantity(String.valueOf(newQuantity));
                                Medicines.setMedicineByName(m, m.getNameMedicine());
                                try {
                                    MedicineEntityManager.update(m, m.getNameMedicine());
                                } catch (SQLException e) {

                                }
                                Sells.sells.add(sell);
                                try {
                                    SellEntityManager.insert(sell);
                                } catch (SQLException e) {

                                }
                                MainMenu.instance.setVisible(true);
                                dispose();
                                break;
                            }
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(instance,
                            "<html><h2 align=\"center\">Неверное количество проданных лекарств.</h2><p align=\"center\"> Введите корректное число.</p>");
                }
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
