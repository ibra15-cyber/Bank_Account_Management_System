package com.ibra.bankingapp.backend.entity;

public class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolderName, double initialDeposit,
                          double overdraftLimit) {
        super(accountNumber, accountHolderName, initialDeposit);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }

        // Check if withdrawal exceeds overdraft limit
        if (balance - amount < -overdraftLimit) {
            return false;
        }

        balance -= amount;
        transactionHistory.addTransaction(
                new Transaction("WITHDRAWAL", amount, balance)
        );
        return true;
    }

    @Override
    public String getAccountDetails() {
        return super.getAccountDetails() +
                String.format("\nAccount Type: Current\nOverdraft Limit: Ghs%.2f", overdraftLimit);
    }

    // Getter
    public double getOverdraftLimit() { return overdraftLimit; }
}