package com.simonfea.transactionviewer;

import android.util.Log;

import com.simonfea.transactionviewer.model.Application;
import com.simonfea.transactionviewer.model.ProductList;
import com.simonfea.transactionviewer.model.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by simonfea on 14/10/2016.
 */

public class TransactionReader {

    static void parse(String json) {

        ProductList products = Application.getInstance().getProductsList();
        try {
            JSONArray transArray = new JSONArray(json);
            for(int i = 0; i < transArray.length(); i++) {
                JSONObject transaction = transArray.getJSONObject(i);
                String sku = transaction.getString("sku");
                Transaction t = new Transaction(transaction.getString("currency"), transaction.getDouble("amount"));
                products.addTransaction(sku, t); //Cost O(# of transactions)
            }
            products.generateSortedList(); //Cost O(# or products)
            Application.getInstance().transactionsRead = true;
            //Total cost O(# of transactions) as worst could be #trans x 2

        } catch (JSONException je) {
            Log.e("TransactionReader", "Failed to parse JSON", je);
        }

    }
}
