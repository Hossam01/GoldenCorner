package com.golden.goldencorner.ui.main.creditCards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter {
    private Context context;
    private List<CharSequence> itemList;

    public CustomAdapter(Context context, int textViewResourceId, List<CharSequence> itemList) {

        super(context, textViewResourceId);
        this.context = context;
        this.itemList = itemList;
    }

    public TextView getView(int position, View convertView, ViewGroup parent) {

        TextView v = (TextView) super.getView(position, convertView, parent);
        v.setText(itemList.get(position));
        return v;
    }

    public TextView getDropDownView(int position, View convertView, ViewGroup parent) {

        TextView v = (TextView) super.getView(position, convertView, parent);

        v.setText(itemList.get(position));
        return v;
    }


}
