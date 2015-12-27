package com.example.oriolburgaya.comandescambrer.Fragments.dummy;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.oriolburgaya.comandescambrer.ItemGridProductesAdapter;
import com.example.oriolburgaya.comandescambrer.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by oriolbv on 25/12/15.
 */
public class PrimersFragrment extends Fragment {

    GridView gridView;
    int[] mImatges = {
            R.drawable.coca,
            R.drawable.pastis_chocolata,
            R.drawable.crestetes,
            R.drawable.hamburger,
            R.drawable.salmon,
            R.drawable.chicken
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_afegir_primers_comanda, container, false);
        ItemGridProductesAdapter adapter = new ItemGridProductesAdapter(container.getContext(), mImatges);
        gridView = (GridView) rootView.findViewById(R.id.gridViewProductes);
        gridView.setAdapter(adapter);
        //gridView.setAdapter(new ItemGridProductesAdapter(new ItemGridProductesAdapter(rootView.getParent())));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });




        return rootView;
    }
}
