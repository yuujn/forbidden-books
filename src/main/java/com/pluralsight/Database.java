package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    private String fileName;
    private String[] fieldMap;
    private ArrayList<Transaction> transactions;

    private Database() {
    }

    public static Database openDatabase(String fileName) throws IOException {
        Database db = new Database();
        db.fileName = fileName;

        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufReader = new BufferedReader(fileReader);

        ArrayList<Transaction> transactions = new ArrayList<>();

        // The transactions CSV file must have a header row.
        db.fieldMap = bufReader.readLine().split("\\|");

        db.transactions = new ArrayList<>();
        String line;
        while ((line = bufReader.readLine()) != null) {
            String[] fields = line.split("\\|");
            Transaction transaction = Transaction.fromCSVRow(db.fieldMap, fields);
            db.transactions.add(transaction);
        }

        System.out.println(db.transactions);

        return db;
    }
}
