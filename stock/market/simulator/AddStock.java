package stock.market.simulator;

import java.io.*;
import java.io.FileNotFoundException;

/**
 * Provides initial stock data and handles serialization of stock data.
 * This class initializes an array of Stock objects representing initial stocks available in the simulation.
 * It also provides static methods to serialize and deserialize stock data to and from a file.
 */
public class AddStock {
    // Initialize stock data
    private static final Stock[] stocks = {
            new Stock("AAPL", 122.02, 100000000),
            new Stock("MSFT", 42.60, 200000000),
            new Stock("FB", 81.67, 100000000),
            new Stock("TWTR", 50.47, 100000000),
            new Stock("PG", 82.83, 100000000),
            new Stock("JD", 29.22, 100000000),
            new Stock("GOOG", 542.56, 100000000),
            new Stock("WMT", 80.71, 100000000),
            new Stock("TSCO", 242.13, 100000000),
            new Stock("VA", 30.36, 100000000),
            new Stock("BT", 65.65, 100000000),
            new Stock("HSBA", 581.08, 100000000)
    };

    /**
     * Save the array of stocks to a file.
     * <p>
     * This method serializes the array of Stock objects to a file named "stocks.txt".
     * </p>
     *
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public static void saveStocks() throws IOException, FileNotFoundException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("stocks.txt"))) {
            objectOutputStream.writeObject(stocks);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read stocks from a file and update the stocks array.
     * <p>
     * This method deserializes Stock objects from the file "stocks.txt"
     * and updates the stocks array with the read data.
     * </p>
     *
     * @throws IOException            if an I/O error occurs while reading from the file.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    public static void readStocks() throws IOException, ClassNotFoundException, FileNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("stocks.txt"))) {
            Stock[] stocksRead = (Stock[]) objectInputStream.readObject();
            System.arraycopy(stocksRead, 0, stocks, 0, stocksRead.length);
        } catch (java.io.IOException | java.lang.ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
