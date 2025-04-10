package com.ibra.bankingapp.backend.entity;

import com.ibra.bankingapp.backend.inter.InterestBearing;

public class SavingsAccount extends Account implements InterestBearing {
    //savings account has minimum balance and interest rate
    private double minimumBalance;
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolderName, double initialDeposit,
                          double minimumBalance, double interestRate) {
        super(accountNumber, accountHolderName, initialDeposit);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false; //you can't withdraw when your account is less
        }

        // Check if withdrawal would violate minimum balance
        if (balance - amount < minimumBalance) {
            return false;
        }

        balance -= amount;

        //then add it to transactions
        transactionHistory.addTransaction(
                new Transaction("WITHDRAWAL", amount, balance)
        );
        return true;
    }

    @Override
    public double calculateInterest() {
        return balance * interestRate;
    }

    @Override
    public void applyInterest() {
        double interest = calculateInterest();
        balance += interest;
        transactionHistory.addTransaction(
                new Transaction("INTEREST", interest, balance)
        );
    }

    @Override
    public String getAccountDetails() {
        return super.getAccountDetails() +
                String.format("\nAccount Type: Savings\nMinimum Balance: Ghs%.2f\nInterest Rate: %.2f%%",
                        minimumBalance, interestRate * 100);
    }

    // Getters
    public double getMinimumBalance() { return minimumBalance; }
    public double getInterestRate() { return interestRate; }
}
