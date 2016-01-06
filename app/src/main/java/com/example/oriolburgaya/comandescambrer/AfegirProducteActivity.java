package com.example.oriolburgaya.comandescambrer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.BD.ProductesDataSource;
import com.example.oriolburgaya.comandescambrer.models.Producte;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    boolean esAfegir;

    private String pathImatge = "";

    Context mContext;

    Producte producte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_producte);
        mContext = this;
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFDC4436));
        etNomProducte = (EditText) findViewById(R.id.et_NomProducte);
        spTipusProducte = (Spinner) findViewById(R.id.sp_TipusProducte);
        etPreuProducte = (EditText) findViewById(R.id.et_PreuProducte);
        etStockProducte = (EditText) findViewById(R.id.et_StockProducte);
        mImatgeProducte = (ImageView) findViewById(R.id.iv_ImatgeProducte);
        imatgeSeleccionada = false;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int idProducte = extras.getInt("idProducte");
            esAfegir = extras.getBoolean("esAfegir");
            if (!esAfegir) {
                // Modificar producte. Hem d'omplir tots els camps!
                ProductesDataSource productesDataSource = new ProductesDataSource(this);
                producte = productesDataSource.getProducteById(idProducte);
                etNomProducte.setText(producte.getNom());
                String tipus = producte.getTipus();
                if (tipus.equals("Primer")) {
                    spTipusProducte.setSelection(0);
                } else if (tipus.equals("Segon")) {
                    spTipusProducte.setSelection(1);
                } else if (tipus.equals("Postre")) {
                    spTipusProducte.setSelection(2);
                } else {
                    spTipusProducte.setSelection(3);
                }
                etPreuProducte.setText(String.valueOf(producte.getPreu()));
                etStockProducte.setText(""+producte.getStock());
                byte[] imatge = producte.getImatge();
                Bitmap b = BitmapFactory.decodeByteArray(imatge, 0, imatge.length);
                mImatgeProducte.setImageBitmap(b);

            } else {
                producte = new Producte();
            }
        }

        ImageView btnConfirmar = (ImageView)findViewById(R.id.btn_ConfirmarProducte);
        btnConfirmar.setOnTouchListener(new View.OnTouchListener() {
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

                        int midaNomProducte = etNomProducte.getText().toString().trim().length();
                        int midaPreuProducte = etPreuProducte.getText().toString().trim().length();
                        int midaStockProducte = etStockProducte.getText().toString().trim().length();

                        if (esAfegir) {
                            if (midaNomProducte == 0 || midaPreuProducte == 0 || midaStockProducte == 0 || imatgeSeleccionada == false) {
                                Toast.makeText(mContext, "Falten camps!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Guardem producte a BD
                                Toast.makeText(mContext, "Tot correcte!", Toast.LENGTH_SHORT).show();
                                producte.setNom(etNomProducte.getText().toString());
                                producte.setTipus(spTipusProducte.getSelectedItem().toString());
                                producte.setPreu(Double.parseDouble(etPreuProducte.getText().toString()));
                                producte.setStock(Integer.parseInt(etStockProducte.getText().toString()));
                                ProductesDataSource productesDataSource = new ProductesDataSource(mContext);
                                productesDataSource.insertRegister(producte.getNom(),producte.getPreu(), producte.getTipus(), producte.getImatge(), producte.getStock());
                                Intent backData = new Intent(mContext, ProductesActivity.class);
                                backData.putExtra("data", spTipusProducte.getSelectedItemPosition());
                                // Enviem la informació
                                setResult(RESULT_OK, backData);
                                finish();
                                //startActivity(backData);

                            }
                        } else {
                            if (midaNomProducte == 0 || midaPreuProducte == 0 || midaStockProducte == 0 || producte.getImatge() == null) {
                                Toast.makeText(mContext, "Falten camps!", Toast.LENGTH_SHORT).show();
                            } else {
                                producte.setNom(etNomProducte.getText().toString());
                                producte.setTipus(spTipusProducte.getSelectedItem().toString());
                                producte.setPreu(Double.parseDouble(etPreuProducte.getText().toString()));
                                producte.setStock(Integer.parseInt(etStockProducte.getText().toString()));
                                ProductesDataSource productesDataSource = new ProductesDataSource(mContext);
                                productesDataSource.updateRegister(Integer.parseInt(producte.getId()), producte.getNom(),producte.getPreu(), producte.getTipus(), producte.getImatge(), producte.getStock());
                                Intent backData = new Intent(mContext, ProductesActivity.class);
                                backData.putExtra("data", producte.getNom());
                                // Enviem la informació
                                setResult(RESULT_OK, backData);
                                finish();
                                //startActivity(backData);
                            }
                        }
                        // ------------------------------------
                        break;
                    }
                }
                return true;
            }
        });

        ImageView btnCancelar = (ImageView)findViewById(R.id.btn_CancelarProducte);
        btnCancelar.setOnTouchListener(new View.OnTouchListener() {
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
                        Intent backData = new Intent();
                        setResult(RESULT_CANCELED, backData);
                        finish();
                        // ------------------------------------
                        break;
                    }
                }
                return true;
            }
        });

        ImageView btnCamera = (ImageView)findViewById(R.id.btn_CameraProducte);
        btnCamera.setOnTouchListener(new View.OnTouchListener() {
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
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = timeStamp + ".jpg";
                        File storageDir = Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES);
                        pathImatge = storageDir.getAbsolutePath() + "/" + imageFileName;
                        File file = new File(pathImatge);
                        Uri outputFileUri = Uri.fromFile(file);
                        Intent ferFotoIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        ferFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                        startActivityForResult(ferFotoIntent, REQUEST_IMAGE_CAPTURE);
                        // ------------------------------------
                        break;
                    }
                }
                return true;
            }
        });

        ImageView btnGaleria = (ImageView)findViewById(R.id.btn_GaleriaProducte);
        btnGaleria.setOnTouchListener(new View.OnTouchListener() {
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
                        Intent getFotoGaleriaIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if (getFotoGaleriaIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(getFotoGaleriaIntent, REQUEST_LOAD_IMAGE);
                        }
                        // ------------------------------------
                        break;
                    }
                }
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        if (!esAfegir) {
            inflater.inflate(R.menu.menu_modificar_producte, menu);
        }
        //
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_remove) {
            AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
            adb.setTitle("Vols esborrar aquest producte?");
            adb.setIcon(R.drawable.delete_red);
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ProductesDataSource producteDataSource = new ProductesDataSource(mContext);
                    producteDataSource.deleteProducte(producte);
                    Intent backData = new Intent(mContext, ProductesActivity.class);
                    backData.putExtra("data", producte.getNom());
                    // Enviem la informació
                    setResult(RESULT_OK, backData);

                    startActivity(backData);

                }
            });
            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            adb.show();
        } else if (id == android.R.id.home) {
            Intent backData = new Intent();
            setResult(RESULT_CANCELED, backData);
            finish();
        }
        return true;
    }

    public void fotoCameraProducte(View view) {
/*        Intent ferFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (ferFotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(ferFotoIntent, REQUEST_IMAGE_CAPTURE);
        }*/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pathImatge = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pathImatge);
        Uri outputFileUri = Uri.fromFile(file);
        Intent ferFotoIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        ferFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(ferFotoIntent, REQUEST_IMAGE_CAPTURE);
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
                /*Bundle extras = data.getExtras();
                imatgeBitmap = (Bitmap) extras.get("data");
                mImatgeProducte.setImageBitmap(imatgeBitmap);
                imatgeSeleccionada = true;*/
                File imgFile = new  File(pathImatge);
                if(imgFile.exists()){
                    //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


                    Bitmap d = new BitmapDrawable(getResources() , imgFile.getAbsolutePath()).getBitmap();
                    int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
                    mImatgeProducte.setImageBitmap(scaled);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    scaled.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    producte.setImatge(stream.toByteArray());
                    imatgeSeleccionada = true;

                    //mImatgeProducte.setImageBitmap(myBitmap);
                }

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
                if (imatgeBitmap != null) {
                    byte[] img = null;
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    imatgeBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    img=bos.toByteArray();
                    producte.setImatge(img);
                }
            }
        }

    }

    public void confirmarProducte(View view) {
        int midaNomProducte = etNomProducte.getText().toString().trim().length();
        int midaPreuProducte = etPreuProducte.getText().toString().trim().length();
        int midaStockProducte = etStockProducte.getText().toString().trim().length();

        if (esAfegir) {
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
                backData.putExtra("data", spTipusProducte.getSelectedItemPosition());
                // Enviem la informació
                setResult(RESULT_OK, backData);
                finish();
                //startActivity(backData);

            }
        } else {
            if (midaNomProducte == 0 || midaPreuProducte == 0 || midaStockProducte == 0 || producte.getImatge() == null) {
                Toast.makeText(this, "Falten camps!", Toast.LENGTH_SHORT).show();
            } else {
                producte.setNom(etNomProducte.getText().toString());
                producte.setTipus(spTipusProducte.getSelectedItem().toString());
                producte.setPreu(Double.parseDouble(etPreuProducte.getText().toString()));
                producte.setStock(Integer.parseInt(etStockProducte.getText().toString()));
                ProductesDataSource productesDataSource = new ProductesDataSource(this);
                productesDataSource.updateRegister(Integer.parseInt(producte.getId()), producte.getNom(),producte.getPreu(), producte.getTipus(), producte.getImatge(), producte.getStock());
                Intent backData = new Intent(this, ProductesActivity.class);
                backData.putExtra("data", producte.getNom());
                // Enviem la informació
                setResult(RESULT_OK, backData);
                finish();
                //startActivity(backData);
            }
        }


    }

    public void cancelarProducte(View view) {
        Intent backData = new Intent();
        setResult(RESULT_CANCELED, backData);
        finish();
    }

}
