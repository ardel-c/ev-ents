package com.example.ardeliachristina.events2;

public class EventTransaction {
    String transactionID, userID, eventID, TransactionDate;
    String qty;

    public EventTransaction(String transactionID, String userID, String eventID, String transactionDate, String qty) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.eventID = eventID;
        TransactionDate = transactionDate;
        this.qty = qty;
    }
}
