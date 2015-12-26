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

import com.example.oriolburgaya.comandescambrer.ImageAdapter;
import com.example.oriolburgaya.comandescambrer.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by oriolbv on 25/12/15.
 */
public class PrimersFragrment extends Fragment {

    GridView gridView;
    private ArrayAdapter<String> listAdapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_afegir_primers_comanda, container, false);
        Log.i("getView()", "" + container.toString());
        gridView = (GridView) rootView.findViewById(R.id.gridViewProductes);
        gridView.setAdapter(new ImageAdapter(rootView.getContext()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });




        return rootView;
    }
}
