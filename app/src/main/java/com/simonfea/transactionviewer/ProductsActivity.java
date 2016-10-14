package com.simonfea.transactionviewer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.simonfea.transactionviewer.model.Application;
import com.simonfea.transactionviewer.model.Product;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    private ListView productListView;
    private static ArrayAdapter productsAdaptor;
    private final static int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        setTitle("Products");

        productListView = (ListView) findViewById(R.id.product_list);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck== PackageManager.PERMISSION_GRANTED){
            readFiles();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }


    }

    private void readFiles() {
        //TODO fix this locking
        if (Application.getInstance().transactionsRead == false) {
            new ReadTransactions().execute();
        } else {
            setupAdapter(Application.getInstance().getProductsList().productsSorted);
        }
        if (Application.getInstance().ratesRead == false) {
            new ReadRates().execute();
        } else {
            setupListOnClick();
        }
    }

    private void setupAdapter(ArrayList<Product> products) {
        productsAdaptor = new ProductAdaptor(getApplicationContext(), products);
        // Attach the adapter to a ListView
        productListView.setAdapter(productsAdaptor);
    }

    private void setupListOnClick() {
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (productsAdaptor!=null) {
                    Product product = (Product) productsAdaptor.getItem(position);
                    Intent i = new Intent(getBaseContext(), TransactionActivity.class);
                    i.putExtra(Application.EXTRA_PRODUCT_SKU, product.sku );
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readFiles();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private class ReadRates extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String fileContents = Util.readFileFromDownloads("rates.json");
            RateReader.parse(fileContents);
            return null;
        }

        @Override
        protected void onPostExecute(Void products) {
            setupListOnClick();
        }
    }

    private class ReadTransactions extends AsyncTask<Void, Void, ArrayList<Product>> {
        @Override
        protected ArrayList<Product> doInBackground(Void... params) {
            String fileContents = Util.readFileFromDownloads("transactions.json");
            TransactionReader.parse(fileContents);
            return Application.getInstance().getProductsList().productsSorted;
        }

        @Override
        protected void onPostExecute(ArrayList<Product> products) {

            //Add in prev messages
            setupAdapter(products);
        }
    }
}
