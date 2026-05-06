package banking;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private String bankName;
    private String branch;
    private List<Customer> customers;

    public Bank(String bankName, String branch) {
        this.bankName = bankName;
        this.branch = branch;
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public Customer getCustomer(int customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId() == customerId) return c;
        }
        return null;
    }

    public List<Customer> getCustomers() { return customers; }

    // FIX: Added missing method called by Main.java
    public void listAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        System.out.println("\n--- All Customers in " + bankName + " (" + branch + ") ---");
        for (Customer c : customers) {
            c.viewDetails();
            System.out.println("-----------------------------");
        }
    }
}