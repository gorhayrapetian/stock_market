package stock.market.simulator.console;

import stock.market.simulator.*;
import java.io.*;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Simulates a stock market trading platform where users can manage accounts,
 * buy, sell, and trade stocks.
 */
public class StockSimulatorConsole {
    private final Scanner userInput = new Scanner(System.in);
    private final DecimalFormat decimalFormat = new DecimalFormat("#.00");
    private static final ArrayList<Account> accounts = new ArrayList<>();
    private Account currentAccount;

    // Array to hold stocks
    public static Stock[] stocks;

    /**
     * Constructs a new StockSimulatorConsole object.
     */
    public StockSimulatorConsole() {

    }

    private void initialize() throws IOException, ClassNotFoundException {

    }

    /**
     * Plays the stock market simulator.
     */
    public void play() {

    }

    /**
     * Displays the main menu for the user to interact with.
     *
     * @param account The current account.
     */
    public void showMenu(Account account) throws IOException {
        System.out.println("\nSelect an option:\n"
                + "1. Portfolio\n"
                + "2. Search Stock\n"
                + "3. Trade Stock\n"
                + "4. Exit\n");

        int selection;
        try {
            selection = userInput.nextInt();
        } catch (InputMismatchException ex) {
            selection = 5;
            userInput.nextLine(); // Clear the invalid input
        }

        switch (selection) {
            case 1:
                if (account != null) {
                    account.displayPortfolio();
                } else {
                    System.out.println("No account selected. Please set up or select an account.");
                }
                break;
            case 2:
                searchStock(account);
                break;
            case 3:
                decideTransaction(account);
                break;
            case 4:
                exit();
                break;
            default:
                System.out.println("Invalid selection. Try again.");
                break;
        }
    }

    /**
     * Allows the user to decide on a transaction (buy, sell, etc.).
     *
     * @param account The current account.
     */
    public void decideTransaction(Account account) {
        if (account != null) {
            System.out.println("\nSelect an option:\n"
                    + "1. Buy\n"
                    + "2. Sell\n"
                    + "3. Sell Short\n"
                    + "4. Buy to Cover\n"
                    + "5. Back\n");

            int selection;
            try {
                selection = userInput.nextInt();
            } catch (InputMismatchException ex) {
                selection = 5;
                userInput.nextLine(); // Clear the invalid input
            }

            switch (selection) {
                case 1:
                    new Buy(account);
                    break;
                case 2:
                    new Sell(account);
                    break;
                case 3:
                    new SellShort(account);
                    break;
                case 4:
                    new BuyToCover(account);
                    break;
                default:
                    System.out.println("Invalid Selection. Returning to main menu.");
                    break;
            }
        } else {
            System.out.println("No account selected. Please set up or select an account.");
        }
    }

    /**
     * Searches for a stock based on the user's input.
     *
     * @param account The current account.
     */
    public void searchStock(Account account) {
        if (account != null) {
            System.out.println("Enter stock symbol: ");
            String stockSymbol = userInput.next();
            userInput.nextLine(); // Consume the newline character

            int position = account.find(stockSymbol, stocks, "");
            if (position == -1) {
                System.out.println("Sorry, that stock is not trading.");
            } else {
                System.out.println(stocks[position].getSymbol() + ", " + " trading at: £"
                        + decimalFormat.format(stocks[position].getPrice()));
            }
        } else {
            System.out.println("No account selected. Please set up or select an account.");
        }
    }

    /**
     * Searches for an account by its name.
     *
     * @param accountName The name of the account to search for.
     * @return The index of the account in the accounts list, or -1 if not found.
     */
    public static int searchAccounts(String accountName) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accountName.equals(accounts.get(i).getAccountName())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Saves the stocks data to a file.
     */
    public void saveStocks() throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("stocks.txt"))) {
            objectOutputStream.writeObject(stocks);
        }
    }

    /**
     * Reads the stocks data from a file.
     */
    public static void readStocks() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("stocks.txt"))) {
            stocks = (Stock[]) objectInputStream.readObject();
        }
    }

    /**
     * Saves the accounts data to a file.
     */
    public void saveAccounts() throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("stock/market/simulator/accounts.txt"))) {
            objectOutputStream.writeObject(accounts);
        }
    }

    /**
     * Registers a new account for the user.
     */
    private void registerAccount() throws IOException {
        System.out.println("Enter a unique Account Name: ");
        String accountName = userInput.next();
        userInput.nextLine(); // Consume the newline character

        if (searchAccounts(accountName) == -1) {
            System.out.println("Congratulations! Account registered successfully.");
            currentAccount = new Account(accountName, 0); // Set starting money to 0
            accounts.add(currentAccount);
            saveAccounts();
        } else {
            System.out.println("Account name already exists. Please choose a different one.");
        }
    }

    /**
     * Reads the accounts data from a file.
     */
    public static void readAccounts() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("stock/market/simulator/accounts.txt"))) {
            accounts.addAll((ArrayList<Account>) objectInputStream.readObject());
        }
    }

    /**
     * Exits the program, saving data before closing.
     */
    public void exit() throws IOException {
        System.out.println("Exiting...");
        saveStocks();
        saveAccounts();
        System.exit(0);
    }
}
