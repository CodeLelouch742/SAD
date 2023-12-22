package manager;

import application.Application;
import util.Sell;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellEntityManager {
    public static void insert(Sell sell) throws SQLException
    {
        try(Connection connection = Application.getConnection())
        {
            String sql = "INSERT INTO Sell(NameMedicine, QuantitySell, DaySell, MonthSell, YearSell) VALUES(?,?,?,?,?)";
            Statement statement = connection.createStatement();
            statement.execute("use pharmacy");
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, sell.getNameMedicine());
            ps.setString(2, sell.getQuantitySell());
            ps.setString(3, sell.getDaySell());
            ps.setString(4, sell.getMonthSell());
            ps.setString(5, sell.getYearSell());
            ps.executeUpdate();

        }
    }

    public static void update(Sell sell) throws SQLException
    {
        try(Connection connection = Application.getConnection())
        {
            String sql = "UPDATE Sell SET NameMedicine=?, QuantitySell=?, DaySell=?, MonthSell=?, YearSell=? WHERE NameMedicine= ?";
            Statement statement = connection.createStatement();
            statement.execute("use pharmacy");
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, sell.getNameMedicine());
            ps.setString(2, sell.getQuantitySell());
            ps.setString(3, sell.getDaySell());
            ps.setString(4, sell.getMonthSell());
            ps.setString(5, sell.getYearSell());
            ps.setString(6, sell.getNameMedicine());
            ps.executeUpdate();
        }
    }

    public static List<Sell> selectAll() throws SQLException
    {
        try(Connection connection = Application.getConnection())
        {
            String sql = "SELECT * FROM Sell";
            Statement s = connection.createStatement();
            s.execute("use pharmacy");
            ResultSet resultSet = s.executeQuery(sql);

            List<Sell> medicines = new ArrayList<>();
            while(resultSet.next()) {
                medicines.add(new Sell(
                        resultSet.getString("NameMedicine"),
                        resultSet.getString("QuantitySell"),
                        resultSet.getString("DaySell"),
                        resultSet.getString("MonthSell"),
                        resultSet.getString("YearSell")
                ));
            }
            return medicines;
        }
    }

    public static void delete(String nameMedicine) throws SQLException
    {
        try (Connection connection = Application.getConnection())
        {
            String sql = "DELETE FROM Sell WHERE NameMedicine=?";
            Statement statement = connection.createStatement();
            statement.execute("use pharmacy");
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nameMedicine);
            ps.executeUpdate();
        }
    }

    public static void delete(Sell sell) throws SQLException
    {
        delete(sell.getNameMedicine());
    }
}
