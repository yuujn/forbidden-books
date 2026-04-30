package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class ReportsMenu {
    static Scanner scan = App.scan;
    static Database database = App.database;
    // While we could reference Transaction.dateFmt, since it happens to
    // use the same pattern as we currently use here, I don't want to deal with
    // something silly like an update to the storage format changing the UI.
    static String inputDateFmtPattern = "yyyy-MM-dd";
    static DateTimeFormatter inputDateFmt = DateTimeFormatter.ofPattern(inputDateFmtPattern);

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
                case 6 -> runCustomSearch();
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
        System.out.println("6) Custom Search");
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

    static LocalDate promptOptionalDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateString = scan.nextLine();
            if (dateString.isBlank()) {
                return null;
            }
            try {
                return LocalDate.parse(dateString, inputDateFmt);
            } catch (DateTimeParseException e) {
                System.out.printf("Please enter a date in the %s format, or leave the field blank.%n", inputDateFmtPattern);
            }
        }
    }

    // Okay, look, the correct thing to do here is probably to use
    // OptionalDouble, but... the optional types aren't something we've
    // covered in the class.
    // So, a nullable Double it is.
    static Double promptOptionalDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String numberString = scan.nextLine();
            if (numberString.isBlank()) {
                return null;
            }
            try {
                return Double.parseDouble(numberString);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number, or leave the field blank.");
            }
        }
    }

    static void runCustomSearch() {
        System.out.println("Each of the following fields is optional.");
        System.out.println("If you do not wish to filter your search by one of them,");
        System.out.println("simply leave it blank and press Enter to move on.");

        LocalDate startDate = promptOptionalDate(String.format("Start Date (%s): ", inputDateFmtPattern));
        LocalDate endDate = promptOptionalDate(String.format("End Date (%s): ", inputDateFmtPattern));
        System.out.print("Description: ");
        String description = scan.nextLine();
        System.out.print("Vendor: ");
        String vendorName = scan.nextLine();
        Double minAmount = promptOptionalDouble("Minimum Amount: ");
        Double maxAmount = promptOptionalDouble("Maximum Amount: ");

        Arrays.stream(database.getTransactions())
                .filter(transaction -> startDate == null || transaction.getDate().isAfter(startDate.minusDays(1)))
                .filter(transaction -> endDate == null || transaction.getDate().isBefore(endDate.plusDays(1)))
                .filter(transaction -> description.isBlank() || transaction.getDescription().toLowerCase().contains(description.toLowerCase()))
                .filter(transaction -> vendorName.isBlank() || transaction.getVendor().equalsIgnoreCase(vendorName))
                .filter(transaction -> minAmount == null || transaction.getAmount() >= minAmount)
                .filter(transaction -> maxAmount == null || transaction.getAmount() <= maxAmount)
                .forEach(LedgerMenu::showTransaction);
    }
}
