package srimani7.javajungle.track;

import java.sql.*;
import java.util.LinkedList;

public class DatabaseHandler {
    private final Connection connection;

    public DatabaseHandler(Connection connection) throws SQLException {
        this.connection = connection;
        Statement statement = connection.createStatement();
        statement.executeUpdate("create table if not exists transaction(" +
                               "tran_date varchar," + "amount double," +
                               "note varchar," +
                               "tran_type varchar);");
        statement.close();
    }

    public void insert(Transaction transaction) throws SQLException {
        PreparedStatement prep = connection.prepareStatement("insert into transaction (tran_date, amount, note, tran_type) values(?,?,?,?);");
        prep.setString(1, transaction.date);
        prep.setDouble(2, transaction.amount);
        prep.setString(3, transaction.note);
        prep.setString(4, transaction.transactionType);
        prep.execute();
        prep.close();
    }

    public LinkedList<Transaction> selectAll() throws SQLException {
        LinkedList<Transaction> list = new LinkedList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select tran_date, amount, note, tran_type from transaction;");
        while (resultSet.next()) {
            Transaction transaction = new Transaction();
            transaction.date = resultSet.getString(1);
            transaction.amount = resultSet.getDouble(2);
            transaction.note = resultSet.getString(3);
            transaction.transactionType = resultSet.getString(4);
            list.add(transaction);
        }
        resultSet.close();
        statement.close();
        return list;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:./databases/exptrack", "user", "password");
    }
}
