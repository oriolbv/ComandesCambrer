package com.example.oriolburgaya.comandescambrer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.oriolburgaya.comandescambrer.Fragments.dummy.BegudesFragment;
import com.example.oriolburgaya.comandescambrer.Fragments.dummy.PrimersFragrment;
import com.example.oriolburgaya.comandescambrer.Fragments.dummy.SegonsFragment;
import com.example.oriolburgaya.comandescambrer.Fragments.dummy.TercersFragment;

/**
 * Created by oriolbv on 25/12/15.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int nombreTabs;
    int idComanda;
    boolean gestio;

    public PagerAdapter(FragmentManager fm, int nombreTabs, int idComanda, boolean gestio) {
        super(fm);
        this.nombreTabs = nombreTabs;
        this.idComanda = idComanda;
        this.gestio = gestio;
    }


    @Override
    public Fragment getItem(int i) {
        Bundle args = new Bundle();
        switch (i) {
            case 0:
                PrimersFragrment tab1 = new PrimersFragrment();
                args.putInt("idComanda", idComanda);
                args.putBoolean("gestio", gestio);
                tab1.setArguments(args);
                return tab1;
            case 1:
                SegonsFragment tab2 = new SegonsFragment();
                args.putInt("idComanda", idComanda);
                args.putBoolean("gestio", gestio);
                tab2.setArguments(args);
                return tab2;
            case 2:
                TercersFragment tab3 = new TercersFragment();
                args.putInt("idComanda", idComanda);
                args.putBoolean("gestio", gestio);
                tab3.setArguments(args);
                return tab3;
            case 3:
                BegudesFragment tab4 = new BegudesFragment();
                args.putInt("idComanda", idComanda);
                args.putBoolean("gestio", gestio);
                tab4.setArguments(args);
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nombreTabs;
    }
}
