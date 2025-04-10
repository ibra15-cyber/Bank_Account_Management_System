package com.ibra.bankingapp.backend.entity;

public class TransactionNode {
    private Transaction transaction; //like data
    private TransactionNode next; //like next

    public TransactionNode(Transaction transaction) {
        this.transaction = transaction; //initializing transaction used to create a new transaction
        this.next = null;
    }

    // Getters and setters
    public Transaction getTransaction() { return transaction; }
    public TransactionNode getNext() { return next; }
    public void setNext(TransactionNode next) { this.next = next; }
}
