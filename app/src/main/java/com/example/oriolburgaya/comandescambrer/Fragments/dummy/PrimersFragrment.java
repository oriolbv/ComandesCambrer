package com.example.oriolburgaya.comandescambrer.Fragments.dummy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.AfegirProductesComandaActivity;
import com.example.oriolburgaya.comandescambrer.BD.ProductesDataSource;
import com.example.oriolburgaya.comandescambrer.ItemGridProductesAdapter;
import com.example.oriolburgaya.comandescambrer.MainActivity;
import com.example.oriolburgaya.comandescambrer.R;
import com.example.oriolburgaya.comandescambrer.models.Producte;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by oriolbv on 25/12/15.
 */
public class PrimersFragrment extends Fragment {

    GridView gridView;

    ArrayList<Bitmap> bitmapImatges = new ArrayList<Bitmap>();

    ViewGroup container;
    View rootView;
    ArrayList<Producte> productes;
    int idComanda;
    boolean gestio;
    ItemGridProductesAdapter adapter;
    LayoutInflater layoutInflater;
    ViewPager viewPager;



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // GET TOTS ELS PRODUCTES DE TIPUS : "Primer"
        this.layoutInflater = inflater;
        Bundle args = getArguments();
        idComanda = args.getInt("idComanda", 0);
        gestio = args.getBoolean("gestio");

        this.container = container;
        ProductesDataSource productesDataSource = new ProductesDataSource(this.getActivity());
        productes = productesDataSource.getProductesTipus("Primer");
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


        return rootView;
    }
}
