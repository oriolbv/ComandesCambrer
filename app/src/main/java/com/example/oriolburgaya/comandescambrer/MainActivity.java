package com.example.oriolburgaya.comandescambrer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.BD.ComandesCambrerDbHelper;
import com.example.oriolburgaya.comandescambrer.BD.ComandesDataSource;
import com.example.oriolburgaya.comandescambrer.BD.ProductesDataSource;
import com.example.oriolburgaya.comandescambrer.models.Comanda;
import com.example.oriolburgaya.comandescambrer.models.Producte;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    public final static int AFEGIR_COMANDA_REQUEST_CODE = 1;

    private ListView listView;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        //Crear nuevo objeto QuotesDataSource
        ProductesDataSource productesDataSource = new ProductesDataSource(this);
        ComandesDataSource dataSource = new ComandesDataSource(this);

        ArrayList<Producte> productes = productesDataSource.getAllProductes();
        if (productes.size() == 0) {
            byte[] img=null;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.chicken);
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Pollastre", 12.3, "Segon", img, 3);

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.hamburger);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Hamburguesa", 5.2, "Segon", img, 30);

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.crestetes);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Crestetes", 2.3, "Segon", img, 10);

        }


        //SQLiteDatabase db = new SQLiteDatabase();
        ArrayList<Comanda> comandes = dataSource.getAllComandes();
        Log.i("ArrayList", ""+comandes.get(0).getPreu());


        this.listView = (ListView) findViewById(R.id.listView);


        this.listView.setAdapter(new ItemListComandesAdapter(this, comandes));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemListComandesAdapter adapter = (ItemListComandesAdapter)listView.getAdapter();
                adapter.removeItemAt(i); // you need to implement this method
                adapter.notifyDataSetChanged();
                return true;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void afegirComanda(View view) {
        ComandesDataSource dataSource = new ComandesDataSource(this);
        int nouId = dataSource.getNouIdentificador();
        Toast.makeText(MainActivity.this,
                "Nou identificador : "+ nouId, Toast.LENGTH_SHORT).show();
        dataSource.insertRegister(nouId, null, 0.0, 0);
        Intent intent = new Intent(this, AfegirComandaActivity.class);
        intent.putExtra("idComanda", nouId);
        startActivityForResult(intent, AFEGIR_COMANDA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AFEGIR_COMANDA_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Log.i("onActivityResult", "Result OK! : "+ data.getStringExtra("data"));
                ComandesDataSource dataSource = new ComandesDataSource(this);
                ArrayList<Comanda> comandes = dataSource.getAllComandes();
                this.listView = (ListView) findViewById(R.id.listView);
                this.listView.setAdapter(new ItemListComandesAdapter(this, comandes));
            }
        }
    }
}
