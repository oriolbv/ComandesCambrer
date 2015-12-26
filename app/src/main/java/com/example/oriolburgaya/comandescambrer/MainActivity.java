package com.example.oriolburgaya.comandescambrer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.BD.ComandesCambrerDbHelper;
import com.example.oriolburgaya.comandescambrer.BD.ComandesDataSource;
import com.example.oriolburgaya.comandescambrer.BD.ProductesDataSource;
import com.example.oriolburgaya.comandescambrer.models.Comanda;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public final static int AFEGIR_COMANDA_REQUEST_CODE = 1;

    private ListView listView;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        //Crear nuevo objeto QuotesDataSource
        ComandesDataSource dataSource = new ComandesDataSource(this);
        ProductesDataSource productesDataSource = new ProductesDataSource(this);

        //SQLiteDatabase db = new SQLiteDatabase();
        ArrayList<Comanda> comandes = dataSource.getAllComandes();
        Log.i("ArrayList", ""+comandes.get(0).getPreu());


        this.listView = (ListView) findViewById(R.id.listView);

        List items = new ArrayList();
        items.add(new ItemListComandes("Pollo"));
        items.add(new ItemListComandes("Pollo"));
        items.add(new ItemListComandes("Polla"));
        items.add(new ItemListComandes("Pollo"));
        items.add(new ItemListComandes("Pollo"));
        items.add(new ItemListComandes("Polla"));
        items.add(new ItemListComandes("Pollo"));
        items.add(new ItemListComandes("Pollo"));
        items.add(new ItemListComandes("Polla"));
        items.add(new ItemListComandes("Pollo"));
        items.add(new ItemListComandes("Pollo"));
        items.add(new ItemListComandes("Polla"));

        this.listView.setAdapter(new ItemListComandesAdapter(this, items));
        //addListenerOnButton();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void afegirComanda(View view) {
        Toast.makeText(MainActivity.this,
                "He fet click madafaka!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AfegirComandaActivity.class);
        startActivityForResult(intent, AFEGIR_COMANDA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AFEGIR_COMANDA_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Log.i("onActivityResult", "Result OK! : "+ data.getStringExtra("data"));
            }
        }
    }
}
