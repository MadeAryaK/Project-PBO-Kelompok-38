

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection database;

    public static Connection getConnection() throws SQLException {
        if (database == null || database.isClosed()) {
            try {
                String SUrl = "jdbc:mysql://localhost:3306/batuguntingkertas";
                String SUsername = "root";
                String SPassword = "";

                Class.forName("com.mysql.cj.jdbc.Driver");
                database = DriverManager.getConnection(SUrl, SUsername, SPassword);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new SQLException("Database driver not found");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("Failed to connect to the database");
            }
        }
        return database;
    }

    public static void closeConnection() throws SQLException {
        if (database != null && !database.isClosed()) {
            database.close();
        }
    }
}
