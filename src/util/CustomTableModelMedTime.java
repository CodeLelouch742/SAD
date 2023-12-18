package util;

import javax.swing.table.AbstractTableModel;

public class CustomTableModelMedTime extends AbstractTableModel {
    public String getColumnName(int column) {
        return Sells.nameColomnSell[column];
    }

    @Override
    public int getRowCount() {
        return Sells.sellsMedicinesTimeInterval.size();
    }

    @Override
    public int getColumnCount() {
        return Sells.nameColomnSell.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return Sells.sellsMedicinesTimeInterval.get(rowIndex).getNameMedicine();
        }
        if (columnIndex == 1) {
            return Sells.sellsMedicinesTimeInterval.get(rowIndex).getQuantitySell();
        }
        if (columnIndex == 2) {
            return Sells.sellsMedicinesTimeInterval.get(rowIndex).getDaySell() + "-" + Sells.sellsMedicinesTimeInterval.get(rowIndex).getMonthSell() + "-" + Sells.sellsMedicinesTimeInterval.get(rowIndex).getYearSell();
        }
        return null;
    }
}
