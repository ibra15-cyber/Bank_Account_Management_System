package com.ibra.bankingapp.entity;

import com.ibra.bankingapp.service.inter.BankOperations;

import java.util.List;

public abstract class Account implements BankOperations {
    protected String accountNumber;
    protected String accountHolderName;
    protected double balance;
    protected TransactionLinkedList transactionHistory;

    public Account(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.transactionHistory = new TransactionLinkedList();

        // Record initial deposit as a transaction
        if (initialDeposit > 0) {
            this.transactionHistory.addTransaction(
                    new Transaction("INITIAL_DEPOSIT", initialDeposit, initialDeposit)
            );
        }
    }

    // Concrete implementation of BankOperations methods
    @Override
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }

        balance += amount;
        transactionHistory.addTransaction(
                new Transaction("DEPOSIT", amount, balance)
        );
        return true;
    }

    @Override
    public double checkBalance() {
        return balance;
    }

    // Abstract method to be implemented by subclasses
    @Override
    public abstract boolean withdraw(double amount);

    @Override
    public String getAccountDetails() {
        return String.format("Account Number: %s\nAccount Holder: %s\nCurrent Balance: $%.2f",
                accountNumber, accountHolderName, balance);
    }

    // Methods to access transaction history
    public List<Transaction> getRecentTransactions(int count) {
        return transactionHistory.getRecentTransactions(count);
    }

    public List<Transaction> getAllTransactions() {
        return transactionHistory.getAllTransactions();
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
}