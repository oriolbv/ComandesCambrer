package com.example.oriolburgaya.comandescambrer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oriolbv on 24/12/15.
 */
public class AfegirComandaActivity extends ActionBarActivity {

    public final static int AFEGIR_PRODUCTES_COMANDA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_afegir_comanda);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Toast t = new Toast()
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmarComanda(View view) {
        Intent backData = new Intent();
        backData.putExtra("data", "Hola em dic Oriol.");

        // Enviem la informació
        setResult(RESULT_OK, backData);
        finish();
    }

    public void afegirProductesComanda(View view) {
        Toast.makeText(AfegirComandaActivity.this,
                "He fet click per afegir Productes a la comanda!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AfegirProductesComandaActivity.class);
        startActivityForResult(intent, AFEGIR_PRODUCTES_COMANDA_REQUEST_CODE);
    }
}
