// Represents a stock in the stock market which is the main product
public class Stock {
    private final String name;
    private double price;
    private double quantity;

    // Constructor for a Stock object with the specified name, price, and quantity.
    public Stock(String name, double price, double quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter and setter methods for a class
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
