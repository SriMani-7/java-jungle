package srimani7.javajungle.track;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Tracker {
    private final List<Transaction> transactions;
    public final DatabaseHandler databaseHandler;

    public Tracker(DatabaseHandler databaseHandler) throws SQLException {
        this.databaseHandler = databaseHandler;
        transactions = databaseHandler.selectAll();
    }

    public void printHistory() {
        String HISTORY_HEADING = String.format("\n| %-12s| %-10s| %-8s| %-9s", "📅Date", "💸Amount", "❓Type", "📝Note");
        System.out.println(HISTORY_HEADING);
        StringBuilder builder = new StringBuilder();
        for (Transaction transaction : transactions) {
            builder.append(String.format("| %-12s| %-10.2f| %-8s| %-8s\n", transaction.date, transaction.amount, transaction.transactionType, transaction.note));
        }
        System.out.println(builder);
    }

    private void add(Scanner scanner) throws SQLException {
        Transaction transaction = new Transaction();
        // date
        System.out.print("Enter date DD/MM/YYYY  📅: ");
        transaction.date = scanner.next();
        if (!transaction.date.matches(Transaction.DATE_REGEX)) {
            System.out.println("Invalid date");
            return;
        }

        // amount
        System.out.print("Enter amount you spend 💸: ");
        try {
            transaction.amount = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid number");
        }

        // type
        System.out.print("Enter transaction type ❓: ");
        transaction.transactionType = transactionType(scanner.next());
        if (transaction.transactionType == null) {
            System.out.println("expected credit/debit");
            return;
        }
        // note
        System.out.print("Enter transaction note 📝: ");
        scanner.nextLine();
        transaction.note = scanner.nextLine();
        transactions.add(transaction);
        databaseHandler.insert(transaction);
    }

    public String transactionType(String input) {
        String in = input.toLowerCase();
        if (in.matches("c(redit)?")) return "CREDIT";
        if (in.matches("d(ebit)")) return "DEBIT";
        return null;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseHandler.getConnection();
        DatabaseHandler databaseHandler = new DatabaseHandler(connection);
        Tracker tracker = new Tracker(databaseHandler);
        String options = """
                1. Add new transaction
                2. Print history
                3. Options
                0. Exit
                """.trim();
        System.out.println("Welcome to the expense tracker !!");
        System.out.println(options);

        // Handle the user input data
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.print("\n> Enter your option : ");
            choice = scanner.nextInt();
            if (choice == 0) System.out.println("See you soon");
            else if (choice == 1) tracker.add(scanner);
            else if (choice == 2) tracker.printHistory();
            else if (choice == 3) System.out.println(options);
            else System.out.println("Wrong option*");
        } while (choice != 0); // loop runs until user enters 0

        // close scanner
        connection.close();
        scanner.close();
    }

}
