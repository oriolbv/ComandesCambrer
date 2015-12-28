package com.example.oriolburgaya.comandescambrer;

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

    public PagerAdapter(FragmentManager fm, int nombreTabs) {
        super(fm);
        this.nombreTabs = nombreTabs;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                PrimersFragrment tab1 = new PrimersFragrment();
                return tab1;
            case 1:
                SegonsFragment tab2 = new SegonsFragment();
                return tab2;
            case 2:
                TercersFragment tab3 = new TercersFragment();
                return tab3;
            case 3:
                BegudesFragment tab4 = new BegudesFragment();
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
