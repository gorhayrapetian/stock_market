package stock.market;

import stock.market.simulator.console.StockSimulatorConsole;

public class Main {
    public static void main(String[] args) {
        StockSimulatorConsole simulator = new StockSimulatorConsole();
        simulator.play();
    }
}
