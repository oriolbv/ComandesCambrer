package com.example.oriolburgaya.comandescambrer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by oriol.burgaya on 12/21/15.
 */
public class ItemListComandesAdapter extends BaseAdapter {

    private Context context;
    private List<ItemListComandes> items;

    public ItemListComandesAdapter(Context context, List<ItemListComandes> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Creem un nou objecte dins la view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.comandes_list_item, parent, false);
        }

        // Modifiquem la data de la view
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);

        ItemListComandes item = this.items.get(position);
        tvTitle.setText(item.getNom());
        return rowView;
    }
}