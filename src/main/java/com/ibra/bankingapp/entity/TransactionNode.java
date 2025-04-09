package com.ibra.bankingapp.entity;

public class TransactionNode {
    private Transaction transaction;
    private TransactionNode next;

    public TransactionNode(Transaction transaction) {
        this.transaction = transaction;
        this.next = null;
    }

    // Getters and setters
    public Transaction getTransaction() { return transaction; }
    public TransactionNode getNext() { return next; }
    public void setNext(TransactionNode next) { this.next = next; }
}
