package stock.market.simulator;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileManager {
    private String TRANSACTIONS_FILE = "C:\\Users\\HP\\Desktop\\oop_project\\stock\\market\\simulator\\transactions.txt";
    private String ACCOUNTS_FILE = "C:\\Users\\HP\\Desktop\\oop_project\\stock\\market\\simulator\\accounts.txt";

    /**
     * Writes a transaction record to the transactions file.
     *
     * @param username        The user's name who performed the transaction.
     * @param stock           The stock symbol involved in the transaction.
     * @param price           The price at which the stock was traded.
     * @param transactionType The type of transaction (e.g., "buy" or "sell").
     * @param totalPrice
     */
    public void writeTransaction(String username, String stock, double price, String transactionType, double totalPrice) {
        try (PrintWriter out = new PrintWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            out.printf("%s, %s, %.2f, %s%n", username, stock, price, transactionType);
        } catch (IOException e) {
            System.err.println("Error writing to transactions file: " + e.getMessage());
        }
    }

    /**
     * Registers a new user by writing their username to the accounts file.
     *
     * @param username The username to register.
     * @return true if the registration was successful, false if the username already exists.
     */
    public boolean registerUser(String username) throws FileNotFoundException {
        Set<String> existingUsers = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingUsers.add(line.trim());
            }
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException("The accounts file could not be found.");
        } catch (IOException e) {
            System.err.println("Error reading accounts file: " + e.getMessage());
            return false;
        }


        // Check if the username already exists
        if (existingUsers.contains(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return false;
        }

        // Write the new username to the file
        try (PrintWriter out = new PrintWriter(new FileWriter(ACCOUNTS_FILE, true))) {
            out.println(username);
        } catch (IOException e) {
            System.err.println("Error writing to accounts file: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Checks if a user with the given username exists in the accounts file.
     *
     * @param username The username to check for existence
     * @return True if the user exists, otherwise false
     * @throws IOException If an I/O error occurs while reading the accounts file
     */
    public boolean userExists(String username) throws java.io.FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Accounts file not found: " + e.getMessage());
        }
        return false;
    }
}
