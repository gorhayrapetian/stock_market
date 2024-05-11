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
        if ("buy".equals(transactionType)) {
            cash -= transactionCash;
        } else if ("sell".equals(transactionType)) {
            cash += transactionCash;
        }
    }


    /**
     * Adds a stock to the user's account holdings.
     *
     * @param stock The stock to be added to the account.
     */
    public void addStockToPortfolio(Stock stock) {
        int existingStockIndex = -1;
        for (int i = 0; i < heldStocks.size(); i++) {
            Stock existingStock = heldStocks.get(i);
            if (existingStock.getSymbol().equals(stock.getSymbol())) {
                existingStockIndex = i;
                break;
            }
        }
        if (existingStockIndex == -1) {
            heldStocks.add(stock);
        } else {
            Stock existingStock = heldStocks.get(existingStockIndex);
            existingStock.setQuantity(existingStock.getQuantity() + stock.getQuantity());
        }
    }

    /**
     * Displays the user's portfolio, including cash balance and holdings.
     */
    public void displayPortfolio() {
        System.out.println(accountName + "'s Portfolio");
        System.out.println("---------------------------");
        double portfolioValue = calculatePortfolioValue();
        System.out.println("\nAccount Value: $" + decimalFormat.format(portfolioValue) + "   Cash: $" + decimalFormat.format(cash) + "\n");

        for (Stock stock : heldStocks) {
            System.out.println("Stock Symbol: " + stock.getSymbol() + "\n" +
                    "Price: $" + decimalFormat.format(stock.getPrice()) + "\n" +
                    "Quantity: " + stock.getQuantity() + "\n" +
                    "Total: $" + decimalFormat.format(stock.getPrice() * stock.getQuantity()) + "\n");
        }
    }

    /**
     * Reduces the quantity of a specific stock in the user's account holdings.
     *
     * @param stockToReduce The stock for which the quantity needs to be reduced.
     * @param quantity The quantity to be reduced.
     */
    public void reduceStockQuantity(Stock stockToReduce, int quantity) {
        for (Stock stock : heldStocks) {
            if (stock.getSymbol().equals(stockToReduce.getSymbol())) {
                int newQuantity = stock.getQuantity() - quantity;
                if (newQuantity >= 0) {
                    stock.setQuantity(newQuantity);
                } else {
                    System.out.println("Error: Trying to reduce more shares than are available.");
                }
                return;
            }
        }
        System.out.println("Stock not found in the portfolio.");
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
     * Checks if the account has enough cash to perform the trade.
     *
     * @param totalCost the total cost of the trade
     * @return true if the account has enough cash, false otherwise
     */
    public boolean hasEnoughCash(double totalCost) {
        return totalCost <= getCashBalance();
    }

    /**
     * Checks if the specified stock has enough shares available to be bought.
     *
     * @param stock    the stock to check for available shares
     * @param quantity the quantity of shares to be bought
     * @return true if there are enough shares available, false otherwise
     */
    public boolean sharesAvailable(Stock stock, int quantity) {
        return (quantity <= stock.getSharesLeft()) && (quantity > 0);
    }

    /**
     * Retrieves a copy of the list of stocks currently held in the user's account.
     *
     * @return A new {@link ArrayList} containing copies of the {@link Stock} objects held in the account.
     */
    public ArrayList<Stock> getHeldStocks() {
        return new ArrayList<>(heldStocks);
    }

}