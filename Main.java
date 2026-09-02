import java.sql.SQLException;
import java.util.InputMismatchException;
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
      System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
      System.out.println("─── 1. Connect to an existing bank account");
      System.out.println("─── 2. Create a new bank account");
      System.out.println("─── 3. Exit");
      System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");

      choice = readInt("──> Enter your choice (1-3): ");
      System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");

      switch (choice) {
        case 1 -> isAuthenticated = login();
        case 2 -> register();
        case 3 -> isRunning = false;
        default -> System.out.println(Colors.RED + "─>> Invalid Operation" + Colors.RESET);
      }
    }

    while (isRunning && isAuthenticated) {
      System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
      System.out.println("─── 1. Show balance");
      System.out.println("─── 2. Deposit");
      System.out.println("─── 3. Withdraw");
      System.out.println("─── 4. Exit");
      System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");

      choice = readInt("──> Enter your choice (1-4): ");
      System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");

      switch (choice) {
        case 1 -> showBalance(currentCustomer.getBalance());
        case 2 -> handleDeposit();
        case 3 -> handleWithdraw();
        case 4 -> isRunning = false;
        default -> System.out.println(Colors.RED + "─>> Invalid Operation" + Colors.RESET);
      }

    }

    scanner.close();
  }

  // Some helper methods

  static int readInt(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        int value = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        return value;
      } catch (InputMismatchException e) {
        System.out.println(Colors.RED + "─>> Invalid input: please enter a whole number." + Colors.RESET);
        scanner.nextLine(); // discard the bad token
      }
    }
  }

  static double readDouble(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume leftover newline
        return value;
      } catch (InputMismatchException e) {
        System.out
            .println(Colors.RED + "─>> Invalid input: please enter a valid amount (numbers only)." + Colors.RESET);
        scanner.nextLine(); // discard the bad token
      }
    }
  }

  static String readNonEmptyString(String prompt) {
    while (true) {
      System.out.print(prompt);
      String value = scanner.nextLine().trim();
      if (value.isEmpty()) {
        System.out.println(Colors.RED + "─>> This field can't be empty." + Colors.RESET);
      } else {
        return value;
      }
    }
  }

  // Authentication methods

  static boolean login() {
    String keyword = readNonEmptyString("──> Enter your e-mail or your phone number: ");
    String password = readNonEmptyString("──> Enter your password: ");

    try {
      Customer customer = customerDAO.authenticate(keyword, password);
      if (customer != null) {
        currentCustomer = customer;
        System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────");
        System.out.println(Colors.GREEN + "─── Welcome back, " + customer.getFirstName() + Colors.RESET);
        return true;
      }
      System.out.println(Colors.RED + "─>> Invalid credentials." + Colors.RESET);
      return false;
    } catch (SQLException e) {
      System.out.println(Colors.RED + "─>> Database error: " + e.getMessage() + Colors.RESET);
      return false;
    }

  }

  static void register() {
    String firstName = readNonEmptyString("──> First name: ");
    String lastName = readNonEmptyString("──> Last name: ");
    String email = readNonEmptyString("──> Email: ");
    String password = readNonEmptyString("──> Password: ");
    String phone = readNonEmptyString("──> Phone number: ");

    try {
      if (customerDAO.register(firstName, lastName, email, password, phone)) {
        System.out.println(Colors.GREEN + "─── Account created successfully, You can now log in." + Colors.RESET);
      }
    } catch (SQLException e) {
      System.out.println(Colors.RED + "─>> Database error: " + e.getMessage() + Colors.RESET);
    }

  }

  // Account handling methods

  static void showBalance(double balance) {
    System.out.printf("─── Your current balance is $%.2f\n", balance);
  }

  static void handleDeposit() {
    double amount = readDouble("──> Enter an amount to be deposited: ");
    if (amount < 0) {
      System.out.println(Colors.RED + "─>> Amount can't be negative" + Colors.RESET);
      return;
    }
    double newBalance = currentCustomer.getBalance() + amount;
    try {
      customerDAO.updateBalance(currentCustomer.getId(), newBalance);
      currentCustomer.setBalance(newBalance);
      System.out.println(Colors.GREEN + "─── Deposit successful." + Colors.RESET);
    } catch (SQLException e) {
      System.out.println(Colors.RED + "─>> Database error: " + e.getMessage() + Colors.RESET);
    }
  }

  static void handleWithdraw() {
    double amount = readDouble("──> Enter an amount you want to withdraw: ");
    if (amount < 0) {
      System.out.println(Colors.RED + "─>> Amount can't be negative" + Colors.RESET);
      return;
    }
    if (amount > currentCustomer.getBalance()) {
      System.out.println(Colors.RED + "─>> Insufficient funds." + Colors.RESET);
      return;
    }
    double newBalance = currentCustomer.getBalance() - amount;
    try {
      customerDAO.updateBalance(currentCustomer.getId(), newBalance);
      currentCustomer.setBalance(newBalance);
      System.out.println(Colors.GREEN + "─── Withdrawal successful." + Colors.RESET);
    } catch (SQLException e) {
      System.out.println(Colors.RED + "─>> Database error: " + e.getMessage() + Colors.RESET);
    }
  }
}