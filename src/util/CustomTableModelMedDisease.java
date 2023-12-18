package util;

import javax.swing.table.AbstractTableModel;

public class CustomTableModelMedDisease extends AbstractTableModel {

    public String getColumnName(int column) {
        return Medicines.nameColomnMedicine[column];
    }

    @Override
    public int getRowCount() {
        return Medicines.listMedicinesByDisease.size();
    }

    @Override
    public int getColumnCount() {
        return Medicines.nameColomnMedicine.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return Medicines.listMedicinesByDisease.get(rowIndex).getNameMedicine();
        }
        if (columnIndex == 1) {
            return Medicines.listMedicinesByDisease.get(rowIndex).getNameDisease();
        }
        if (columnIndex == 2) {
            return Medicines.listMedicinesByDisease.get(rowIndex).getPrice();
        }
        if (columnIndex == 3) {
            return Medicines.listMedicinesByDisease.get(rowIndex).getQuantity();
        }
        return null;
    }

}
