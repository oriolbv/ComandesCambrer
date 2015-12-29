package com.example.oriolburgaya.comandescambrer.Fragments.dummy;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.oriolburgaya.comandescambrer.BD.ComandesDataSource;
import com.example.oriolburgaya.comandescambrer.BD.ProductesDataSource;
import com.example.oriolburgaya.comandescambrer.ItemGridProductesAdapter;
import com.example.oriolburgaya.comandescambrer.ItemListComandesAdapter;
import com.example.oriolburgaya.comandescambrer.R;
import com.example.oriolburgaya.comandescambrer.models.Comanda;
import com.example.oriolburgaya.comandescambrer.models.Producte;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by oriolbv on 29/12/15.
 */
public class FragmentProva extends Fragment {
    public final static int AFEGIR_COMANDA_REQUEST_CODE = 1;

    private ListView listView;
    private ImageButton imageButton;
    private ViewGroup container;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // GET TOTS ELS PRODUCTES DE TIPUS : "Primer"
        View rootView;
        rootView = inflater.inflate(R.layout.activity_main, container, false);

        this.container = container;
        ProductesDataSource productesDataSource = new ProductesDataSource(this.getActivity());
        ComandesDataSource dataSource = new ComandesDataSource(this.getActivity());

        ArrayList<Producte> productes = productesDataSource.getAllProductes();
        if (productes.size() == 0) {
            byte[] img=null;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.chicken);
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Pollastre", 12.3, "Segon", img, 3);

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.hamburger);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Hamburguesa", 5.2, "Segon", img, 30);

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.crestetes);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Crestetes", 2.3, "Segon", img, 10);

        }

        ArrayList<Comanda> comandes = dataSource.getAllComandes();
        Log.i("ArrayList", ""+comandes.get(0).getPreu());


        listView = (ListView) rootView.findViewById(R.id.listView);

        Log.i("context", ""+listView.toString());
        this.listView.setAdapter(new ItemListComandesAdapter(this.getActivity(), comandes));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemListComandesAdapter adapter = (ItemListComandesAdapter)listView.getAdapter();
                adapter.removeItemAt(i); // you need to implement this method
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return rootView;
    }
}
