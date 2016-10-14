package com.simonfea.transactionviewer.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by simonfea on 14/10/2016.
 */

public class ProductList {
    public Map<String, Product> products = new TreeMap<>();
    public ArrayList<Product> productsSorted = new ArrayList<>();

    public void addTransaction(String sku, Transaction toAdd) {
        Product product = products.get(sku);
        if (product==null) {
            product = new Product(sku);
            products.put(sku, product);
        }
        product.transactions.add(toAdd);
    }

    public void generateSortedList() {
        if (products.size()!=productsSorted.size()){
            productsSorted.clear();
            for (Product p: products.values()) {
                productsSorted.add(p);
            }

        }
    }
}
