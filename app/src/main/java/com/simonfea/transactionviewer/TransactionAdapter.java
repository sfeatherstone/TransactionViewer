package com.simonfea.transactionviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.simonfea.transactionviewer.model.Transaction;

import java.util.ArrayList;

/**
 * Created by simonfea on 14/10/2016.
 */

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, R.layout.list_item, transactions);
    }

    @Override
    public
    @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Transaction transaction = getItem(position);

        ViewHolder holder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String amount = transaction.currency + String.format("%.2f", transaction.amount);
        holder.textPrimary.setText(amount);
        if (transaction.amountSterling!=null) {
            holder.textSecondary.setText(String.format("£%.2f", transaction.amountSterling));
        }
        else {
            holder.textSecondary.setText("");
        }

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


