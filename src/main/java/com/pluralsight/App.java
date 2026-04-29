package com.pluralsight;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class App {
    static Scanner scan = new Scanner(System.in);
    static Database database;

    public static void main(String[] args) {
        try {
            database = Database.openDatabase("transactions.csv");
        } catch (IOException e) {
            System.out.println("Failed to open transactions database.");
            throw new RuntimeException(e);
        }

        runHomeMenu();
    }

    static void printHomeMenuOptions() {
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
    }

    static void runHomeMenu() {
        System.out.println("# Home");
        System.out.println("=======");
        boolean isRunning = true;

        while (isRunning) {
            printHomeMenuOptions();
            System.out.print("Choose: ");
            String choice = scan.nextLine();

            switch (choice.toLowerCase()) {
                case "d" -> runAddDeposit();
                case "p" -> runMakePayment();
                case "l" -> {}
                case "x" -> isRunning = false;
                default -> {
                    System.out.println("Please enter one of D, P, L, or X.");
                }
            }
        }
    }

    static double promptPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            double number = Double.parseDouble(scan.nextLine());
            if (number >= 0) {
                return number;
            } else {
                System.out.println("Please enter a positive number.");
            }
        }
    }

    static void runAddDeposit() {
        LocalDateTime now = LocalDateTime.now();
        System.out.print("Describe the purpose of the deposit: ");
        String description = scan.nextLine();
        System.out.print("State who has paid the deposit: ");
        String vendor = scan.nextLine();
        double amount = promptPositiveDouble("Enter the amount of the deposit: ");

        Transaction transaction = new Transaction(
                now.toLocalDate(),
                now.toLocalTime(),
                description,
                vendor,
                amount
        );

        database.addTransaction(transaction);
    }

    static void runMakePayment() {
        LocalDateTime now = LocalDateTime.now();
        System.out.print("Describe the purpose of the payment: ");
        String description = scan.nextLine();
        System.out.print("State who has paid the payment: ");
        String vendor = scan.nextLine();
        double amount = promptPositiveDouble("Enter the amount of the deposit: ");

        Transaction transaction = new Transaction(
                now.toLocalDate(),
                now.toLocalTime(),
                description,
                vendor,
                -amount
        );

        database.addTransaction(transaction);
    }
}
