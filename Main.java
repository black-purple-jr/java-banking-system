import java.sql.SQLException;
import java.util.Scanner;
// import java.sql.SQLException;

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
        case 1 -> login();
        case 2 -> register();
        case 3 -> isRunning = false;
        default -> System.out.println("Invalid Operation");
      }
    }

    // isAuthenticated = true;

    double balance = 0;

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
        case 1 -> showBalance(balance);
        case 2 -> balance += deposit();
        case 3 -> balance -= withDraw();
        case 4 -> isRunning = false;
        default -> System.out.println("Invalid Operation");
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
      System.out.println("──> Invalid credentials.");
      return false;
    } catch (SQLException e) {
      System.out.println("──> Database error: " + e.getMessage());
      return false;
    }

  }

  static void register() {

  }

  static void showBalance(double balance) {
    System.out.printf("──> Your current balance is $%.2f\n", balance);
  }

  static double deposit() {
    Double amount;

    System.out.print("──> Enter an amount to be deposited: ");
    amount = scanner.nextDouble();

    if (amount < 0) {
      System.out.println("Amount can't be negative");
      return 0;
    } else {
      return amount instanceof Double ? amount : 0;
    }
  }

  static double withDraw() {
    double amount;

    System.out.print("──> Enter an amount you want to withdraw: ");
    amount = scanner.nextDouble();

    if (amount < 0) {
      System.out.println("Amount can't be negative");
      return 0;
    } else {
      return amount;
    }
  }
}