package com.example.oriolburgaya.comandescambrer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
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
        Log.i("EOO", "HE ENTRAT al onCreate");

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 4, 0, true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_productes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_afegir) {
            Intent intent = new Intent(this, AfegirProducteActivity.class);
            intent.putExtra("idProducte", 0);
            intent.putExtra("esAfegir", true);
            startActivityForResult(intent, AFEGIR_PRODUCTE_REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AFEGIR_PRODUCTE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                // Refresh del llistat de productes!
                Log.i("EOO", "HE ENTRAT al onActivityResult");

                ActionBar actionBar = this.getSupportActionBar();
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
                actionBar.setDisplayShowTitleEnabled(true);
                final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 4, 0, true);
                viewPager.setAdapter(adapter);
                // Create a tab listener that is called when the user changes tabs.
                tabListener = new ActionBar.TabListener() {
                    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                        // show the given tab
                        //setContentView(R.layout.clear);
                        viewPager.setCurrentItem(tab.getPosition());


                    }

                    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                        // hide the given tab
                    }

                    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                        // probably ignore this event
                    }
                };
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 4, 0, true);
        viewPager.setAdapter(adapter);
        // Create a tab listener that is called when the user changes tabs.
        tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                //setContentView(R.layout.clear);
                viewPager.setCurrentItem(tab.getPosition());


            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

    }


}
