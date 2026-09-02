import java.sql.SQLException;
import java.util.Scanner;

public class Main {

  static Scanner scanner = new Scanner(System.in);
  static CustomerDAO customerDAO = new CustomerDAO();
  static Customer currentCustomer = null;

  public static void main(String[] args) {

    boolean isRunning = true;
    boolean isAuthenticated = false;
    int choice;

    while (isRunning && !isAuthenticated) {
      System.out.println("──────────────────────────────────────────────────────────────────────────────");
      System.out.println("1. Connect to an existing bank account");
      System.out.println("2. Create a new bank account");
      System.out.println("3. Exit");
      System.out.println("──────────────────────────────────────────────────────────────────────────────");

      System.out.print("──> Enter your choice (1-3): ");
      choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1 -> isAuthenticated = login();
        case 2 -> register();
        case 3 -> isRunning = false;
        default -> System.out.println("Invalid Operation");
      }
    }

    while (isRunning && isAuthenticated) {
      System.out.println("──────────────────────────────────────────────────────────────────────────────");
      System.out.println("1. Show balance");
      System.out.println("2. Deposit");
      System.out.println("3. Withdraw");
      System.out.println("4. Exit");
      System.out.println("──────────────────────────────────────────────────────────────────────────────");

      System.out.print("──> Enter your choice (1-4): ");
      choice = scanner.nextInt();

      switch (choice) {
        case 1 -> showBalance(currentCustomer.getBalance());
        case 2 -> handleDeposit();
        case 3 -> handleWithdraw();
        case 4 -> isRunning = false;
        default -> System.out.println("──>> Invalid Operation");
      }

    }

    scanner.close();
  }

  static boolean login() {
    System.out.print("──> Enter your e-mail or your phone number: ");
    String keyword = scanner.nextLine();

    System.out.print("──> Enter your password: ");
    String password = scanner.nextLine();
    

    try {
      Customer customer = customerDAO.authenticate(keyword, password);
      if (customer != null) {
        currentCustomer = customer;
        System.out.println("──> Welcome back, " + customer.getFirstName());
        return true;
      }
      System.out.println("──>> Invalid credentials.");
      return false;
    } catch (SQLException e) {
      System.out.println("──>> Database error: " + e.getMessage());
      return false;
    }

  }

  static void register() {
    System.out.print("──> First name: ");
    String firstName = scanner.nextLine();

    System.out.print("──> Last name: ");
    String lastName = scanner.nextLine();

    System.out.print("──> Email: ");
    String email = scanner.nextLine();

    System.out.print("──> Password: ");
    String password = scanner.nextLine();

    System.out.print("──> Phone number: ");
    String phone = scanner.nextLine();

    try {
      if (customerDAO.register(firstName, lastName, email, password, phone)) {
        System.out.println("──> Account created! You can now log in.");
      }
    } catch (SQLException e) {
      System.out.println("──>> Database error: " + e.getMessage());
    }

  }

  static void showBalance(double balance) {
    System.out.printf("──> Your current balance is $%.2f\n", balance);
  }

  static void handleDeposit() {
    System.out.print("──> Enter an amount to be deposited: ");
    double amount = scanner.nextDouble();
    if (amount < 0) {
      System.out.println("──>> Amount can't be negative");
      return;
    }
    double newBalance = currentCustomer.getBalance() + amount;
    try {
      customerDAO.updateBalance(currentCustomer.getId(), newBalance);
      currentCustomer.setBalance(newBalance);
      System.out.println("──> Deposit successful.");
    } catch (SQLException e) {
      System.out.println("──>> Database error: " + e.getMessage());
    }
  }

  static void handleWithdraw() {
    System.out.print("──> Enter an amount you want to withdraw: ");
    double amount = scanner.nextDouble();
    if (amount < 0) {
      System.out.println("──>> Amount can't be negative");
      return;
    }
    if (amount > currentCustomer.getBalance()) {
      System.out.println("──>> Insufficient funds.");
      return;
    }
    double newBalance = currentCustomer.getBalance() - amount;
    try {
      customerDAO.updateBalance(currentCustomer.getId(), newBalance);
      currentCustomer.setBalance(newBalance);
      System.out.println("──> Withdrawal successful.");
    } catch (SQLException e) {
      System.out.println("──>> Database error: " + e.getMessage());
    }
  }

}