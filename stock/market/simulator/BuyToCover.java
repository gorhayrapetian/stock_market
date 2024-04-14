package stock.market.simulator;

import stock.market.simulator.console.StockSimulatorConsole;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Allows the user to buy back the stock they sold short in order to exit a short position and cover their position.
 * This class facilitates the process of buying back shares that were previously sold short.
 */
public class BuyToCover extends Trade {

    DecimalFormat df = new DecimalFormat("#.00");

    /**
     * Constructs a BuytoCover object to facilitate the buy-to-cover transaction.
     *
     * @param ac The user's account for trading.
     */
    public BuyToCover(Account ac) {
        super(ac);

        try {
            // Validation on stock symbol
            Scanner user_input = new Scanner(System.in);

            System.out.println("Enter stock symbol you would like to trade: ");
            String symbol = user_input.nextLine();

            int stockIndex = ac.find(symbol, StockSimulatorConsole.stocks, "");

            if(stockIndex == -1) {
                System.out.println("Sorry that stock is not trading.");

            } else {
                Stock stockTrade = StockSimulatorConsole.stocks[stockIndex];

                System.out.println("How many shares would you like to trade: ");
                int quantity = user_input.nextInt();

                // Validation on quantity
                if(sharesAvailable(stockTrade ,quantity)) {
                    double totalPrice = stockTrade.getPrice() * quantity;
                    if(hasEnoughCash(totalPrice)) {
                        System.out.println("The total comes to: \u00A3" + df.format(totalPrice));
                        // Set how many user has of the current stock
                        stockTrade.setQuantity(quantity);
                        // Set stock type to cover
                        stockTrade.setType("cover");

                        if(quantity == stockTrade.getQuantity()) {
                            ac.deleteStockFromPortfolio(stockIndex);
                        } else {
                            ac.reduceStockQuantity(stockIndex, quantity);
                        }
                        // Update the amount of shares left in a stock
                        stockTrade.updateSharesLeft(quantity);
                        // Update user's cash
                        ac.updateCashBalance(totalPrice, "buy");
                    } else {
                        System.out.println("Sorry, not enough cash.");
                    }
                } else {
                    System.out.println("Sorry not enough shares available.");
                }
            }

        } catch(Exception ex) {
            System.out.println("Error trade not gone through");
        }
    }

    /**
     * Overrides the sharesAvailable method in the Trade class.
     * Allows the program to check if there are enough shares already shorted to cover the transaction.
     *
     * @param s The stock to be traded.
     * @param quantity The quantity of shares to be traded.
     * @return true if there are enough shares available for the transaction, false otherwise.
     */
    @Override
    public boolean sharesAvailable(Stock s, int quantity) {
        if(s.getType().equals("short")) {
            return (quantity <= s.getSharesLeft()) && (quantity > 0);
        } else {
            return false;
        }
    }
}
