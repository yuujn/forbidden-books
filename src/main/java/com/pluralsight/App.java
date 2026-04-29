package com.pluralsight;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Database db;
        try {
             db = Database.openDatabase("transactions.csv");
        } catch (IOException e) {
            System.out.println("Failed to open transactions database.");
            throw new RuntimeException(e);
        }
    }
}
