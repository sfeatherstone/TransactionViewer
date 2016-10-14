package com.simonfea.transactionviewer.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by simonfea on 14/10/2016.
 */

public class Application {
    private static Application ourInstance = new Application();
    private ProductList products = new ProductList();
    public boolean transactionsRead = false;
    public boolean ratesRead = false;
    private Rates rates = new Rates();

    public static final String EXTRA_PRODUCT_SKU = "Product.Sku";

    public static Application getInstance() {
        return ourInstance;
    }

    private Application() {
    }


    public @NonNull ProductList getProductsList(){
        return products;
    }

    public @NonNull Rates getRates() {
        return rates;
    }

  /*  public boolean loadTransactions() {

    }*/
}
