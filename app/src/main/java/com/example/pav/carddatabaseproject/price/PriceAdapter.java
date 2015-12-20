package com.example.pav.carddatabaseproject.price;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pav.carddatabaseproject.R;

import java.util.Vector;

public class PriceAdapter extends BaseAdapter {

    TextView name, set, cost;
    private Vector<Data> data;
    private LayoutInflater lInflater;

    public PriceAdapter(final Context context, Vector<Data> data) {
        this.data = data;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    @Override
    public Data getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null)
            v = lInflater.inflate(R.layout.trade_item, parent, false);

        name = (TextView) v.findViewById(R.id.trade_card_name);
        set = (TextView) v.findViewById(R.id.trade_card_set);
        cost = (TextView) v.findViewById(R.id.trade_card_cost);

        name.setText(data.get(position).name);
        set.setText(data.get(position).set);
        cost.setText(String.valueOf(data.get(position).count) + "x$" + String.valueOf(data.get(position).cost));

        return v;
    }
}
