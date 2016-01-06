package com.example.oriolburgaya.comandescambrer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.BD.ComandesCambrerDbHelper;
import com.example.oriolburgaya.comandescambrer.BD.ComandesDataSource;
import com.example.oriolburgaya.comandescambrer.BD.ProductesDataSource;
import com.example.oriolburgaya.comandescambrer.models.Comanda;
import com.example.oriolburgaya.comandescambrer.models.Producte;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class MainActivity extends BaseActivity {

    public final static int AFEGIR_COMANDA_REQUEST_CODE = 1;
    public final static int MODIFICAR_COMANDA_REQUEST_CODE = 1;

    private ListView listView;
    private ImageButton imageButton;

    private boolean bLlistarTotes = true;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        //setContentView(R.layout.activity_main);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        //Crear nuevo objeto QuotesDataSource
        ProductesDataSource productesDataSource = new ProductesDataSource(this);
        ComandesDataSource dataSource = new ComandesDataSource(this);

        ArrayList<Producte> productes = productesDataSource.getAllProductesNoEliminats();
        if (productes.size() == 0) {

            byte[] img=null;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.chicken);
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Pollastre", 12.3, "Segon", img, 7);

            // --------------------------------------------------------
            // PRIMERS

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.hamburger);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Hamburguesa", 5.2, "Primer", img, 30);

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.pulpo);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Pop a la Gallega", 9.2, "Primer", img, 12);

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.amanida);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Amanida espinacs", 4, "Primer", img, 8);

            // ----------------------------------------------------------------
            // SEGONS

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.crestetes);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Crestetes", 2.3, "Segon", img, 10);



            // ----------------------------------------------------------------
            // POSTRES


            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.brownie);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Brownie", 4, "Postre", img, 10);

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.donut);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Donut", 1.75, "Postre", img, 19);

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.tiramisu);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Tiramisu", 3.75, "Postre", img, 8);

            // -----------------------------------------------------------------
            // BEGUDES

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cocacola);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Coca-cola", 1.75, "Beguda", img, 15);

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cocktail);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Cocktail", 4.60, "Beguda", img, 7);

            img=null;
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.aigua);
            bos=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            img=bos.toByteArray();
            productesDataSource.insertRegister("Aigua", 2.30, "Beguda", img, 15);

        }

        //SQLiteDatabase db = new SQLiteDatabase();
        final ArrayList<Comanda> comandes;
        if (bLlistarTotes) {
            comandes = dataSource.getAllComandes();
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            comandes = dataSource.getAllComandesData(dateFormatter.format(calendar.getTime()));
        }
        for (int i = 0; i < comandes.size(); ++i) {
            if (comandes.get(i).getnTaula() == 0) {
                dataSource.deleteRegister(Integer.parseInt(comandes.get(i).getId()));
                comandes.remove(i);
            }
        }
        Collections.reverse(comandes);
        this.listView = (ListView) findViewById(R.id.listView);
        this.listView.setAdapter(new ItemListComandesAdapter(this, comandes));

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                AlertDialog.Builder adb = new AlertDialog.Builder(view.getContext());
                adb.setTitle("Vols esborrar aquesta comanda?");
                adb.setIcon(R.drawable.delete_red);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ItemListComandesAdapter adapter = (ItemListComandesAdapter)listView.getAdapter();
                        adapter.removeItemAt(position); // you need to implement this method
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                adb.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ComandesDataSource dataSource = new ComandesDataSource(view.getContext());
                ItemListComandesAdapter itemListComandesAdapter = (ItemListComandesAdapter) listView.getAdapter();
                Comanda comanda = (Comanda) itemListComandesAdapter.getItem(i);
                Log.i("comanda", comanda.getId());

                Intent intent = new Intent(view.getContext(), AfegirComandaActivity.class);
                intent.putExtra("idComanda", Integer.parseInt(comanda.getId()));
                intent.putExtra("esAfegir", false);
                startActivityForResult(intent, MODIFICAR_COMANDA_REQUEST_CODE);

            }
        });

        ImageView btnAfegir = (ImageView)findViewById(R.id.btn_AfegirComanda);
        btnAfegir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;

                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;

                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        // ---------------------------------
                        ComandesDataSource dataSource = new ComandesDataSource(mContext);
                        int nouId = dataSource.getNouIdentificador();
                        dataSource.insertRegister(nouId, null, null, 0.0, 0);
                        Intent intent = new Intent(mContext, AfegirComandaActivity.class);
                        intent.putExtra("idComanda", nouId);
                        intent.putExtra("esAfegir", true);
                        startActivityForResult(intent, AFEGIR_COMANDA_REQUEST_CODE);
                        // ------------------------------------
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ComandesDataSource dataSource = new ComandesDataSource(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Comandes");
        final ArrayList<Comanda> comandes;
        if (bLlistarTotes) {
            comandes = dataSource.getAllComandes();
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            comandes = dataSource.getAllComandesData(dateFormatter.format(calendar.getTime()));
        }
        for (int i = 0; i < comandes.size(); ++i) {
            if (comandes.get(i).getnTaula() == 0) {
                dataSource.deleteRegister(Integer.parseInt(comandes.get(i).getId()));
                comandes.remove(i);
            }
        }
        Collections.reverse(comandes);
        this.listView = (ListView) findViewById(R.id.listView);
        this.listView.setAdapter(new ItemListComandesAdapter(this, comandes));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_comandes, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AFEGIR_COMANDA_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                ComandesDataSource dataSource = new ComandesDataSource(this);
                final ArrayList<Comanda> comandes;
                if (bLlistarTotes) {
                    comandes = dataSource.getAllComandes();
                } else {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    Calendar calendar = Calendar.getInstance();
                    comandes = dataSource.getAllComandesData(dateFormatter.format(calendar.getTime()));
                }
                Collections.reverse(comandes);
                this.listView = (ListView) findViewById(R.id.listView);
                this.listView.setAdapter(new ItemListComandesAdapter(this, comandes));
            } else {
                ComandesDataSource dataSource = new ComandesDataSource(this);
                final ArrayList<Comanda> comandes;
                if (bLlistarTotes) {
                    comandes = dataSource.getAllComandes();
                } else {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    Calendar calendar = Calendar.getInstance();
                    comandes = dataSource.getAllComandesData(dateFormatter.format(calendar.getTime()));
                }
                for (int i = 0; i < comandes.size(); ++i) {
                    if (comandes.get(i).getnTaula() == 0) {
                        dataSource.deleteRegister(Integer.parseInt(comandes.get(i).getId()));
                    }
                }
                Collections.reverse(comandes);
                this.listView = (ListView) findViewById(R.id.listView);
                this.listView.setAdapter(new ItemListComandesAdapter(this, comandes));

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.help_afegir_comandes).setTitle("Ajuda Comandes")
                    .setPositiveButton("D'acord", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create().show();
            return true;
        } else if (id == R.id.tipus_llistat) {
            int indexTipus = 0;
            final boolean bEstatAnterior;
            if (bLlistarTotes == false) {
                indexTipus = 1;
                bEstatAnterior = false;
            } else {
                indexTipus = 0;
                bEstatAnterior = true;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Llistat de Comandes")
                    .setSingleChoiceItems(R.array.array_tipus_llistat, indexTipus, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == 0) {
                                bLlistarTotes = true;
                            }
                            else {
                                bLlistarTotes = false;
                            }
                        }
                    })
                    .setPositiveButton(R.string.action_Ok, new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int id) {
                            ComandesDataSource dataSource = new ComandesDataSource(mContext);
                            final ArrayList<Comanda> comandes;
                            if (bLlistarTotes) {
                                comandes = dataSource.getAllComandes();
                            } else {
                                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                Calendar calendar = Calendar.getInstance();
                                comandes = dataSource.getAllComandesData(dateFormatter.format(calendar.getTime()));
                            }
                            Collections.reverse(comandes);
                            listView = (ListView) findViewById(R.id.listView);
                            listView.setAdapter(new ItemListComandesAdapter(mContext, comandes));
                        }
                    })
                    .setNegativeButton(R.string.action_cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            bLlistarTotes = bEstatAnterior;
                        }
                    });
            builder.create().show();
        } else if (id == R.id.about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.about).setTitle("Quant a")
                    .setPositiveButton("D'acord", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
