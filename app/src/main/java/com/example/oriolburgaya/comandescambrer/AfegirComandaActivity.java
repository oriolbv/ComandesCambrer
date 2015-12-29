package com.example.oriolburgaya.comandescambrer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.BD.ComandesDataSource;
import com.example.oriolburgaya.comandescambrer.BD.ProductesComandaDataSource;
import com.example.oriolburgaya.comandescambrer.models.ProductesComanda;

import java.util.ArrayList;

/**
 * Created by oriolbv on 24/12/15.
 */
public class AfegirComandaActivity extends ActionBarActivity {

    public final static int AFEGIR_PRODUCTES_COMANDA_REQUEST_CODE = 1;
    int idComanda;
    EditText etData;
    EditText etHora;
    EditText etNTaula;
    TextView tvPreuTotal;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_afegir_comanda);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("idComanda");
            idComanda = value;
        }

        etData = (EditText) findViewById(R.id.et_Data);
        etHora = (EditText) findViewById(R.id.et_Hora);
        etNTaula = (EditText) findViewById(R.id.et_nTaula);
        tvPreuTotal = (TextView) findViewById(R.id.tv_PreuTotalComanda);
        ProductesComandaDataSource productesComandaDataSource = new ProductesComandaDataSource(this);
        ArrayList<ProductesComanda> productesComanda = productesComandaDataSource.getProductesComanda(idComanda);
        double preuTotal = 0;
        for (int i = 0; i < productesComanda.size(); ++i) {
            preuTotal = preuTotal + productesComanda.get(i).getPreuTotal();
        }
        tvPreuTotal.setText(String.valueOf(preuTotal));

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
        int midaData = etData.getText().toString().trim().length();
        int midaHora = etHora.getText().toString().trim().length();
        int midanTaula = etNTaula.getText().toString().trim().length();
        int midaPreu = tvPreuTotal.getText().toString().trim().length();
        if (midaData == 0 || midaHora == 0 || midanTaula == 0 || midaPreu == 0) {
            Toast.makeText(this, "Falten camps!", Toast.LENGTH_SHORT).show();
        } else {
            // Guardem producte a BD
            Toast.makeText(this, "Tot correcte!", Toast.LENGTH_SHORT).show();
            ComandesDataSource comandesDataSource = new ComandesDataSource(this);

            String data = etData.getText().toString();
            String hora = etHora.getText().toString();
            data = data + " " + hora;
            String sPreu = tvPreuTotal.getText().toString();
            String nouPreu = sPreu.replace("€", "");

            Double preu = Double.valueOf(nouPreu);
            int nTaula = Integer.parseInt(etNTaula.getText().toString());

            comandesDataSource.updateRegister(idComanda, data, preu, nTaula);
            setResult(RESULT_OK, backData);
            finish();
        }
    }

    public void afegirProductesComanda(View view) {
        Toast.makeText(AfegirComandaActivity.this,
                "He fet click per afegir Productes a la comanda!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AfegirProductesComandaActivity.class);
        intent.putExtra("idComanda", idComanda);
        startActivityForResult(intent, AFEGIR_PRODUCTES_COMANDA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("CANCELED", "CANCELED");
        if (requestCode == AFEGIR_PRODUCTES_COMANDA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.i("OK", "OK");
                idComanda = data.getIntExtra("idComanda", 0);
                listView = (ListView) findViewById(R.id.listView2);
                ProductesComandaDataSource productesComandaDataSource = new ProductesComandaDataSource(this);
                ArrayList<ProductesComanda> productesComanda = productesComandaDataSource.getProductesComanda(idComanda);
                listView.setAdapter(new ItemProducteComandaAdapter(this, productesComanda));
                double preuTotal = 0;
                for (int i = 0; i < productesComanda.size(); ++i) {
                    preuTotal = preuTotal + productesComanda.get(i).getPreuTotal();
                }
                tvPreuTotal.setText(String.valueOf(preuTotal));

            } else if (resultCode == RESULT_CANCELED) {
                Log.i("CANCELED", "CANCELED");
                idComanda = data.getIntExtra("idComanda", 0);
            }
        }
    }
}
