package util;


import javax.swing.table.AbstractTableModel;



public class CustomTableModelMedicine extends AbstractTableModel
{


    public String getColumnName(int column) {
        return Medicines.nameColomnMedicine[column];
    }

    @Override
    public int getRowCount() {
        return Medicines.medicines.size();
    }

    @Override
    public int getColumnCount() {
        return Medicines.nameColomnMedicine.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return Medicines.medicines.get(rowIndex).getNameMedicine();
        }
        if (columnIndex == 1) {
            return Medicines.medicines.get(rowIndex).getNameDisease();
        }
        if (columnIndex == 2) {
            return Medicines.medicines.get(rowIndex).getPrice();
        }
        if (columnIndex == 3) {
            return Medicines.medicines.get(rowIndex).getQuantity();
        }
        return null;
    }
}
