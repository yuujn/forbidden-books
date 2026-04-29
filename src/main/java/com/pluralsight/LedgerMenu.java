package com.pluralsight;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LedgerMenu {
    static Scanner scan = App.scan;
    static Database database = App.database;

    public static void run() {
        System.out.println("# Ledger");
        System.out.println("=========");

        boolean isRunning = true;
        while (isRunning) {
            printLedgerOptions();
            System.out.print("Choose: ");
            String choice = scan.nextLine();

            switch (choice.toLowerCase()) {
                case "a" -> showAllTransactions();
                case "d" -> showDeposits();
                case "p" -> {}
                case "r" -> {}
                case "h" -> isRunning = false;
                default -> {
                    System.out.println("Please enter one of A, D, P, R, or H.");
                }
            }
        }
    }

    static void printLedgerOptions() {
        System.out.println("A) All");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) Reports");
        System.out.println("H) Home");
    }

    static void showTransaction(Transaction transaction) {
        System.out.println("---------------------");
        System.out.printf("Date:        %s%n", transaction.getDate().format(DateTimeFormatter.ofPattern("LLL dd, yyyy")));
        System.out.printf("Time:        %s%n", transaction.getTime().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        System.out.printf("Description: %s%n", transaction.getDescription());
        System.out.printf("Vendor:      %s%n", transaction.getVendor());
        System.out.printf("Amount:      %.2f%n", transaction.getAmount());
        System.out.println("---------------------");
    }

    static void showAllTransactions() {
        for (Transaction transaction : database.getTransactions()) {
            showTransaction(transaction);
        }
    }

    static void showDeposits() {
        for (Transaction transaction : database.getTransactions()) {
            if (transaction.getAmount() >= 0) {
                showTransaction(transaction);
            }
        }
    }
}
