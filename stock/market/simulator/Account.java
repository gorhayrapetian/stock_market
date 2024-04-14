package stock.market.simulator;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Represents a user account in the stock market simulation.
 * This class manages the user's account, including their name, cash balance, and holdings of stocks.
 */
public class Account implements Serializable {
    private final String accountName;
    private double cash;
    private ArrayList<Stock> heldStocks = new ArrayList<>();
    DecimalFormat decimalFormat = new DecimalFormat("#.00");

    /**
     * Constructs a new Account object with the specified name and initial cash balance.
     *
     * @param accountName The name of the account holder.
     * @param initialCash The initial cash balance in the account.
     */
    public Account(String accountName, double initialCash) {
        this.accountName = accountName;
        this.cash = initialCash;
    }

    /**
     * Returns the name of the account holder.
     *
     * @return The name of the account holder.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Returns the current cash balance in the account.
     *
     * @return The current cash balance.
     */
    public double getCashBalance() {
        return cash;
    }

    /**
     * Updates the cash balance in the account based on a trading transaction.
     *
     * @param transactionCash The cash amount involved in the transaction.
     * @param transactionType The type of transaction ("buy" or "sell").
     */
    public void updateCashBalance(double transactionCash, String transactionType) {
        switch (transactionType) {
            case "buy":
                cash -= transactionCash;
                break;
            case "sell":
                cash += transactionCash;
                break;
            default:
                System.out.println("Error: Invalid transaction type.");
                break;
        }
    }

    /**
     * Adds a stock to the user's account holdings.
     *
     * @param stock The stock to be added to the account.
     * @param transactionType The type of transaction ("buy" or "sell").
     */
    public void addStockToPortfolio(Stock stock, String transactionType) {
        int existingStockIndex = -1;
        for (int i = 0; i < heldStocks.size(); i++) {
            Stock existingStock = heldStocks.get(i);
            if (existingStock.getSymbol().equals(stock.getSymbol()) && (existingStock.getType().equals(stock.getType()))) {
                existingStockIndex = i;
            }
        }
        if (existingStockIndex == -1) {
            heldStocks.add(stock);
        } else {
            Stock existingStock = heldStocks.get(existingStockIndex);
            int updatedQuantity = existingStock.getQuantity() + stock.getQuantity();
            existingStock.setQuantity(updatedQuantity);
        }
    }

    /**
     * Removes a stock from the user's account holdings.
     *
     * @param index The index of the stock to be removed.
     */
    public void deleteStockFromPortfolio(int index) {
        heldStocks.remove(index);
    }

    /**
     * Displays the user's portfolio, including cash balance and holdings.
     */
    public void displayPortfolio() {
        System.out.println(accountName + "'s Portfolio");
        System.out.println("---------------------------");
        double portfolioValue = calculatePortfolioValue();
        System.out.println("\nAccount Value: $" + decimalFormat.format(portfolioValue) + "   Cash: $" + decimalFormat.format(cash) + "\n");

        int numShorts = 0;

        for (Stock stock : heldStocks) {
            if (!stock.getType().equals("short")) {
                System.out.println("Stock Symbol: " + stock.getSymbol() + "\n" +
                        "Price: $" + decimalFormat.format(stock.getPrice()) + "\n" +
                        "Quantity: " + stock.getQuantity() + "\n" +
                        "Total: $" + decimalFormat.format(stock.getPrice() * stock.getQuantity()) + "\n");
            } else {
                numShorts++;
            }
        }

        if (numShorts != 0) {
            System.out.println("Shorting Stocks");
            System.out.println("-----------------");
            for (Stock stock : heldStocks) {
                if (stock.getType().equals("short")) {
                    System.out.println("Stock Symbol: " + stock.getSymbol() + "\n" +
                            "Price: $" + decimalFormat.format(stock.getPrice()) + "\n" +
                            "Quantity: " + stock.getQuantity() + "\n" +
                            "Total: $" + decimalFormat.format(stock.getPrice() * stock.getQuantity()) + "\n");
                }
            }
        }
    }

    /**
     * Reduces the quantity of a stock in the user's account holdings.
     *
     * @param index The index of the stock.
     * @param quantity The quantity to be reduced.
     */
    public void reduceStockQuantity(int index, int quantity) {
        Stock stock = heldStocks.get(index);
        int newQuantity = stock.getQuantity() - quantity;
        stock.setQuantity(newQuantity);
    }

    /**
     * Calculates the total value of the user's portfolio, including cash balance and stock holdings.
     *
     * @return The total value of the portfolio.
     */
    public double calculatePortfolioValue() {
        double totalValue = 0;
        for (Stock stock : heldStocks) {
            totalValue += (stock.getPrice() * stock.getQuantity());
        }
        return totalValue + cash;
    }

    /**
     * Finds the index of a stock in the stocks array.
     *
     * @param symbol The symbol of the stock to find.
     * @param stocks The array of stocks to search.
     * @param type The type of stock.
     * @return The index of the stock in the array, or -1 if not found.
     */
    public int find(String symbol, Stock[] stocks, String type) {
        for (int i = 0; i < stocks.length; i++) {
            if ((stocks[i].getSymbol().equals(symbol)) && !(stocks[i].getType().equals(type))) {
                return i;
            }
        }
        return -1;
    }
}
