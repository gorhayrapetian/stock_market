package stock.market.simulator.console;

import stock.market.simulator.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * StockSimulatorConsole is a class representing a stock simulator console application.
 * It allows users to simulate trading stocks.
 */
public class StockSimulatorConsole {
    private ArrayList<Stock> stocks;
    private FileManager fileManager = new FileManager();
    private Account currentAccount;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a new StockSimulatorConsole object and initializes the list of stocks.
     */
    public StockSimulatorConsole() {
        stocks = new ArrayList<>();
        initializeStocks();
    }

    /**
     * Initializes the list of stocks with some predefined stocks.
     */
    private void initializeStocks() {
        stocks.add(new Stock("AAPL", 122.02, 100000000));
        stocks.add(new Stock("MSFT", 42.60, 200000000));
        stocks.add(new Stock("FB", 81.67, 100000000));
        stocks.add(new Stock("TWTR", 50.47, 100000000));
        stocks.add(new Stock("PG", 82.83, 100000000));
        stocks.add(new Stock("JD", 29.22, 100000000));
        stocks.add(new Stock("GOOG", 542.56, 100000000));
        stocks.add(new Stock("WMT", 80.71, 100000000));
        stocks.add(new Stock("TSCO", 242.13, 100000000));
        stocks.add(new Stock("VA", 30.36, 100000000));
        stocks.add(new Stock("BT", 65.65, 100000000));
        stocks.add(new Stock("HSBA", 581.08, 100000000));
    }

    /**
     * Runs the stock market simulator console application. It displays a menu for users to login, register,
     * or exit the application, and directs them to corresponding functionalities based on their choice.
     */
    public void run() {
        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome to the Stock Market Simulator!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (login(scanner)) {
                        tradeMenu(scanner);
                    }
                    break;
                case "2":
                    register(scanner);
                    break;
                case "3":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    /**
     * Handles the login functionality. Prompts the user to enter their username, checks if the user exists,
     * and creates a new account if the user does not exist.
     *
     * @param scanner Scanner object for user input
     * @return True if login is successful, otherwise false
     */
    private boolean login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        try {
            if (fileManager.userExists(username)) {
                int initialCash = 10000;
                currentAccount = new Account(username, initialCash);
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Username not found. Please register.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Handles the registration functionality. Prompts the user to choose a username and registers them.
     *
     * @param scanner Scanner object for user input
     */
    private void register(Scanner scanner) {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine();

        try {
            if (!fileManager.userExists(username)) {
                fileManager.registerUser(username);
                System.out.println("Registration successful. Please login.");
            } else {
                System.out.println("Username already exists. Please try another one.");
            }
        } catch (IOException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays the trading menu options and executes corresponding functionalities based on user choice.
     *
     * @param scanner Scanner object for user input
     */
    private void tradeMenu(Scanner scanner) {
        boolean logout = false;

        while (!logout) {
            System.out.println("\nMenu:");
            System.out.println("1. Buy Stocks");
            System.out.println("2. Sell Stocks");
            System.out.println("3. View Portfolio");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    performBuy();
                    break;
                case "2":
                    performSell();
                    break;
                case "3":
                    if (currentAccount != null) {
                        currentAccount.displayPortfolio();
                    }
                    break;
                case "4":
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    /**
     * Handles the process of buying stocks. Displays available stocks, prompts the user for input,
     * and executes the buy transaction.
     */
    private void performBuy() {
        System.out.println("Available Stocks to Buy:");
        for (Stock stock : getAllStocks()) {
            System.out.println("Symbol: " + stock.getSymbol() + ", Price: $" + stock.getPrice() + ", Shares Available: " + stock.getSharesLeft());
        }

        System.out.println("Enter stock symbol you would like to trade: ");
        String symbol = scanner.nextLine();
        Stock stock = getStockBySymbol(symbol);
        if (stock == null) {
            System.out.println("Sorry, that stock is not trading.");
            return;
        }

        System.out.println("How many shares would you like to buy: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        if(quantity > 0 && stock.getSharesLeft() >= quantity) {
            Buy buy = new Buy(currentAccount, stock, quantity, this, fileManager);
            buy.execute();
        } else {
            System.out.println("Invalid quantity or not enough shares available.");
        }
    }

    /**
     * Handles the process of selling stocks. Displays available stocks held by the user, prompts the user for input,
     * and executes the sell transaction.
     */
    private void performSell() {
        System.out.println("Stocks Available to Sell:");
        for (Stock stock : currentAccount.getHeldStocks()) {
            System.out.println("Symbol: " + stock.getSymbol() + ", Quantity: " + stock.getQuantity());
        }

        System.out.println("Enter the stock symbol you would like to sell:");
        String symbol = scanner.nextLine();
        Stock stock = getStockBySymbol(symbol);
        if (stock == null) {
            System.out.println("Sorry, that stock is not available for trading.");
            return;
        }

        System.out.println("How many shares would you like to sell:");
        int quantity = Integer.parseInt(scanner.nextLine());
        if(quantity > 0 && stock.getQuantity() >= quantity) {
            Sell sell = new Sell(currentAccount, stock, quantity, this, fileManager);
            sell.execute();
        } else {
            System.out.println("Invalid quantity or not enough shares to sell.");
        }
    }

    /**
     * Retrieves all available stocks.
     *
     * @return ArrayList of all available stocks
     */
    public ArrayList<Stock> getAllStocks() {
        return new ArrayList<>(stocks);
    }

    /**
     * Retrieves a stock by its symbol.
     *
     * @param symbol The symbol of the stock to retrieve
     * @return The Stock object corresponding to the symbol, or null if not found
     */
    public Stock getStockBySymbol(String symbol) {
        return stocks.stream()
                .filter(s -> s.getSymbol().equals(symbol))
                .findFirst()
                .orElse(null);
    }
}
