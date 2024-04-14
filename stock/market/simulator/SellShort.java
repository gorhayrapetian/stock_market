package stock.market.simulator;

import stock.market.simulator.console.StockSimulatorConsole;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Allows the user to trade stocks worth more than their total account value by selling short.
 * This is an advanced feature for the stock simulator.
 */
public class SellShort extends Trade{

    DecimalFormat df = new DecimalFormat("#.00");

    /**
     * Constructs a SellShort object to facilitate the process of selling stocks short.
     *
     * @param account The user's account for trading.
     */
    public SellShort(Account account) {
        super(account);

        try {
            // Validation on stock symbol
            Scanner user_input = new Scanner(System.in);

            System.out.println("Enter stock symbol you would like to trade: ");
            String symbol = user_input.nextLine();

            int stockIndex = account.find(symbol, StockSimulatorConsole.stocks, "");

            if(stockIndex == -1) {
                System.out.println("Sorry, that stock is not trading.");
            } else {
                Stock stockTrade = StockSimulatorConsole.stocks[stockIndex];

                System.out.println("How many shares would you like to trade: ");
                int quantity = user_input.nextInt();

                // Validation on quantity
                if(sharesAvailable(stockTrade ,quantity)) {
                    double totalPrice = stockTrade.getPrice() * quantity;
                    System.out.println("The total comes to: \u00A3" + df.format(totalPrice));
                    // Set the quantity of the stock in the user's portfolio
                    stockTrade.setQuantity(quantity);
                    // Set stock type to short
                    stockTrade.setType("short");
                    // Update the amount of shares left in the market for that stock
                    stockTrade.updateSharesLeft(quantity, "sell");
                    // Add the stock to the user's account
                    account.addStockToPortfolio(stockTrade, "short");
                    // Update the user's cash balance
                    account.updateCashBalance(totalPrice, "sell");
                } else {
                    System.out.println("Sorry, not enough shares available.");
                }
            }

        } catch(Exception ex) {
            System.out.println("Error: Trade not executed.");
        }
    }

    /**
     * Overrides the sharesAvailable method in the Trade class.
     * Allows the program to check if there are enough shares to sell short.
     *
     * @param s The stock to be traded.
     * @param quantity The quantity of shares to be traded.
     * @return true if there are enough shares available for the short sale, false otherwise.
     */
    @Override
    public boolean sharesAvailable(Stock s, int quantity) {
        return (quantity + s.getSharesLeft()) <= s.getTotalShares();
    }
}
