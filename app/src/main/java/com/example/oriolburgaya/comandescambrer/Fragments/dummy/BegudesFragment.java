package com.example.oriolburgaya.comandescambrer.Fragments.dummy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.oriolburgaya.comandescambrer.BD.ProductesDataSource;
import com.example.oriolburgaya.comandescambrer.ItemGridProductesAdapter;
import com.example.oriolburgaya.comandescambrer.R;
import com.example.oriolburgaya.comandescambrer.models.Producte;

import java.util.ArrayList;

/**
 * Created by oriolbv on 28/12/15.
 */
public class BegudesFragment extends Fragment {

    GridView gridView;
    ArrayList<Bitmap> bitmapImatges = new ArrayList<Bitmap>();

    View rootView;
    ArrayList<Producte> productes;
    int idComanda;
    boolean gestio;
    ItemGridProductesAdapter adapter;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        idComanda = args.getInt("idComanda", 0);
        gestio = args.getBoolean("gestio");
        Log.i("index", "" + idComanda);
        ProductesDataSource productesDataSource = new ProductesDataSource(this.getActivity());
        productes = productesDataSource.getProductesTipus("Beguda");
        Log.i("nProductes: ", "" + productes.size());
        for (int i = 0; i < productes.size(); ++i) {
            Bitmap b = BitmapFactory.decodeByteArray(productes.get(i).getImatge(), 0, productes.get(i).getImatge().length);
            bitmapImatges.add(b);
        }
        rootView = inflater.inflate(R.layout.fragment_afegir_primers_comanda, container, false);
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        this.viewPager = viewPager;
        adapter = new ItemGridProductesAdapter(this, container.getContext(), bitmapImatges, productes, idComanda, gestio, viewPager);
        gridView = (GridView) rootView.findViewById(R.id.gridViewProductes);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        return rootView;
    }
}
