package stock.market.simulator;

import stock.market.simulator.console.StockSimulatorConsole;

import java.text.DecimalFormat;

/**
 * Handles the buying of stocks within the stock market simulator.
 */
public class Buy extends AbstractTransaction {

    private DecimalFormat df = new DecimalFormat("#.00");
    private StockSimulatorConsole simulator;

    /**
     * Constructs a Buy object to facilitate the stock purchasing process.
     *
     * @param account     The user's account for trading.
     * @param stock       The stock to be traded.
     * @param quantity    The quantity of stock to buy.
     * @param simulator   The stock simulator to interact with the stock data.
     * @param fileManager The file manager to handle file operations.
     */
    public Buy(Account account, Stock stock, int quantity, StockSimulatorConsole simulator, FileManager fileManager) {
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
     * Executes the buy transaction. Checks if there are enough shares available and if the account has enough cash.
     * If conditions are met, updates the cash balance, stock quantity, and portfolio, and writes the transaction to a file.
     */
    @Override
    public void execute() {
        if (account.sharesAvailable(stock, quantity)) {
            double totalPrice = calculateTransactionAmount();
            if (account.hasEnoughCash(totalPrice)) {
                System.out.println("The total comes to: $" + df.format(totalPrice));
                account.updateCashBalance(-totalPrice, "buy");
                stock.setQuantity(quantity);
                account.addStockToPortfolio(new Stock(stock.getSymbol(), stock.getPrice(), quantity));
                fileManager.writeTransaction(account.getAccountName(), "bought", quantity, stock.getSymbol(), totalPrice);
            } else {
                System.out.println("Sorry, not enough cash.");
            }
        } else {
            System.out.println("Sorry, not enough shares available.");
        }
    }
}
