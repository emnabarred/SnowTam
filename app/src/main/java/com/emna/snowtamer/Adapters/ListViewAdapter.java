package com.emna.snowtamer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emna.snowtamer.Models.Airport;
import com.emna.snowtamer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Airport> airportNamesList = null;
    private ArrayList<Airport> arraylist;

    public ListViewAdapter(Context context, List<Airport> airportNamesList) {
        mContext = context;
        this.airportNamesList = airportNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Airport>();
        this.arraylist.addAll(airportNamesList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return airportNamesList.size();
    }

    @Override
    public Airport getItem(int position) {
        return airportNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(airportNamesList.get(position).getName());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        airportNamesList.clear();
        if (charText.length() == 0) {
            airportNamesList.addAll(arraylist);
        } else {
            for (Airport wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    airportNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
