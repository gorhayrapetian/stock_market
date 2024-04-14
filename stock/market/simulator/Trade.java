package stock.market.simulator;

/**
 * Represents a trade operation in the stock market simulation.
 */
public class Trade {
    String stockSymbol;
    int tradeQuantity;
    final Account account;

    /**
     * Constructs a Trade object with the specified account.
     *
     * @param account the account performing the trade
     */
    public Trade(Account account) {
        this.account = account;
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
     * Checks if the account has enough cash to perform the trade.
     *
     * @param totalCost the total cost of the trade
     * @return true if the account has enough cash, false otherwise
     */
    public boolean hasEnoughCash(double totalCost) {
        return totalCost <= account.getCashBalance();
    }
}
