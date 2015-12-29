package com.example.oriolburgaya.comandescambrer;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

/**
 * Created by oriolbv on 29/12/15.
 */
public class ProductesActivity extends BaseActivity {
    private static int AFEGIR_PRODUCTE_REQUEST_CODE = 1;
    ActionBar.TabListener tabListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_afegir_productes_comanda);
        getLayoutInflater().inflate(R.layout.activity_afegir_productes_comanda, frameLayout);


        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 3, 0, true);
        viewPager.setAdapter(adapter);
        // Create a tab listener that is called when the user changes tabs.
        tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                viewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        String[] texts = {"Primers", "Segons", "Postres", "Begudes"};
        for (int i = 0; i < 4; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(texts[i])
                            .setTabListener(tabListener));
        }
    }
}
