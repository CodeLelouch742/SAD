package forms;

import util.CustomTableModelMedicine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormShowAllMedicine extends JFrame{

    public static FormShowAllMedicine instance;
    private JTable allMedicineTable;
    private JButton returnButton;
    private JPanel allMedicinePanel;



    public FormShowAllMedicine()
    {
        instance = this;
        setContentPane(allMedicinePanel);
        setPreferredSize(new Dimension(1200,800));
        setTitle("Таблица со всеми лекарствами");
        setVisible(true);

        instance.addWindowListener(exitWindowListener);

        allMedicineTable.setModel(new CustomTableModelMedicine());
        allMedicineTable.getTableHeader().setReorderingAllowed(false);

        returnButton.addActionListener(returnListener);
    }

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
