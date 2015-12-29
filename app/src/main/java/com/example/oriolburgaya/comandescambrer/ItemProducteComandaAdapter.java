package com.example.oriolburgaya.comandescambrer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oriolburgaya.comandescambrer.models.Comanda;
import com.example.oriolburgaya.comandescambrer.models.Producte;

import java.util.List;

/**
 * Created by oriolbv on 28/12/15.
 */
public class ItemProducteComandaAdapter extends BaseAdapter {

    private Context context;
    private List<Producte> items;

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
    public View getView(int i, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Creem un nou objecte dins la view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.productes_comanda_list_item, parent, false);
        }

        // Modifiquem la data de la view
        TextView tvQtt = (TextView) rowView.findViewById(R.id.tv_QttProducteComanda);
        TextView tvNom = (TextView) rowView.findViewById(R.id.tv_NomProducteComanda);
        TextView tvPreu = (TextView) rowView.findViewById(R.id.tv_PreuProducteComanda);

/*        Comanda item = this.items.get(position);
        Log.i("tvNumTAula", String.valueOf(item.getnTaula()));
        tvTitle.setText(item.getId());
        tvData.setText(item.getData());
        tvImportTotal.setText(String.valueOf(item.getPreu()));
        tvNumTaula.setText(String.valueOf(item.getnTaula()));
*/
        return rowView;
    }
}
