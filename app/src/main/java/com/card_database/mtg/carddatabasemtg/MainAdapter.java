package com.card_database.mtg.carddatabasemtg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Pav on 29.12.2015.
 */
public class MainAdapter extends BaseAdapter {

    TextView name, cost, type, text, power_toughness;
    Card[] data;
    private LayoutInflater lInflater;

    public MainAdapter(final Context context, Card[] data) {
        this.data = data;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null)
            v = lInflater.inflate(R.layout.fragment_item, parent, false);

        name = (TextView) v.findViewById(R.id.fragment_name);
        cost = (TextView) v.findViewById(R.id.fragment_cost);
        type = (TextView) v.findViewById(R.id.fragment_type);
        text = (TextView) v.findViewById(R.id.fragment_text);
        power_toughness = (TextView) v.findViewById(R.id.fragment_power_toughness);

        name.setText(data[position].NAME);
        cost.setText(data[position].MANACOST);

        String type = "";
        if(data[position].SUPERTYPES.length() != 0)
            type += data[position].SUPERTYPES.substring(0,1).toUpperCase() + data[position].SUPERTYPES.substring(1);
        if(type.length() != 0)
            type += " " + data[position].TYPES;
        else
            type += data[position].TYPES.substring(0,1).toUpperCase() + data[position].TYPES.substring(1);
        if(data[position].SUBTYPES.length() != 0)
            type += " - " + data[position].SUBTYPES.substring(0,1).toUpperCase() + data[position].SUBTYPES.substring(1);

        this.type.setText(type);
        text.setText(data[position].TEXT);
        if(!data[position].POWER.equals("-5"))
            power_toughness.setText(data[position].POWER + "/" + data[position].TOUGHNESS);
        return v;
    }
}