package com.ibra.bankingapp.backend.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private int transactionId;
    private String type; // "DEPOSIT", "WITHDRAWAL", "INTEREST"
    private double amount;
    private double balanceAfterTransaction;
    private LocalDateTime timestamp;

    // Constructor, getters, setters
    public Transaction(String type, double amount, double balanceAfterTransaction) {
        this.transactionId = generateId();
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.timestamp = LocalDateTime.now();
    }

    private int generateId() {
        // Simple implementation - in a real system, this would be more sophisticated
        return (int)(Math.random() * 100000);
    }

    // Getters and setters
    public int getTransactionId() { return transactionId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfterTransaction() { return balanceAfterTransaction; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("ID: %d | Type: %s | Amount: Ghs%.2f | Balance: Ghs%.2f | Time: %s",
                transactionId, type, amount, balanceAfterTransaction,
                timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}