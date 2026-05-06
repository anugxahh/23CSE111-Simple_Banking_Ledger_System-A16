package banking;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new java.io.File("data").mkdir();
        Bank bank = new Bank("Nova Bank", "Amritapuri");
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        System.out.println("\n=============================");
        System.out.println("     AMRITA BANK LEDGER        ");
        System.out.println("=============================");

        while (choice != 0) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View All Customers");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Transactions (In-App)");
            System.out.println("5. View Transactions (From File)");
            System.out.println("6. Add New Customer");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    bank.listAllCustomers();
                    break;
                case 2:
                    System.out.print("Enter Customer ID: ");
                    Customer dc = bank.getCustomer(sc.nextInt());
                    if (dc != null) {
                        System.out.print("Enter deposit amount: Rs.");
                        dc.getAccount().deposit(sc.nextDouble());
                    }
                    break;
                case 3:
                    System.out.print("Enter Customer ID: ");
                    Customer wc = bank.getCustomer(sc.nextInt());
                    if (wc != null) {
                        System.out.print("Enter withdrawal amount: Rs.");
                        wc.getAccount().withdraw(sc.nextDouble());
                    }
                    break;
                case 4:
                    System.out.print("Enter Customer ID: ");
                    Customer tc = bank.getCustomer(sc.nextInt());
                    if (tc != null) tc.getAccount().printAllTransactions();
                    break;
                case 5:
                    FileManager.readData("data/transactions.txt");
                    break;
                case 6:
                    System.out.print("Enter Customer ID: ");
                    int newId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter Phone: ");
                    String newPhone = sc.nextLine();
                    System.out.print("Enter Account Number: ");
                    int newAccNum = sc.nextInt();
                    System.out.print("Enter Initial Balance: Rs.");
                    double newBal = sc.nextDouble();
                    Customer newCust = new Customer(newId, newName, newPhone);
                    newCust.createAccount(newAccNum, newBal);
                    bank.addCustomer(newCust);
                    System.out.println("✓ Customer added successfully!");
                    System.out.println("  Name       : " + newName);
                    System.out.println("  Account No : " + newAccNum);
                    System.out.println("  Balance    : Rs." + newBal);
                    break;
                case 0:
                    System.out.println("Goodbye! Thank you for using Nova Bank.");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter 0-6.");
            }
        }
        sc.close();
    }
}