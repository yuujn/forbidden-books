package com.pluralsight;

import java.util.Scanner;

public class LedgerMenu {
    static Scanner scan = App.scan;

    public static void run() {
        System.out.println("# Ledger");
        System.out.println("=========");

        boolean isRunning = true;
        while (isRunning) {
            printLedgerOptions();
            System.out.print("Choose: ");
            String choice = scan.nextLine();

            switch (choice.toLowerCase()) {
                case "a" -> {}
                case "d" -> {}
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
}
