// Represents a broker in the stock market that will sell stocks to customers
public class Broker {
    private final String name;
    private final double commissionRate;

    // Constructor for a Broker with the specified name and commission rate.
    public Broker(String name, double commissionRate) {
        this.name = name;
        this.commissionRate = commissionRate;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    // Method for calculating the commission rate
    public double calculateCommission(double transactionAmount) {
        return transactionAmount * commissionRate;
    }
}
