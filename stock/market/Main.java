package stock.market;

import stock.market.simulator.console.StockSimulatorConsole;

/**
 * Main class to start the stock market simulator application.
 */
public class Main {

    /**
     * Main method to start the application.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        StockSimulatorConsole simulator = new StockSimulatorConsole();
        simulator.run();
    }
}
