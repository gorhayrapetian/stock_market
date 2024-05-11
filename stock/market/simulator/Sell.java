package stock.market.simulator;

import stock.market.simulator.console.StockSimulatorConsole;

import java.text.DecimalFormat;

/**
 * Handles the selling of stocks within the stock market simulator.
 */
public class Sell extends AbstractTransaction {

    private DecimalFormat decimalFormat = new DecimalFormat("#.00");
    private StockSimulatorConsole simulator;

    /**
     * Constructs a Sell object to facilitate the stock selling process.
     *
     * @param account     The user's account for trading.
     * @param stock       The stock to be traded.
     * @param quantity    The quantity of stock to sell.
     * @param simulator   The stock simulator to interact with the stock data.
     * @param fileManager The file manager to handle file operations.
     */
    public Sell(Account account, Stock stock, int quantity, StockSimulatorConsole simulator, FileManager fileManager) {
        super(account, stock, quantity, fileManager);
        this.simulator = simulator;
    }

    /**
     * Calculates the total transaction amount by multiplying the price of the stock with the quantity being traded.
     *
     * @return The total transaction amount
     */
    @Override
    protected double calculateTransactionAmount() {
        return stock.getPrice() * quantity;
    }

    /**
     * Executes the sell transaction. Checks if there are enough shares available for sale and calculates the total price.
     * If conditions are met, updates the stock's available shares, account's cash balance, stock quantity in the portfolio,
     * and writes the transaction to a file.
     */
    @Override
    public void execute() {
        if (account.sharesAvailable(stock, quantity)) {
            double totalPrice = calculateTransactionAmount();
            if (totalPrice > 0) {
                System.out.println("The total value of the sale is: $" + decimalFormat.format(totalPrice));
                stock.updateSharesLeft(-quantity);  // Assuming this method decrements shares available
                account.updateCashBalance(totalPrice, "sell");
                account.reduceStockQuantity(stock, quantity);
                fileManager.writeTransaction(account.getAccountName(), "sold", quantity, stock.getSymbol(), totalPrice);
            } else {
                System.out.println("Error: Negative value encountered.");
            }
        } else {
            System.out.println("Sorry, you do not have enough shares to sell.");
        }
    }
}
