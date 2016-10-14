package com.simonfea.transactionviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.simonfea.transactionviewer.model.Product;

import java.util.ArrayList;


/**
 * Created by simonfea on 14/10/2016.
 */

public class ProductAdaptor extends ArrayAdapter<Product> {

    public ProductAdaptor(Context context, ArrayList<Product> products) {
        super(context, R.layout.list_item, products);
    }

    @Override
    public
    @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);

        ViewHolder holder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textPrimary.setText(product.sku);
        holder.textSecondary.setText(String.valueOf(product.transactions.size()) + " transactions");

        // Return the completed view to render on screen
        return convertView;
    }



    // View lookup cache
    private static class ViewHolder {
        public ViewHolder(View view) {
            textPrimary = (TextView) view.findViewById(R.id.main_text);
            textSecondary = (TextView) view.findViewById(R.id.sub_text);
            view.setTag(this);
        }
        TextView textPrimary;
        TextView textSecondary;
    }
}
