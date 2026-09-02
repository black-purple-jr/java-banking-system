public class Customer {
  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private double balance;

  public Customer(int id, String firstName, String lastName, String email, String phone, double balance) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.balance = balance;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }
}