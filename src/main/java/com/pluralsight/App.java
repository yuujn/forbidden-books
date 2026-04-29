package com.pluralsight;

import java.io.IOException;
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
            String choice = scan.next();

            switch (choice.toLowerCase()) {
                case "d" -> {}
                case "p" -> {}
                case "l" -> {}
                case "x" -> isRunning = false;
                default -> {
                    System.out.println("Please enter one of D, P, L, or X.");
                }
            }
        }
    }
}
