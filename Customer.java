// Represents a customer in the stock market that will buy and sell stocks
public class Customer {
    private String name;
    private double balance;
    String[] portfolioStocks;
    int[] portfolioQuantities;

    // Constructor to initialize a customer with a given name and balance
    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    // Getter and setter methods of the Customer class
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Method for buying stock
    public void buyStock() {};

    // Method for selling stocks
    public void sellStock() {};

}
