import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Account {
    private int accountNumber;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(int accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        transactionHistory = new ArrayList<>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void recordTransaction(TransactionType type, double amount) {
        Transaction transaction = new Transaction(type, amount, LocalDateTime.now());
        transactionHistory.add(transaction);
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History for Account " + accountNumber + ":");
        for (Transaction transaction : transactionHistory) {
            System.out.println("Type: " + transaction.getType() +
                               ", Amount: " + transaction.getAmount() +
                               ", Date: " + transaction.getDate());
        }
    }
}

enum TransactionType {
    DEPOSIT, WITHDRAWAL
}

class Transaction {
    private TransactionType type;
    private double amount;
    private LocalDateTime date;

    public Transaction(TransactionType type, double amount, LocalDateTime date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void displayAccounts() {
        for (Account account : accounts) {
            System.out.println("Account: " + account.getAccountNumber() + ", Balance: " + account.getBalance());
        }
    }
}

class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}

public class BankingSystemApp {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Simple Banking System");

        // Create customers and accounts
        System.out.print("Enter the number of customers: ");
        int numCustomers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 1; i <= numCustomers; i++) {
            System.out.print("Enter name for Customer " + i + ": ");
            String name = scanner.nextLine();
            Customer customer = new Customer(name);

            System.out.print("Enter initial balance for " + name + "'s account: ");
            double initialBalance = scanner.nextDouble();
            Account account = new Account(1000 + i, initialBalance);
            customer.addAccount(account);
            bank.addCustomer(customer);
            scanner.nextLine(); // Consume newline
        }

        // Perform banking operations
        System.out.println("Banking Operations:");
        for (Customer customer : bank.getCustomers()) {
            System.out.println("Customer: " + customer.getName());

            List<Account> accounts = customer.getAccounts();
            if (!accounts.isEmpty()) {
                System.out.print("Enter amount to deposit: ");
                double depositAmount = scanner.nextDouble();
                accounts.get(0).deposit(depositAmount);
                accounts.get(0).recordTransaction(TransactionType.DEPOSIT, depositAmount); // Record transaction

                System.out.print("Enter amount to withdraw: ");
                double withdrawAmount = scanner.nextDouble();
                accounts.get(0).withdraw(withdrawAmount);
                accounts.get(0).recordTransaction(TransactionType.WITHDRAWAL, withdrawAmount); // Record transaction
                scanner.nextLine(); // Consume newline

                System.out.println("Updated account information:");
                customer.displayAccounts();

                // Display transaction history
                System.out.println("Transaction History:");
                accounts.get(0).displayTransactionHistory();

                System.out.println();
            } else {
                System.out.println("No accounts found for customer.");
            }
        }

        scanner.close();
    }
}
