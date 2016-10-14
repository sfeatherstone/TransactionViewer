package com.simonfea.transactionviewer;

import android.util.Log;

import com.simonfea.transactionviewer.model.Application;
import com.simonfea.transactionviewer.model.ProductList;
import com.simonfea.transactionviewer.model.Rates;
import com.simonfea.transactionviewer.model.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by simonfea on 14/10/2016.
 */

public class RateReader {
    static void parse(String json) {

        Rates rates = Application.getInstance().getRates();
        try {
            JSONArray transArray = new JSONArray(json);
            for (int i = 0; i < transArray.length(); i++) {
                JSONObject rateJson = transArray.getJSONObject(i);
                String from = rateJson.getString("from");
                String to = rateJson.getString("to");
                double rate = rateJson.getDouble("rate");
                rates.addRate(from, to , rate); //Cost O(# of transactions)
            }
            Application.getInstance().ratesRead = true;
            //Total cost O(# of rates)

        } catch (JSONException je) {
            Log.e("TransactionReader", "Failed to parse JSON", je);
        }

    }
}
