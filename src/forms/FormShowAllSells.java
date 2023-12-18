package forms;

import util.CustomTableModelSell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormShowAllSells extends JFrame{

    public static FormShowAllSells instance;
    private JTable allSellTable;
    private JButton returnButton;
    private JPanel allSellPanel;


    public FormShowAllSells()
    {
        instance = this;
        setContentPane(allSellPanel);
        setPreferredSize(new Dimension(1200,800));
        setTitle("Таблица со всеми лекарствами");
        setVisible(true);

        instance.addWindowListener(exitWindowListener);

        allSellTable.setModel(new CustomTableModelSell());
        allSellTable.getTableHeader().setReorderingAllowed(false);

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
