package forms;

import util.Medicine;
import util.Medicines;
import util.Sell;
import util.Sells;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JFrame
{
    public static MainMenu instance;

    private JPanel mainMenu;
    private JButton newMedicine;
    private JButton show;
    private JButton dropMedicine;
    private JTextField nameMedicine;
    private JButton edit;
    private JTextField editMedicine;
    private JButton dropAllMedicines;
    private JTextField nameDisease;
    private JButton showAllMedicineByDiseaseButton;
    private JButton sellByTimeButton;
    private JButton sellByMonthButton;
    private JButton showAllSellsButton;
    private JButton addSellButton;
    private JButton writeInFileMedicinesButton;
    private JButton writeInFileSellsButton;
    private JTextField pathFileMedicines;
    private JButton readFileMedicinesButton;
    private JTextField pathFileSells;
    private JButton readFileSellsButton;
    private JButton writeInXMLMedicinesButton;

    private JButton writeInXMLSellsButton;

    private JTextField pathXMLFileMedicines;
    private JButton readFileXMLMedicinesButton;
    private JTextField pathXMLFileSells;
    private JButton readFileXMLSellsButton;


    public MainMenu()
    {
        instance = this;
        setContentPane(mainMenu);
        setPreferredSize(new Dimension(1200,700));
        setTitle("Главное меню");
        newMedicine.addActionListener(addMedicine);
        show.addActionListener(showAllMedicines);
        dropMedicine.addActionListener(removeMedicine);
        edit.addActionListener(editMedicineListener);
        dropAllMedicines.addActionListener(dropEveryMedicines);
        showAllMedicineByDiseaseButton.addActionListener(showAllMedicineByDisease);
        showAllSellsButton.addActionListener(showAllSells);
        addSellButton.addActionListener(addSell);
        sellByTimeButton.addActionListener(sellByTimeListener);
        sellByMonthButton.addActionListener(sellByMonthListener);
        writeInFileMedicinesButton.addActionListener(writeMedicinesInFile);
        writeInFileSellsButton.addActionListener(writeSellsInFile);
        readFileMedicinesButton.addActionListener(readFileMedicines);
        readFileSellsButton.addActionListener(readFileSells);
        writeInXMLMedicinesButton.addActionListener(writeXMLFileMedicines);
        writeInXMLSellsButton.addActionListener(writeXMLFileSells);
        readFileXMLMedicinesButton.addActionListener(readXMLFileMedicines);
        readFileXMLSellsButton.addActionListener(readXMLFileSells);


        instance.addWindowListener(exitWindowListener);

        String messageMedicine = "<html> Введите название лекарства <br></html>";
        String messageDisease = "<html> Введите название болезни <br></html>";
        editMedicine.setToolTipText(messageMedicine);
        nameMedicine.setToolTipText(messageMedicine);
        nameDisease.setToolTipText(messageDisease);

    }

    int numberThread = -1;

    public class NewThread extends Thread {
        public void run()
        {
            switch(numberThread) {
                case 0:
                    Medicines.writeInFileMedicines();
                    break;
                case 1:
                    Sells.writeInFileSells();
                    break;
                case 2:
                    int finishReadMedicines = Medicines.readOutFileMedicines(pathFileMedicines.getText());
                    switch (finishReadMedicines){
                        case 0:
                            for(Medicine medicine : Medicines.readMedicines){
                                if(!(Medicines.uniqueMedicines.contains(medicine.getNameMedicine()))){
                                    Medicines.uniqueMedicines.add(medicine.getNameMedicine());
                                }
                                System.out.println(medicine.toString());
                                Medicines.medicines.add(medicine);
                            }
                            Medicines.readMedicines.clear();
                            JOptionPane.showMessageDialog(MainMenu.instance,
                                    "<html><h2 align=\"center\">Файл прочитан успешно.</h2><p align=\"center\"> Данные загружены в список лекарств</p>");
                            break;
                        case 1:
                            JOptionPane.showMessageDialog(MainMenu.instance,
                                    "<html><h2 align=\"center\">Встречена ошибка данных файла</h2><p align=\"center\"> Проверьте правильность данных в файле</p>");
                            break;
                    }
                    break;
                case 3:
                    int finishReadSells = Sells.readOutFileSells(pathFileSells.getText());
                    switch (finishReadSells){
                        case 0:
                            for(Sell sell : Sells.readSells){
                                System.out.println(sell.toString());
                                Sells.sells.add(sell);
                            }
                            Sells.readSells.clear();
                            JOptionPane.showMessageDialog(MainMenu.instance,
                                    "<html><h2 align=\"center\">Файл прочитан успешно.</h2><p align=\"center\"> Данные загружены в список продаж</p>");
                            break;
                        case 1:
                            JOptionPane.showMessageDialog(MainMenu.instance,
                                    "<html><h2 align=\"center\">Встречена ошибка данных файла</h2><p align=\"center\"> Проверьте правильность данных в файле</p>");
                            break;
                    }
                    break;
                case 4:
                    Medicines.writeXmlMedicines();
                    break;
                case 5:
                    Sells.writeXmlSells();
                    break;
                case 6:
                    Medicines.readXmlMedicines(pathXMLFileMedicines.getText());
                    break;
                case 7:
                    Sells.readXmlSells(pathXMLFileSells.getText());
                    break;
                default:
                    break;
            }
        }

    }

    ActionListener removeMedicine = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!(nameMedicine.getText().isEmpty()) && Medicines.checkNameMedicineList(nameMedicine.getText())){
                Medicines.removeByNameMedicine(nameMedicine.getText());
                JOptionPane.showMessageDialog(instance,
                        "<html><h2 align=\"center\">Лекарство удалено из базы");

            }else{
                JOptionPane.showMessageDialog(instance,
                        "<html><h2 align=\"center\">Введённое лекарство отсутствует или поле пустое.</h2><p align=\"center\"> Введите корректное название лекарства.</p>");
            }
        }
    };

    ActionListener dropEveryMedicines = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Medicines.removeArrayMedicines();
        }
    };

    ActionListener editMedicineListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!(editMedicine.getText().isEmpty()) && Medicines.checkNameMedicineList(editMedicine.getText())){
                EditFormMedicine editForm = new EditFormMedicine(editMedicine.getText());
                editForm.setVisible(true);
                editForm.pack();
                setVisible(false);
            }else{
                JOptionPane.showMessageDialog(instance,
                                "<html><h2 align=\"center\">Введённое лекарство отсутствует или поле пустое.</h2><p align=\"center\"> Введите корректное название лекарства.</p>");
            }
        }
    };

    ActionListener showAllMedicines = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FormShowAllMedicine formShowAllMedicine = new FormShowAllMedicine();
            formShowAllMedicine.setVisible(true);
            formShowAllMedicine.pack();
            setVisible(false);
            dispose();
        }
    };

    ActionListener addMedicine = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FormAddMedicine formMedicine = new FormAddMedicine();
            formMedicine.setVisible(true);
            formMedicine.pack();
            setVisible(false);
            dispose();
        }
    };

    ActionListener addSell = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            FormAddSell formSell = new FormAddSell();
            formSell.setVisible(true);
            formSell.pack();
            setVisible(false);
        }
    };

    ActionListener showAllMedicineByDisease = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Medicines.getAllMedicineByDisease(nameDisease.getText());
            FormShowMedicineDisease formShowMedicineDisease = new FormShowMedicineDisease();
            formShowMedicineDisease.setVisible(true);
            formShowMedicineDisease.pack();
            setVisible(false);
            dispose();
        }
    };

    ActionListener showAllSells = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FormShowAllSells formShowAllSells = new FormShowAllSells();
            formShowAllSells.setVisible(true);
            formShowAllSells.pack();
            setVisible(false);
            dispose();
        }
    };

    ActionListener sellByTimeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FormTimeSells formTimeSells = new FormTimeSells();
            formTimeSells.setVisible(true);
            formTimeSells.pack();
            setVisible(false);
        }
    };

    ActionListener sellByMonthListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(MainMenu.this,
                    "<html><h2 align=\"center\">Общая сумма продаж за месяц:</h2><p align=\"center\">"+ Sells.getMonthlySalesAmount() +" руб.</p>");
        }
    };

    WindowAdapter exitWindowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            Auth auth = new Auth();
            auth.pack();
            auth.setVisible(true);
            dispose();
        }
    };

    ActionListener writeMedicinesInFile = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            numberThread = 0;
            NewThread thread = new NewThread();
            thread.start();

        }
    };

    ActionListener writeSellsInFile = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            numberThread = 1;
            NewThread thread = new NewThread();
            thread.start();
        }
    };

    ActionListener readFileMedicines = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            numberThread = 2;
            NewThread thread = new NewThread();
            thread.start();
        }
    };
    ActionListener readFileSells = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            numberThread = 3;
            NewThread thread = new NewThread();
            thread.start();
        }
    };

    ActionListener writeXMLFileMedicines = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            numberThread = 4;
            NewThread thread = new NewThread();
            thread.start();
        }
    };

    ActionListener writeXMLFileSells = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            numberThread = 5;
            NewThread thread = new NewThread();
            thread.start();
        }
    };

    ActionListener readXMLFileMedicines = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            numberThread = 6;
            NewThread thread = new NewThread();
            thread.start();
        }
    };

    ActionListener readXMLFileSells = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            numberThread = 7;
            NewThread thread = new NewThread();
            thread.start();
        }
    };
}
