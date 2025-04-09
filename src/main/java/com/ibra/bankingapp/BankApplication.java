package com.ibra.bankingapp;

import com.ibra.bankingapp.entity.FixedDepositAccount;
import com.ibra.bankingapp.entity.Transaction;
import com.ibra.bankingapp.service.system.BankSystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;


public class BankApplication extends Application {
    private BankSystem bankSystem;

    @Override
    public void start(Stage primaryStage) {
        bankSystem = new BankSystem();

        // Create sample accounts for testing
        createSampleAccounts();

        TabPane tabPane = new TabPane();

        // Create tabs for different functionalities
        Tab accountCreationTab = createAccountCreationTab();
        Tab transactionTab = createTransactionTab();
        Tab accountDetailsTab = createAccountDetailsTab();

        tabPane.getTabs().addAll(accountCreationTab, transactionTab, accountDetailsTab);

        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setTitle("Bank Account Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createSampleAccounts() {
        bankSystem.createSavingsAccount("John Doe", 1000, 500, 0.03);
        bankSystem.createCurrentAccount("Jane Smith", 2000, 1000);
        bankSystem.createFixedDepositAccount("Robert Johnson", 5000, 0.05, 12);
    }

    private Tab createAccountCreationTab() {
        Tab tab = new Tab("Create Account");
        tab.setClosable(false);

        GridPane grid = new GridPane(); //in this tab we create a grid
        grid.setPadding(new Insets(20)); //the grid will have a padding of 20
        grid.setVgap(10); //the vertical gap is 10
        grid.setHgap(10); //horizontal gap is 10

        Label nameLabel = new Label("Account Holder Name:"); //a label and a text field
        TextField nameField = new TextField();

        Label initialDepositLabel = new Label("Initial Deposit:");
        TextField initialDepositField = new TextField();

        Label accountTypeLabel = new Label("Account Type:"); //creating selection for all the types
        ComboBox<String> accountTypeCombo = new ComboBox<>();
        accountTypeCombo.getItems().addAll("Savings", "Current", "Fixed Deposit");
        accountTypeCombo.setValue("Savings"); //setting default value

        // Additional fields that appear based on account type
        Label additionalLabel1 = new Label("Minimum Balance:");
        TextField additionalField1 = new TextField("500");

        Label additionalLabel2 = new Label("Interest Rate (%):");
        TextField additionalField2 = new TextField("3.0");

        //disabled term of interest
        Label termLabel = new Label("Term (Months):");
        TextField termField = new TextField("12");
        termField.setVisible(false);
        termLabel.setVisible(false);

        // Update additional fields based on account type selection
        accountTypeCombo.setOnAction(e -> {
            String selectedType = accountTypeCombo.getValue();

            if (selectedType.equals("Savings")) {
                additionalLabel1.setText("Minimum Balance:");
                additionalField1.setText("500");
                additionalLabel2.setText("Interest Rate (%):");
                additionalField2.setText("3.0");
                additionalLabel1.setVisible(true);
                additionalField1.setVisible(true);
                additionalLabel2.setVisible(true);
                additionalField2.setVisible(true);
                termLabel.setVisible(false);
                termField.setVisible(false);
            } else if (selectedType.equals("Current")) {
                additionalLabel1.setText("Overdraft Limit:");
                additionalField1.setText("1000");
                additionalLabel1.setVisible(true);
                additionalField1.setVisible(true);
                additionalLabel2.setVisible(false);
                additionalField2.setVisible(false);
                termLabel.setVisible(false);
                termField.setVisible(false);
            } else if (selectedType.equals("Fixed Deposit")) {
                additionalLabel1.setVisible(false);
                additionalField1.setVisible(false);
                additionalLabel2.setText("Interest Rate (%):");
                additionalField2.setText("5.0");
                additionalField2.setVisible(true);
                additionalLabel2.setVisible(true);
                termLabel.setVisible(true);
                termField.setVisible(true);
            }
        });

        Button createButton = new Button("Create Account");
        Label resultLabel = new Label();

        createButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                double initialDeposit = Double.parseDouble(initialDepositField.getText());
                String accountType = accountTypeCombo.getValue();

                if (name.isEmpty()) {
                    resultLabel.setText("Please enter account holder name.");
                    return;
                }

                String accountNumber = ""; //initializing account number as empty string bc it's string is an obj

                if (accountType.equals("Savings")) {
                    double minimumBalance = Double.parseDouble(additionalField1.getText());
                    double interestRate = Double.parseDouble(additionalField2.getText()) / 100.0;
                    accountNumber = bankSystem.createSavingsAccount(name, initialDeposit, minimumBalance, interestRate);
                } else if (accountType.equals("Current")) {
                    double overdraftLimit = Double.parseDouble(additionalField1.getText());
                    accountNumber = bankSystem.createCurrentAccount(name, initialDeposit, overdraftLimit);
                } else if (accountType.equals("Fixed Deposit")) {
                    double interestRate = Double.parseDouble(additionalField2.getText()) / 100.0;
                    int term = Integer.parseInt(termField.getText());
                    accountNumber = bankSystem.createFixedDepositAccount(name, initialDeposit, interestRate, term);
                }

                resultLabel.setText("Account created successfully. Account Number: " + accountNumber);

                // Clear fields
                nameField.clear();
                initialDepositField.clear();

            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numeric values.");
            }
        });

        // Add components to the grid
        grid.add(nameLabel, 0, 0); grid.add(nameField, 1, 0);
        grid.add(initialDepositLabel, 0, 1); grid.add(initialDepositField, 1, 1);
        grid.add(accountTypeLabel, 0, 2); grid.add(accountTypeCombo, 1, 2);
        grid.add(additionalLabel1, 0, 3); grid.add(additionalField1, 1, 3);
        grid.add(additionalLabel2, 0, 4); grid.add(additionalField2, 1, 4);
        grid.add(termLabel, 0, 5); grid.add(termField, 1, 5);
        grid.add(createButton, 1, 6); grid.add(resultLabel, 0, 7, 2, 1);

        tab.setContent(grid);
        return tab;
    }

    private Tab createTransactionTab() {
        Tab tab = new Tab("Transactions");
        tab.setClosable(false);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label accountLabel = new Label("Account Number:");
        ComboBox<String> accountCombo = new ComboBox<>();
        Button refreshButton = new Button("Refresh List");

        refreshButton.setOnAction(e -> {
            accountCombo.getItems().clear();
            accountCombo.getItems().addAll(bankSystem.getAllAccountNumbers());
        });

        // Initial population of account numbers
        accountCombo.getItems().addAll(bankSystem.getAllAccountNumbers());

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button checkBalanceButton = new Button("Check Balance");

        Label resultLabel = new Label();

        // Transaction history section
        Label historyLabel = new Label("Transaction History:");
        ListView<String> historyList = new ListView<>();
        historyList.setPrefHeight(200);

        accountCombo.setOnAction(e -> {
            String accountNumber = accountCombo.getValue();
            if (accountNumber != null) {
                updateTransactionHistory(accountNumber, historyList);
            }
        });

        depositButton.setOnAction(e -> {
            try {
                String accountNumber = accountCombo.getValue();
                double amount = Double.parseDouble(amountField.getText());

                if (accountNumber == null || accountNumber.isEmpty()) {
                    resultLabel.setText("Please select an account.");
                    return;
                }

                boolean success = bankSystem.depositToAccount(accountNumber, amount);

                if (success) {
                    resultLabel.setText("Deposit successful. New balance: $" +
                            String.format("%.2f", bankSystem.checkBalance(accountNumber)));
                    updateTransactionHistory(accountNumber, historyList);
                } else {
                    resultLabel.setText("Deposit failed. Please check the amount.");
                }

            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter a valid amount.");
            }
        });

        withdrawButton.setOnAction(e -> {
            try {
                String accountNumber = accountCombo.getValue();
                double amount = Double.parseDouble(amountField.getText());

                if (accountNumber == null || accountNumber.isEmpty()) {
                    resultLabel.setText("Please select an account.");
                    return;
                }

                boolean success = bankSystem.withdrawFromAccount(accountNumber, amount);

                if (success) {
                    resultLabel.setText("Withdrawal successful. New balance: $" +
                            String.format("%.2f", bankSystem.checkBalance(accountNumber)));
                    updateTransactionHistory(accountNumber, historyList);
                } else {
                    if (bankSystem.getAccount(accountNumber) instanceof FixedDepositAccount) {
                        FixedDepositAccount fdAccount = (FixedDepositAccount) bankSystem.getAccount(accountNumber);
                        if (!fdAccount.isMatured()) {
                            resultLabel.setText("Withdrawal failed. Fixed deposit has not matured yet.");
                            return;
                        }
                    }
                    resultLabel.setText("Withdrawal failed. Insufficient funds or exceeds limits.");
                }

            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter a valid amount.");
            }
        });

        checkBalanceButton.setOnAction(e -> {
            String accountNumber = accountCombo.getValue();

            if (accountNumber == null || accountNumber.isEmpty()) {
                resultLabel.setText("Please select an account.");
                return;
            }

            double balance = bankSystem.checkBalance(accountNumber);
            resultLabel.setText("Current Balance: $" + String.format("%.2f", balance));
        });

        HBox accountSelectionBox = new HBox(10);
        accountSelectionBox.getChildren().addAll(accountCombo, refreshButton);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(depositButton, withdrawButton, checkBalanceButton);

        grid.add(accountLabel, 0, 0);
        grid.add(accountSelectionBox, 1, 0);
        grid.add(amountLabel, 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(buttonBox, 1, 2);
        grid.add(resultLabel, 0, 3, 2, 1);
        grid.add(historyLabel, 0, 4);
        grid.add(historyList, 0, 5, 2, 1);

        tab.setContent(grid);
        return tab;
    }

    private Tab createAccountDetailsTab() {
        Tab tab = new Tab("Account Details");
        tab.setClosable(false);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label accountLabel = new Label("Select Account:");
        ComboBox<String> accountCombo = new ComboBox<>();
        Button refreshButton = new Button("Refresh List");

        refreshButton.setOnAction(e -> {
            accountCombo.getItems().clear();
            accountCombo.getItems().addAll(bankSystem.getAllAccountNumbers());
        });

        accountCombo.getItems().addAll(bankSystem.getAllAccountNumbers());

        TextArea detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setPrefHeight(400);

        accountCombo.setOnAction(e -> {
            String accountNumber = accountCombo.getValue();
            if (accountNumber != null) {
                String details = bankSystem.getAccountDetails(accountNumber);
                detailsArea.setText(details);
            }
        });

        HBox accountSelectionBox = new HBox(10);
        accountSelectionBox.getChildren().addAll(accountCombo, refreshButton);

        grid.add(accountLabel, 0, 0);
        grid.add(accountSelectionBox, 1, 0);
        grid.add(detailsArea, 0, 1, 2, 1);

        tab.setContent(grid);
        return tab;
    }

    private void updateTransactionHistory(String accountNumber, ListView<String> historyList) {
        List<Transaction> transactions = bankSystem.getRecentTransactions(accountNumber, 10);
        ObservableList<String> items = FXCollections.observableArrayList();

        for (Transaction transaction : transactions) {
            items.add(transaction.toString());
        }

        historyList.setItems(items);
    }

    public static void main(String[] args) {
        launch(args);
    }
}