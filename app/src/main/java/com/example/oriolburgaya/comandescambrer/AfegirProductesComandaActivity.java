package com.example.oriolburgaya.comandescambrer;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.models.Producte;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by oriolbv on 25/12/15.
 */
public class AfegirProductesComandaActivity extends ActionBarActivity {

    private static int AFEGIR_PRODUCTE_REQUEST_CODE = 1;
    ActionBar.TabListener tabListener;
    TextView tv_idProducte;
    TextView tv_idComanda;
    TextView tv_NomProducte;
    TextView tv_QuantitatProducte;
    TextView tv_PreuProducte;
    TextView tv_PreuTotalProducte;
    ArrayList<Producte> productesComanda = new ArrayList<Producte>();
    int idComanda;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_productes_comanda);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("idComanda");
            idComanda = value;
        }
        tv_idComanda = (TextView) findViewById(R.id.tv_idComanda);
        tv_idProducte = (TextView) findViewById(R.id.tv_idProducte);
        tv_QuantitatProducte = (TextView) findViewById(R.id.tv_QttProducte);
        tv_PreuTotalProducte = (TextView) findViewById(R.id.tv_PreuTotalProducte);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 3, idComanda, false);
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
        inflater.inflate(R.menu.menu_productes_comanda, menu);
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
            startActivityForResult(intent, AFEGIR_PRODUCTE_REQUEST_CODE);
            return true;
        } else if (id == R.id.action_Ok) {
            GridView gv = (GridView) findViewById(R.id.gridViewProductes);
            Log.i("gridView", ""+gv.getCount());
            Intent backData = new Intent();
            backData.putExtra("idComanda", idComanda);
            setResult(RESULT_OK, backData);
            finish();

            //Intent backData = new Intent();
            //backData.putExtra("data", "Hola em dic Oriol.");

            // Enviem la informació
            //
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AFEGIR_PRODUCTE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Log.i("AfegirProductesComandaActivity", "ProducteAfegit : "+ data.getStringExtra("data"));
                // Refresh del llistat de productes!
                tv_QuantitatProducte = (TextView) findViewById(R.id.tv_QttProducte);
                tv_PreuTotalProducte = (TextView) findViewById(R.id.tv_PreuTotalProducte);

                ActionBar actionBar = this.getSupportActionBar();
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
                actionBar.setDisplayShowTitleEnabled(true);
                final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 3, idComanda, false);
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
    public void onBackPressed() {
        Intent backData = new Intent();
        backData.putExtra("idComanda", idComanda);
        setResult(RESULT_CANCELED, backData);
        finish();
    }

}
