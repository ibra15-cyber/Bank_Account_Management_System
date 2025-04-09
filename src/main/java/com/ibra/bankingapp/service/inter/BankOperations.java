package com.ibra.bankingapp.service.inter;

public interface BankOperations {
    boolean deposit(double amount);
    boolean withdraw(double amount);
    double checkBalance();
    String getAccountDetails();
}
