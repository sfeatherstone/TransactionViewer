package com.simonfea.transactionviewer.model;

import java.util.ArrayList;

/**
 * Created by simonfea on 14/10/2016.
 */

public class Product {

    static final String LOCAL_CURRENCY = "GBP";
    public String sku;
    public double total;
    private boolean totalCalculated = false;
    public ArrayList<Transaction> transactions = new ArrayList<>();

    public void calculateSterling() {
        if (!totalCalculated) {
            Rates rates = Application.getInstance().getRates();
            for (Transaction t: transactions) {
                Double rate = rates.getRate(t.currency, LOCAL_CURRENCY);
                if (rate!=null) {
                    t.amountSterling = t.amount * rate;
                    total += t.amountSterling;
                }
            }
            //Cost O(# of transactions in product)

            totalCalculated = true;
        }
    }

    public Product(String sku) {
        this.sku = sku;
    }
}
