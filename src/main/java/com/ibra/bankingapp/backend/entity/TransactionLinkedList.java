package com.ibra.bankingapp.backend.entity;

import java.util.ArrayList;
import java.util.List;

public class TransactionLinkedList {
    private TransactionNode head; //like data
    private int size;

    public TransactionLinkedList() {
        this.head = null; //initialize empty node as head
        this.size = 0;
    }

    public void addTransaction(Transaction transaction) {
        TransactionNode newNode = new TransactionNode(transaction);

        if (head == null) {
            head = newNode;
        } else {
            // Add to the beginning for O(1) insertion; latest will always be the first
            newNode.setNext(head);
            head = newNode;
        }
        size++;
    }

    public List<Transaction> getRecentTransactions(int count) {
        List<Transaction> recentTransactions = new ArrayList<>();
        TransactionNode current = head; //make the head current
        int counter = 0;

        //if current/head exist and counter/number of transactions < the count passed by the user
        while (current != null && counter < count) {
            recentTransactions.add(current.getTransaction());
            current = current.getNext();
            counter++;
        }

        return recentTransactions;
    }

    public List<Transaction> getAllTransactions() {
        return getRecentTransactions(size);
    }

    public int getSize() {
        return size;
    }
}
