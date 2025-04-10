package com.ibra.bankingapp.backend;

import com.ibra.bankingapp.backend.entity.*;

import java.util.*;

public class BankSystem {
    //we create a map of account number and Account
    private Map<String, Account> accounts;

    public BankSystem() {
        this.accounts = new HashMap<>();
    }

    //fn to create savings account
    public String createSavingsAccount(String holderName,
                                       double initialDeposit,
                                       double minimumBalance,
                                       double interestRate) {
        String accountNumber = generateAccountNumber();
        SavingsAccount account = new SavingsAccount(
                accountNumber, holderName, initialDeposit, minimumBalance, interestRate
        );
        accounts.put(accountNumber, account);
        return accountNumber;
    }

    //fn to create current account
    public String createCurrentAccount(String holderName, double initialDeposit,
                                       double overdraftLimit) {
        String accountNumber = generateAccountNumber();
        CurrentAccount account = new CurrentAccount(
                accountNumber, holderName, initialDeposit, overdraftLimit
        );
        accounts.put(accountNumber, account);
        return accountNumber;
    }

    //fn to create fixed deposit account
    public String createFixedDepositAccount(String holderName, double depositAmount,
                                            double interestRate, int termInMonths) {
        String accountNumber = generateAccountNumber();
        FixedDepositAccount account = new FixedDepositAccount(
                accountNumber, holderName, depositAmount, interestRate, termInMonths
        );
        accounts.put(accountNumber, account);
        return accountNumber;
    }

    //a fn we can call you do withdrawal
    public boolean depositToAccount(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            return false;
        }
        return account.deposit(amount);
    }

    //a fn we can call you do withdrawal
    public boolean withdrawFromAccount(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            return false;
        }
        return account.withdraw(amount);
    }

    //a fn to call to check balance
    public double checkBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            return -1; // Indicating account not found
        }
        return account.checkBalance();
    }

    //fn to call account details
    public String getAccountDetails(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            return "Account not found";
        }
        return account.getAccountDetails();
    }

    //fn to call all recent transactions
    public List<Transaction> getRecentTransactions(String accountNumber, int count) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            return new ArrayList<>();
        }
        return account.getRecentTransactions(count);
    }

    public boolean accountExists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public void applyInterestToSavingsAccounts() {
        for (Account account : accounts.values()) {
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).applyInterest();
            }
        }
    }

    public void checkFixedDepositMaturity() {
        for (Account account : accounts.values()) {
            if (account instanceof FixedDepositAccount) {
                ((FixedDepositAccount) account).checkMaturity();
            }
        }
    }

    //generating account number
    private String generateAccountNumber() {
        // Simple implementation - in a real system, this would be more sophisticated
        return "ACC" + (100000 + new Random().nextInt(900000));
    }

    //get all account numbers it returns the account number and the account
    public List<String> getAllAccountNumbers() {
         List<String> str = new ArrayList<>(accounts.keySet());
        System.out.println(str.toString());
        return str;

    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}