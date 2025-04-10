package com.ibra.bankingapp.backend.entity;


import com.ibra.bankingapp.backend.inter.BankOperations;

import java.util.List;

public abstract class Account implements BankOperations {
    //states/instance variable similar to all accounts
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
        //transaction(type, deposit, balance after transaction)
        if (initialDeposit > 0) {
            this.transactionHistory.addTransaction(
                    new Transaction("INITIAL_DEPOSIT", initialDeposit, initialDeposit)
            );
        }
    }

    // behaviour common to all account types implemented
    // Concrete implementation of BankOperations/interface methods
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
        return String.format("Account Number: %s\nAccount Holder: %s\nCurrent Balance: Ghs%.2f",
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