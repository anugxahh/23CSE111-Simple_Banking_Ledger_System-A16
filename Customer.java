package banking;

public class Customer {

    private int customerId;
    private String name;
    private String phone;
    private Account account;

    public Customer(int customerId, String name, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
    }

    public void createAccount(int accountNumber, double initialBalance) {
        this.account = new Account(accountNumber, initialBalance);
        this.account.setCustomerName(this.name); // Pass name so logs show it
        System.out.println("Account created! Number: " + accountNumber);
    }

    public Account getAccount() { return account; }

    public void viewDetails() {
        System.out.println("Customer ID : " + customerId);
        System.out.println("Name        : " + name);
        System.out.println("Phone       : " + phone);
        if (account != null)
            System.out.println("Balance     : Rs." + account.getBalance());
        else
            System.out.println("No account linked.");
    }

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    // FIX: Added missing getter
    public String getPhone() { return phone; }
}