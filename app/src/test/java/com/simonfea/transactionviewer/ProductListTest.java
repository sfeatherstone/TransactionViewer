package com.simonfea.transactionviewer;

import com.simonfea.transactionviewer.model.Application;
import com.simonfea.transactionviewer.model.ProductList;
import com.simonfea.transactionviewer.model.Rates;
import com.simonfea.transactionviewer.model.Transaction;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by simonfea on 14/10/2016.
 */

public class ProductListTest {
    Application app;

    @Before
    public void setup() {
        app = Application.getInstance();
        Rates rates = app.getRates();
        rates.addRate("GBP", "b", 2.0);
        rates.addRate("b", "c", 3.0);
        rates.addRate("c", "d", 5.0);
        rates.addRate("GBP", "d", 7.0);
        rates.addRate("d", "GBP", 11.0);


    }
    @Test
    public void check_sorting() throws Exception {
        ProductList list = new ProductList();

        list.addTransaction("cba", new Transaction("GBP", 200));
        list.addTransaction("abc", new Transaction("GBP", 200));
        list.addTransaction("bca", new Transaction("GBP", 200));

        list.generateSortedList();
        assertEquals(list.productsSorted.get(0).sku, "abc");
        assertEquals(list.productsSorted.get(1).sku, "bca");
        assertEquals(list.productsSorted.get(2).sku, "cba");

    }

    @Test
    public void check_totals() throws Exception {
        ProductList list = new ProductList();

        list.addTransaction("cba", new Transaction("GBP", 200));
        list.addTransaction("abc", new Transaction("GBP", 201));
        list.addTransaction("abc", new Transaction("GBP", 7));
        list.addTransaction("abc", new Transaction("GBP", 17));
        list.addTransaction("bca", new Transaction("GBP", 202));

        list.products.get("abc").calculateSterling();

        assertEquals(list.products.get("abc").total, 225, 0.001);
    }

    @Test
    public void check_formed_total1() throws Exception {
        ProductList list = new ProductList();

        list.addTransaction("abc", new Transaction("GBP", 201));
        list.addTransaction("abc", new Transaction("c", 7));

        list.products.get("abc").calculateSterling();

        assertEquals(list.products.get("abc").total, 586.0, 0.001);
    }

    @Test
    public void check_formed_total2() throws Exception {
        ProductList list = new ProductList();

        list.addTransaction("abc", new Transaction("GBP", 201));
        list.addTransaction("abc", new Transaction("d", 7));

        list.products.get("abc").calculateSterling();

        assertEquals(list.products.get("abc").total, 278, 0.001);
    }

    @Test
    public void check_un_connected_total1() throws Exception {
        ProductList list = new ProductList();

        list.addTransaction("abc", new Transaction("GBP", 201));
        list.addTransaction("abc", new Transaction("b", 7));

        list.products.get("abc").calculateSterling();

        assertEquals(1356.0, list.products.get("abc").total, 0.001);
    }

}
