package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;
    private static DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Transaction() {
    }

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public static Transaction fromCSVRow(String[] fieldMap, String[] fields) {
        Transaction transaction = new Transaction();
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            switch (fieldMap[i].toLowerCase()) {
                case "date" -> transaction.date = LocalDate.parse(field, dateFmt);
                case "time" -> transaction.time = LocalTime.parse(field, timeFmt);
                case "description" -> transaction.description = field;
                case "vendor" -> transaction.vendor = field;
                case "amount" -> transaction.amount = Double.parseDouble(field);
            }
        }
        return transaction;
    }

    public String toCSVRow(String[] fieldMap) {
        StringBuilder row = new StringBuilder();
        for (int i = 0; i < fieldMap.length; i++) {
            String field = fieldMap[i];
            String val = switch (field.toLowerCase()) {
                case "date" -> date.format(dateFmt);
                case "time" -> time.format(timeFmt);
                case "description" -> description;
                case "vendor" -> vendor;
                case "amount" -> Double.toString(amount);
                default -> null;
            };
            if (val != null) {
                if (i > 0) {
                    row.append("|");
                }
                row.append(val);
            }
        }
        return row.toString();
    }
}
