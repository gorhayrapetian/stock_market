package stock.market.simulator;

import java.io.Serializable;

/**
 * Represents a stock in the stock market simulation.
 * This class contains information about a stock, including its symbol, description, current price,
 * total shares, and remaining shares available for trading.
 */
public class Stock implements Serializable {

    private final String symbol;
    private final int totalShares;
    private int sharesLeft;
    private int quantity;
    private double currentPrice;

    /**
     * Constructs a new Stock object with the specified attributes.
     *
     * @param symbol       The symbol of the stock.
     * @param currentPrice The current price of the stock.
     * @param totalShares  The total shares of the stock.
     */
    public Stock(String symbol, double currentPrice, int totalShares) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.totalShares = totalShares;
        this.sharesLeft = totalShares;
    }


    // Getters and setters

    /**
     * Returns the quantity of the stock.
     *
     * @return The quantity of the stock.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the stock.
     *
     * @param quantity The quantity of the stock.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the current price of the stock.
     *
     * @return The current price of the stock.
     */
    public double getPrice() {
        return currentPrice;
    }

    /**
     * Sets the current price of the stock.
     *
     * @param currentPrice The current price of the stock.
     */
    public void setPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * Returns the symbol of the stock.
     *
     * @return The symbol of the stock.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the number of shares left in the open market for trading.
     *
     * @return The number of shares left in the open market.
     */
    public int getSharesLeft() {
        return sharesLeft;
    }

    /**
     * Updates the remaining shares left in the open market after buying or buying to cover.
     *
     * @param quantity The quantity of shares to update.
     */
    public void updateSharesLeft(int quantity) {
        sharesLeft -= quantity;
    }

    /**
     * Updates the remaining shares left in the open market after selling or selling short.
     *
     * @param quantity    The quantity of shares to update.
     * @param transaction The type of transaction (e.g., "buy", "sell").
     */
    public void updateSharesLeft(int quantity, String transaction) {
        if (transaction.equals("buy")) {
            sharesLeft -= quantity;
        } else if (transaction.equals("sell")) {
            sharesLeft += quantity;
        }
    }

    /**
     * Returns the total number of shares available for the stock.
     *
     * @return The total number of shares available.
     */
    public int getTotalShares() {
        return totalShares;
    }
}