package com.example.oriolburgaya.comandescambrer;

import android.app.Activity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
}
