package manager;

import util.Medicine;
import application.Application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineEntityManager {
    public static void insert(Medicine medicine) throws SQLException
    {
        try(Connection connection = Application.getConnection())
        {
            String sql = "INSERT INTO Medicine(NameMedicine, NameDisease, Price, Quantity) VALUES(?,?,?,?)";
            System.out.println(medicine.toString());
            Statement statement = connection.createStatement();
            statement.execute("use pharmacy");
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, medicine.getNameMedicine());
            ps.setString(2, medicine.getNameDisease());
            ps.setString(3, medicine.getPrice());
            ps.setString(4, medicine.getQuantity());
            ps.executeUpdate();

        }
    }

    public static void update(Medicine medicine, String nameMedicine) throws SQLException
    {
        try(Connection connection = Application.getConnection())
        {
            String sql = "UPDATE Medicine SET NameMedicine=?, NameDisease=?, Price=?, Quantity=? WHERE NameMedicine= ?";
            Statement statement = connection.createStatement();
            statement.execute("use pharmacy");
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, medicine.getNameMedicine());
            ps.setString(2, medicine.getNameDisease());
            ps.setString(3, medicine.getPrice());
            ps.setString(4, medicine.getQuantity());
            ps.setString(5, nameMedicine);
            ps.executeUpdate();
        }
    }

    public static List<Medicine> selectAll() throws SQLException
    {
        try(Connection connection = Application.getConnection())
        {
            String sql = "SELECT * FROM Medicine";
            Statement s = connection.createStatement();
            s.execute("use pharmacy");
            ResultSet resultSet = s.executeQuery(sql);

            List<Medicine> medicines = new ArrayList<>();
            while(resultSet.next()) {
                medicines.add(new Medicine(
                        resultSet.getString("NameMedicine"),
                        resultSet.getString("NameDisease"),
                        resultSet.getString("Price"),
                        resultSet.getString("Quantity")
                ));
            }
            return medicines;
        }
    }

    public static void delete(String nameMedicine) throws SQLException
    {
        try (Connection connection = Application.getConnection())
        {
            String sql = "DELETE FROM Medicine WHERE NameMedicine=?";
            Statement statement = connection.createStatement();
            statement.execute("use pharmacy");
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nameMedicine);
            ps.executeUpdate();
        }
    }

    public static void delete(Medicine medicine) throws SQLException
    {
        delete(medicine.getNameMedicine());
    }

}
