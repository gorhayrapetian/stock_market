package stock.market.simulator;

import stock.market.simulator.console.StockSimulatorConsole;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Allows the user to buy stocks, a basic feature needed for trading.
 * This class facilitates the process of buying stocks by prompting the user to enter the stock symbol
 * and the quantity of shares they wish to purchase.
 */
public class Buy extends Trade {

    // Formats the input to two decimal places
    DecimalFormat df = new DecimalFormat("#.00");

    /**
     * Constructs a Buy object to facilitate the stock purchasing process.
     *
     * @param account The user's account for trading.
     */
    public Buy(Account account) {
        // Calls the Trade constructor to set the start of the trade
        super(account);

        try {
            // Validation on stock symbol
            Scanner user_input = new Scanner(System.in);

            // Get user input for stock symbol
            System.out.println("Enter stock symbol you would like to trade: ");
            String symbol = user_input.nextLine();

            // Check if the entered stock exists
            int stockIndex = account.find(symbol, StockSimulatorConsole.stocks, "");

            if(stockIndex == -1) {
                System.out.println("Sorry, that stock is not trading.");
            } else {
                // Get the stock object for the entered symbol
                Stock stockTrade = StockSimulatorConsole.stocks[stockIndex];

                // Prompt the user to enter the quantity of shares to trade
                System.out.println("How many shares would you like to trade: ");
                int quantity = user_input.nextInt();

                // Validation on quantity
                if(sharesAvailable(stockTrade, quantity)) {
                    double totalPrice = stockTrade.getPrice() * quantity;
                    // Check if the user has enough cash to execute the trade
                    if(hasEnoughCash(totalPrice)) {
                        System.out.println("The total comes to: \u00A3" + df.format(totalPrice));
                        // Set the quantity of the stock in the user's portfolio
                        stockTrade.setQuantity(quantity);
                        // Update the amount of shares left in the market for that stock
                        stockTrade.updateSharesLeft(quantity);
                        // Set the type of stock to buy
                        stockTrade.setType("buy");
                        // Add the stock to the user's account
                        account.addStockToPortfolio(stockTrade, "buy");
                        // Update the user's cash balance
                        account.updateCashBalance(totalPrice, "buy");
                    } else {
                        System.out.println("Sorry, not enough cash.");
                    }
                } else {
                    System.out.println("Sorry, not enough shares available.");
                }
            }

        } catch(Exception ex) {
            System.out.println("Error: Trade not executed.");
        }

        writeTransaction("bought");
    }

    private void writeTransaction(String action) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt", true))) {
            writer.println(account.getAccountName() + ", " + action + " stock");
        } catch (java.io.IOException e) {
            System.out.println("Error writing transaction to file: " + e.getMessage());
        }
    }
}
