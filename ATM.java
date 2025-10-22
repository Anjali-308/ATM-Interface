import java.util.ArrayList;
import java.util.Scanner;

// --- ATM class to start the program ---
public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankAccount account = new BankAccount("Anjali", "123456", 1234); // demo account

        System.out.println("===== Welcome to ATM Interface =====");
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        if (account.login(userId, pin)) {
            System.out.println("\nLogin Successful!\n");
            ATMInterface atm = new ATMInterface(account);
            atm.showMenu();
        } else {
            System.out.println("Invalid User ID or PIN. Try again!");
        }
        sc.close();
    }
}

// --- ATM Interface Class ---
class ATMInterface {
    private BankAccount account;
    private Scanner sc = new Scanner(System.in);

    ATMInterface(BankAccount account) {
        this.account = account;
    }

    public void showMenu() {
        int option;
        do {
            System.out.println("\n===== ATM Menu =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    account.showTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        } while (option != 5);
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        account.withdraw(amount);
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        account.deposit(amount);
    }

    private void transfer() {
        sc.nextLine(); // consume newline
        System.out.print("Enter recipient User ID: ");
        String recipientId = sc.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();
        account.transfer(amount, recipientId);
    }
}

// --- BankAccount Class ---
class BankAccount {
    private String userName;
    private String userId;
    private int pin;
    private double balance;
    private ArrayList<String> transactionHistory = new ArrayList<>();

    BankAccount(String userName, String userId, int pin) {
        this.userName = userName;
        this.userId = userId;
        this.pin = pin;
        this.balance = 1000.0; // Initial balance
    }

    public boolean login(String userId, int pin) {
        return this.userId.equals(userId) && this.pin == pin;
    }

    public void showTransactionHistory() {
        System.out.println("\n===== Transaction History =====");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else if (amount <= 0) {
            System.out.println("Enter a valid amount!");
        } else {
            balance -= amount;
            System.out.println("Withdraw Successful! Current Balance: ₹" + balance);
            transactionHistory.add("Withdraw: ₹" + amount);
        }
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Enter a valid amount!");
        } else {
            balance += amount;
            System.out.println("Deposit Successful! Current Balance: ₹" + balance);
            transactionHistory.add("Deposit: ₹" + amount);
        }
    }

    public void transfer(double amount, String recipientId) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else if (amount <= 0) {
            System.out.println("Enter a valid amount!");
        } else {
            balance -= amount;
            System.out.println("Transfer Successful! ₹" + amount + " sent to " + recipientId);
            transactionHistory.add("Transfer: ₹" + amount + " to " + recipientId);
        }
    }
}
