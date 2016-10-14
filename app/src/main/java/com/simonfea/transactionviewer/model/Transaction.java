package com.simonfea.transactionviewer.model;

/**
 * Created by simonfea on 14/10/2016.
 */

public class Transaction {
    public Transaction(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }
    public String currency;
    public double amount;
    public Double amountSterling;
}
