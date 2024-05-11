package stock.market.simulator;

/**
 * Abstract base class for stock transactions, providing foundational elements
 * and functionality common to all transaction types.
 */
public abstract class AbstractTransaction {
    protected Account account;
    protected Stock stock;
    protected int quantity;
    protected FileManager fileManager;

    /**
     * Constructs a new transaction object.
     *
     * @param account     The user's trading account involved in the transaction.
     * @param stock       The stock involved in the transaction.
     * @param quantity    The number of shares involved in the transaction.
     * @param fileManager The file manager for logging the transaction to storage.
     */
    public AbstractTransaction(Account account, Stock stock, int quantity, FileManager fileManager) {
        this.account = account;
        this.stock = stock;
        this.quantity = quantity;
        this.fileManager = fileManager;
    }

    /**
     * Executes the transaction.
     *
     * @throws Exception If there is an issue executing the transaction.
     */
    public abstract void execute() throws Exception;

    /**
     * Calculates the total monetary amount for the transaction.
     *
     * @return The total monetary amount.
     */
    protected abstract double calculateTransactionAmount();
}
