package com.example.oriolburgaya.comandescambrer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oriolburgaya.comandescambrer.BD.ComandesDataSource;
import com.example.oriolburgaya.comandescambrer.BD.ProductesComandaDataSource;
import com.example.oriolburgaya.comandescambrer.models.Comanda;
import com.example.oriolburgaya.comandescambrer.models.ProductesComanda;

import java.util.List;

/**
 * Created by oriol.burgaya on 12/21/15.
 */
public class ItemListComandesAdapter extends BaseAdapter {

    private Context context;
    private List<Comanda> items;

    public ItemListComandesAdapter(Context context, List<Comanda> items) {
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
        TextView tvData = (TextView) rowView.findViewById(R.id.tv_DataComanda);
        TextView tvImportTotal = (TextView) rowView.findViewById(R.id.tv_ImportTotal);
        TextView tvNumTaula = (TextView) rowView.findViewById(R.id.tv_NumeroTaula);

        Comanda item = this.items.get(position);
        Log.i("tvNumTaula", String.valueOf(item.getnTaula()));
        tvTitle.setText((position+1)+".");
        tvData.setText(item.getData());
        tvImportTotal.setText(String.format("%.2f", item.getPreu()) + " €");
        tvNumTaula.setText(String.valueOf(item.getnTaula()));
        return rowView;
    }

    public void removeItemAt(int i) {
        Log.i("removeAt", "" + items.get(i).getId());
        ComandesDataSource comandesDataSource = new ComandesDataSource(context);
        comandesDataSource.deleteRegister(Integer.valueOf(items.get(i).getId()));
        ProductesComandaDataSource productesComandaDataSource = new ProductesComandaDataSource(context);
        productesComandaDataSource.deleteRegisters(Integer.valueOf(items.get(i).getId()));
        items.remove(i);
    }
}