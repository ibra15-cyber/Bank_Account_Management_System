package com.ibra.bankingapp.backend.inter;

public interface BankOperations {
    boolean deposit(double amount);
    boolean withdraw(double amount);
    double checkBalance();
    String getAccountDetails();
}
