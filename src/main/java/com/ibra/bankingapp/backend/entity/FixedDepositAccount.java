package com.ibra.bankingapp.backend.entity;

import com.ibra.bankingapp.backend.inter.InterestBearing;

import java.time.LocalDate;

public class FixedDepositAccount extends Account implements InterestBearing {
    //Fixed account has interest rate, maturityDate
    private double interestRate;
    private LocalDate maturityDate;
    private boolean hasMatured;

    public FixedDepositAccount(String accountNumber,
                               String accountHolderName,
                               double depositAmount,
                               double interestRate,
                               int termInMonths) {
        super(accountNumber, accountHolderName, depositAmount);
        this.interestRate = interestRate;
        this.maturityDate = LocalDate.now().plusMonths(termInMonths);
        this.hasMatured = false;
    }

    @Override
    public boolean withdraw(double amount) {
        //you can't withdraw when the account has not matured yet
        if (!hasMatured && LocalDate.now().isBefore(maturityDate)) {
            return false;
        }

        if (amount <= 0 || amount > balance) {
            return false; //you can't withdraw when your account is less
        }

        balance -= amount;
        transactionHistory.addTransaction(
                new Transaction("WITHDRAWAL", amount, balance)
        );
        return true;
    }

    @Override
    public boolean deposit(double amount) {
        return false;         // Fixed deposit accounts don't allow additional deposits
    }

    @Override
    public double calculateInterest() {
        return balance * interestRate;
    }

    //fixed account just like savings attracts interest
    @Override
    public void applyInterest() {
        double interest = calculateInterest();
        balance += interest;
        transactionHistory.addTransaction(
                new Transaction("INTEREST", interest, balance)
        );
    }

    //if the account has matured and the time is before maturity isn't true aka has matured
    public void checkMaturity() {
        if (!hasMatured && !LocalDate.now().isBefore(maturityDate)) {
            hasMatured = true;

            // Apply interest upon maturity
            applyInterest();
        }
    }

    @Override
    public String getAccountDetails() {
        return super.getAccountDetails() +
                String.format("\nAccount Type: Fixed Deposit\nInterest Rate: %.2f%%\nMaturity Date: %s\nStatus: %s",
                        interestRate * 100, maturityDate, hasMatured ? "Matured" : "Not Matured");
    }

    // Getters
    public double getInterestRate() { return interestRate; }
    public LocalDate getMaturityDate() { return maturityDate; }
    public boolean isMatured() { return hasMatured; }
}