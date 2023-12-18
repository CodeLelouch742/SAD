package forms;

import util.CustomTableModelMedDisease;
import util.Medicines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormShowMedicineDisease extends JFrame {

    public static FormShowMedicineDisease instance;
    private JTable allMedicineDiseaseTable;
    private JButton returnButton;
    private JPanel allMedicineDiseasePanel;

    public FormShowMedicineDisease()
    {
        instance = this;
        setContentPane(allMedicineDiseasePanel);
        setPreferredSize(new Dimension(1200,800));
        setTitle("Таблица со всеми лекарствами по введённой болезне");
        setVisible(true);

        allMedicineDiseaseTable.setModel(new CustomTableModelMedDisease());
        allMedicineDiseaseTable.getTableHeader().setReorderingAllowed(false);

        instance.addWindowListener(exitWindowListener);

        returnButton.addActionListener(returnListener);
    }

    ActionListener returnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MainMenu.instance.setVisible(true);
            dispose();
            Medicines.removeArrayMedicinesByDisease();
        }
    };

    WindowAdapter exitWindowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            MainMenu.instance.setVisible(true);
            dispose();
            Medicines.removeArrayMedicinesByDisease();
        }
    };
}
