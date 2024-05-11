package stock.market.simulator;

/**
 * FileNotFoundException is a custom exception class representing a file not found error.
 */
public class FileNotFoundException extends Exception {

    /**
     * Constructs a FileNotFoundException with a default error message.
     */
    public FileNotFoundException() {
        super("File not found exception");
    }

    /**
     * Constructs a FileNotFoundException with a specified error message.
     *
     * @param message The error message to include
     */
    public FileNotFoundException(String message) {
        super(message);
    }
}
