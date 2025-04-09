package com.ibra.bankingapp.backend.entity;

import java.util.ArrayList;
import java.util.List;

public class TransactionLinkedList {
    private TransactionNode head;
    private int size;

    public TransactionLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void addTransaction(Transaction transaction) {
        TransactionNode newNode = new TransactionNode(transaction);

        if (head == null) {
            head = newNode;
        } else {
            // Add to the beginning for O(1) insertion
            newNode.setNext(head);
            head = newNode;
        }
        size++;
    }

    public List<Transaction> getRecentTransactions(int count) {
        List<Transaction> recentTransactions = new ArrayList<>();
        TransactionNode current = head;
        int counter = 0;

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
