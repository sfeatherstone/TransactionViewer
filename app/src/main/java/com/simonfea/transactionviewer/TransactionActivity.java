package com.simonfea.transactionviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.simonfea.transactionviewer.model.Application;
import com.simonfea.transactionviewer.model.Product;

public class TransactionActivity extends AppCompatActivity {

    private ListView transactionListView;
    private static ArrayAdapter transactionAdaptor;
    private Product product;
    private String productName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        productName = getIntent().getStringExtra(Application.EXTRA_PRODUCT_SKU);

        setTitle("Transactions for " + productName);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        product = Application.getInstance().getProductsList().products.get(productName);
        if (product==null) {
            finish();
        }

        transactionListView = (ListView) findViewById(R.id.transaction_list);

        product.calculateSterling();
        transactionAdaptor = new TransactionAdapter(getApplicationContext(), product.transactions);
        // Attach the adapter to a ListView
        transactionListView.setAdapter(transactionAdaptor);

        TextView total = (TextView)findViewById(R.id.total);
        if (total!=null) {
            total.setText(String.format("£%.2f",product.total));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
