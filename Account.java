package banking;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private int accountNumber;
    private double balance;
    private List<Transaction> transactions;
    private int transactionCounter;
    private String customerName;

    public Account(int accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.transactionCounter = 1;
        this.customerName = "Unknown";
    }

    public void setCustomerName(String name) { this.customerName = name; }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
        Transaction t = new Transaction(transactionCounter++, amount, "DEPOSIT");
        transactions.add(t);
        t.processTransaction();
        FileManager.saveData("data/transactions.txt",
            String.format("%-20s | Acc#%-10s | Rs.%-12s | Rs.%-12s | DEPOSIT",
                customerName, accountNumber,
                String.format("%,.2f", amount),
                String.format("%,.2f", balance)));
        System.out.println("New Balance: Rs." + String.format("%,.2f", balance));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds! Balance: Rs." + String.format("%,.2f", balance));
            return;
        }
        balance -= amount;
        Transaction t = new Transaction(transactionCounter++, amount, "WITHDRAWAL");
        transactions.add(t);
        t.processTransaction();
        FileManager.saveData("data/transactions.txt",
            String.format("%-20s | Acc#%-10s | Rs.%-12s | Rs.%-12s | WITHDRAWAL",
                customerName, accountNumber,
                String.format("%,.2f", amount),
                String.format("%,.2f", balance)));
        System.out.println("New Balance: Rs." + String.format("%,.2f", balance));
    }

    public double getBalance() { return balance; }

    public int getAccountNumber() { return accountNumber; }

    public List<Transaction> getTransactions() { return transactions; }

    public void printAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for account " + accountNumber);
            return;
        }
        System.out.println("\n--- Transactions for Account " + accountNumber + " ---");
        for (Transaction t : transactions) {
            t.displayTransaction();
        }
    }
}