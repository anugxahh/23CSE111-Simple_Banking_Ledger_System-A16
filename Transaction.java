package banking;

public class Transaction {

    private int transactionId;
    private double amount;
    private String transactionType;

    public Transaction(int transactionId, double amount, String transactionType) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public void processTransaction() {
        System.out.println("Processing " + transactionType + " of Rs." + String.format("%.2f", amount));
    }

    public void displayTransaction() {
        System.out.println("[TXN-" + transactionId + "] " + transactionType + " --> Rs." + String.format("%,.2f", amount));
    }

    public int getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
    public String getTransactionType() { return transactionType; }

    @Override
    public String toString() {
        return transactionId + "," + amount + "," + transactionType;
    }
}