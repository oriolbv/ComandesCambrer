package com.example.oriolburgaya.comandescambrer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.BD.ProductesDataSource;
import com.example.oriolburgaya.comandescambrer.models.Producte;

import java.io.ByteArrayOutputStream;

/**
 * Created by oriolbv on 27/12/15.
 */
public class AfegirProducteActivity extends ActionBarActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMAGE = 2;

    EditText etNomProducte;
    Spinner spTipusProducte;
    EditText etPreuProducte;
    EditText etStockProducte;
    ImageView mImatgeProducte;
    boolean imatgeSeleccionada;

    Producte producte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_producte);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFDC4436));
        etNomProducte = (EditText) findViewById(R.id.et_NomProducte);
        spTipusProducte = (Spinner) findViewById(R.id.sp_TipusProducte);
        etPreuProducte = (EditText) findViewById(R.id.et_PreuProducte);
        etStockProducte = (EditText) findViewById(R.id.et_StockProducte);
        mImatgeProducte = (ImageView) findViewById(R.id.iv_ImatgeProducte);
        imatgeSeleccionada = false;
        producte = new Producte();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_productes_comanda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return true;
    }

    public void fotoCameraProducte(View view) {
        Intent ferFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (ferFotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(ferFotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void fotoGaleriaProducte(View view) {
        Intent getFotoGaleriaIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (getFotoGaleriaIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(getFotoGaleriaIntent, REQUEST_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap imatgeBitmap = null;
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                imatgeBitmap = (Bitmap) extras.get("data");
                mImatgeProducte.setImageBitmap(imatgeBitmap);
                imatgeSeleccionada = true;
            }
            else if (requestCode == REQUEST_LOAD_IMAGE && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                imatgeBitmap = BitmapFactory.decodeFile(picturePath);
                mImatgeProducte.setImageBitmap(imatgeBitmap);
                imatgeSeleccionada = true;
            }
            if (imatgeBitmap != null) {
                byte[] img = null;
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                imatgeBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                img=bos.toByteArray();
                producte.setImatge(img);
            }

        }

    }

    public void confirmarProducte(View view) {
        int midaNomProducte = etNomProducte.getText().toString().trim().length();
        int midaPreuProducte = etPreuProducte.getText().toString().trim().length();
        int midaStockProducte = etStockProducte.getText().toString().trim().length();
        if (midaNomProducte == 0 || midaPreuProducte == 0 || midaStockProducte == 0 || imatgeSeleccionada == false) {
            Toast.makeText(this, "Falten camps!", Toast.LENGTH_SHORT).show();
        } else {
            // Guardem producte a BD
            Toast.makeText(this, "Tot correcte!", Toast.LENGTH_SHORT).show();
            producte.setNom(etNomProducte.getText().toString());
            producte.setTipus(spTipusProducte.getSelectedItem().toString());
            producte.setPreu(Double.parseDouble(etPreuProducte.getText().toString()));
            producte.setStock(Integer.parseInt(etStockProducte.getText().toString()));
            ProductesDataSource productesDataSource = new ProductesDataSource(this);
            productesDataSource.insertRegister(producte.getNom(),producte.getPreu(), producte.getTipus(), producte.getImatge(), producte.getStock());
            Intent backData = new Intent(this, ProductesActivity.class);
            backData.putExtra("data", producte.getNom());
            // Enviem la informació
            setResult(RESULT_OK, backData);

            startActivity(backData);

        }

    }

    public void cancelarProducte(View view) {

    }

}
