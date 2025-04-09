package com.ibra.bankingapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BankingController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}