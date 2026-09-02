import java.sql.*;

public class Database {
  private static final String URL = "jdbc:mysql://localhost:3306/bank";
  private static final String USER = "root";
  private static final String PASSWORD = "";

  public static Connection getConnection() throws SQLException {
    System.out.println("Connected successfully");
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

}