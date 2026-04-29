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
                case 2 -> reportPreviousMonth();
                case 3 -> reportYearToDate();
                case 4 -> reportPreviousYear();
                case 5 -> searchByVendor();
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

    static void reportPreviousMonth() {
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        for (Transaction transaction : database.getTransactions()) {
            LocalDate date = transaction.getDate();
            if (date.getMonthValue() == lastMonth.getMonthValue() && date.getYear() == lastMonth.getYear()) {
                LedgerMenu.showTransaction(transaction);
            }
        }
    }

    static void reportYearToDate() {
        LocalDateTime now = LocalDateTime.now();
        for (Transaction transaction : database.getTransactions()) {
            LocalDate date = transaction.getDate();
            if (date.getYear() == now.getYear()) {
                LedgerMenu.showTransaction(transaction);
            }
        }
    }

    static void reportPreviousYear() {
        LocalDateTime lastYear = LocalDateTime.now().minusYears(1);
        for (Transaction transaction : database.getTransactions()) {
            LocalDate date = transaction.getDate();
            if (date.getYear() == lastYear.getYear()) {
                LedgerMenu.showTransaction(transaction);
            }
        }
    }

    static void searchByVendor() {
        System.out.print("Enter Vendor Name: ");
        String vendorName = scan.nextLine();

        for (Transaction transaction : database.getTransactions()) {
            if (transaction.getVendor().equalsIgnoreCase(vendorName)) {
                LedgerMenu.showTransaction(transaction);
            }
        }
    }
}
