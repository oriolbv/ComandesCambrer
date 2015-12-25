package com.example.oriolburgaya.comandescambrer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.oriolburgaya.comandescambrer.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by oriolbv on 25/12/15.
 */
public class PrimersFragrment extends Fragment {

    ListView listView;
    private ArrayAdapter<String> listAdapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.activity_afegir_productes_comanda_1, container, false);
        Log.i("getView()", "" + container.toString());
        listView = (ListView) rootView.findViewById(R.id.listPrimers);


        // Create and populate a List of planet names.
        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune"};
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll( Arrays.asList(planets) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(rootView.getContext(), R.layout.simplerow, planetList);

        listView.setAdapter(listAdapter);



        return rootView;
    }
}
