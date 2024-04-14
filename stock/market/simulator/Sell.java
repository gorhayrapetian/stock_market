package stock.market.simulator;

import stock.market.simulator.console.StockSimulatorConsole;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Represents a selling operation in the stock market simulation.
 * This class allows the user to sell stocks, updating the account's stock holdings and cash balance accordingly.
 */
public class Sell extends Trade {

    DecimalFormat decimalFormat = new DecimalFormat("#.00");

    /**
     * Constructs a Sell object to facilitate selling stocks.
     *
     * @param account The user's account for trading.
     */
    public Sell(Account account) {
        super(account);

        try {
            Scanner scanner = new Scanner(System.in);

            // Prompt user to enter the stock symbol
            System.out.println("Enter the stock symbol you would like to sell: ");
            String stockSymbol = scanner.nextLine();

            // Find the stock index in the account's holdings
            int stockIndex = account.find(stockSymbol, StockSimulatorConsole.stocks, "");

            if (stockIndex == -1) {
                System.out.println("Sorry, that stock is not available for trading.");

            } else {
                Stock stockToTrade = StockSimulatorConsole.stocks[stockIndex];

                // Prompt user to enter the quantity of shares to sell
                System.out.println("How many shares would you like to sell: ");
                int sellQuantity = scanner.nextInt();

                // Check if the specified quantity of shares is available for selling
                if (sharesAvailable(stockToTrade, sellQuantity)) {
                    double totalPrice = stockToTrade.getPrice() * sellQuantity;
                    System.out.println("The total value of the sale is: $" + decimalFormat.format(totalPrice));

                    // Update the type of stock transaction to "sell" and adjust the stock holdings and cash balance
                    stockToTrade.setType("sell");
                    stockToTrade.updateSharesLeft(sellQuantity, "sell");

                    // Remove the stock from the user's account if all shares are sold; otherwise, reduce the quantity
                    if (sellQuantity == stockToTrade.getQuantity()) {
                        account.deleteStockFromPortfolio(stockIndex);
                    } else {
                        account.reduceStockQuantity(stockIndex, sellQuantity);
                    }

                    // Update the user's cash balance
                    account.updateCashBalance(totalPrice, "sell");

                } else {
                    System.out.println("Sorry, you do not have enough shares to sell.");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: The trade could not be completed.");
        }

        writeTransaction("sold");
    }

    private void writeTransaction(String action) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt", true))) {
            writer.println(account.getAccountName() + ", " + action + " stock");
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the specified quantity of shares is available for selling.
     * Overrides the sharesAvailable method in the Trade class.
     *
     * @param stock    The stock to check for available shares.
     * @param quantity The quantity of shares to be sold.
     * @return true if there are enough shares available for selling, otherwise false.
     */
    @Override
    public boolean sharesAvailable(Stock stock, int quantity) {
        if (stock.getType().equals("buy")) {
            return (quantity + stock.getSharesLeft()) <= stock.getTotalShares();
        } else {
            return false;
        }
    }
}
