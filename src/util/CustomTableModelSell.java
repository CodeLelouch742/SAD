package util;

import javax.swing.table.AbstractTableModel;

public class CustomTableModelSell extends AbstractTableModel {

        public String getColumnName(int column) {
            return Sells.nameColomnSell[column];
        }

        @Override
        public int getRowCount() {
            return Sells.sells.size();
        }

        @Override
        public int getColumnCount() {
            return Sells.nameColomnSell.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return Sells.sells.get(rowIndex).getNameMedicine();
            }
            if (columnIndex == 1) {
                return Sells.sells.get(rowIndex).getQuantitySell();
            }
            if (columnIndex == 2) {
                return Sells.sells.get(rowIndex).getDaySell() + "-" + Sells.sells.get(rowIndex).getMonthSell() + "-" + Sells.sells.get(rowIndex).getYearSell();
            }
            return null;
        }
}
