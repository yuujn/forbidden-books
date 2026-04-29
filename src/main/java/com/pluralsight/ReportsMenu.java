package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ReportsMenu {
    static Scanner scan = App.scan;
    static Database database = App.database;

    public static void run() {
        boolean isRunning = true;

        while (isRunning) {
            printReportOptions();
            System.out.print("Choose: ");
            int choice = Integer.parseInt(scan.nextLine());

            switch (choice) {
                case 1 -> reportMonthToDate();
                case 2 -> {}
                case 3 -> {}
                case 4 -> {}
                case 5 -> {}
                case 0 -> isRunning = false;
                default -> System.out.println("Please enter one of 0 through 5.");
            }
        }
    }

    static void printReportOptions() {
        System.out.println("1) Month To Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("0) Back");
    }

    static void reportMonthToDate() {
        LocalDateTime now = LocalDateTime.now();
        for (Transaction transaction : database.getTransactions()) {
            LocalDate date = transaction.getDate();
            if (date.getMonthValue() == now.getMonthValue() && date.getYear() == now.getYear()) {
                LedgerMenu.showTransaction(transaction);
            }
        }
    }
}
