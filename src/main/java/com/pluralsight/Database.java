package com.pluralsight;

import java.io.*;
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

        // The transactions CSV file must have a header row.
        db.fieldMap = bufReader.readLine().split("\\|");

        db.transactions = new ArrayList<>();
        String line;
        while ((line = bufReader.readLine()) != null) {
            String[] fields = line.split("\\|");
            Transaction transaction = Transaction.fromCSVRow(db.fieldMap, fields);
            db.transactions.add(transaction);
        }

        return db;
    }

    public void addTransaction(Transaction transaction) throws IOException {
        transactions.add(transaction);
        saveChanges();
    }

    public String[] getFieldMap() {
        return this.fieldMap;
    }

    private void saveChanges() throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufWriter = new BufferedWriter(fileWriter);

        String header = String.join("|", fieldMap);
        bufWriter.write(header);
        bufWriter.newLine();

        for (Transaction transaction : transactions) {
            bufWriter.write(transaction.toCSVRow(fieldMap));
            bufWriter.newLine();
        }

        bufWriter.close();
    }

    public Transaction[] getTransactions() {
        Transaction[] array = new Transaction[transactions.size()];
        return transactions.toArray(array);
    }
}
