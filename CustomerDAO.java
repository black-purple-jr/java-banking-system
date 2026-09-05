import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class CustomerDAO {

  public Customer authenticate(String keyword, String password) throws SQLException {
    String sql = "SELECT * FROM customers WHERE email = ? OR phone = ?";

    try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, keyword);
      stmt.setString(2, keyword);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String storedHash = rs.getString("password");
        if (BCrypt.checkpw(password, storedHash)) {
          return new Customer(
              rs.getInt("id"),
              rs.getString("first_name"),
              rs.getString("last_name"),
              rs.getString("email"),
              rs.getString("phone"),
              rs.getDouble("balance"));
        }
      }
      return null;
    }
  }

  public boolean register(String firstName, String lastName, String email, String password, String phone)
      throws SQLException {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    String sql = "INSERT INTO customers (first_name, last_name, email, password, phone) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, firstName);
      stmt.setString(2, lastName);
      stmt.setString(3, email);
      stmt.setString(4, hashedPassword);
      stmt.setString(5, phone);
      stmt.executeUpdate();
      return true;
    } catch (SQLIntegrityConstraintViolationException e) {
      System.out.println("──>> Email already registered.");
      return false;
    }
  }

  public void updateBalance(int id, double newBalance) throws SQLException {
    String sql = "UPDATE customers SET balance = ? WHERE id = ?";

    try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setDouble(1, newBalance);
      stmt.setInt(2, id);
      stmt.executeUpdate();
    }
  }

  public void getCustomerById(String id) {
  }
}