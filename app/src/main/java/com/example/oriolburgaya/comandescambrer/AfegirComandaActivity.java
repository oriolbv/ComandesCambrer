package com.example.oriolburgaya.comandescambrer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.BD.ComandesDataSource;
import com.example.oriolburgaya.comandescambrer.BD.ProductesComandaDataSource;
import com.example.oriolburgaya.comandescambrer.models.Comanda;
import com.example.oriolburgaya.comandescambrer.models.ProductesComanda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    private boolean esAfegir;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_afegir_comanda);
        mContext = this;
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFDC4436));
        etData = (EditText) findViewById(R.id.et_Data);
        etHora = (EditText) findViewById(R.id.et_Hora);
        etNTaula = (EditText) findViewById(R.id.et_nTaula);
        tvPreuTotal = (TextView) findViewById(R.id.tv_PreuTotalComanda);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        listView = (ListView) findViewById(R.id.listView2);
        setDateTimeField();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("idComanda");
            idComanda = value;
            esAfegir = extras.getBoolean("esAfegir");
            if (esAfegir == true) {
                ProductesComandaDataSource productesComandaDataSource = new ProductesComandaDataSource(this);
                ArrayList<ProductesComanda> productesComanda = productesComandaDataSource.getProductesComanda(idComanda);
                double preuTotal = 0;
                for (int i = 0; i < productesComanda.size(); ++i) {
                    preuTotal = preuTotal + productesComanda.get(i).getPreuTotal();
                }
                tvPreuTotal.setText(String.format("%.2f", preuTotal) + " €");
                // DATA i HORA per defecte!!!
                Calendar newDate = Calendar.getInstance();
                etData.setText(dateFormatter.format(newDate.getTime()));

                int hour = newDate.get(Calendar.HOUR);
                int minute = newDate.get(Calendar.MINUTE);

                if (Integer.toString(minute).length() == 1) {
                    etHora.setText( hour + ":0" + minute);
                } else {
                    etHora.setText( hour + ":" + minute);
                }

            } else {
                // Modificar comanda
                ComandesDataSource comandesDataSource = new ComandesDataSource(this);
                Comanda comanda = comandesDataSource.getComanda(idComanda);
                etData.setText(comanda.getData());
                etHora.setText(comanda.getHora());
                etNTaula.setText(""+comanda.getnTaula());
                ProductesComandaDataSource productesComandaDataSource = new ProductesComandaDataSource(this);

                ArrayList<ProductesComanda> productesComanda = productesComandaDataSource.getProductesComanda(idComanda);
                Log.i("productesComanda", ""+productesComanda.size());
                listView.setAdapter(new ItemProducteComandaAdapter(this, productesComanda));
                double preuTotal = 0;
                for (int i = 0; i < productesComanda.size(); ++i) {
                    preuTotal = preuTotal + productesComanda.get(i).getPreuTotal();
                }
                tvPreuTotal.setText(String.valueOf(preuTotal));

            }

        }

        ImageView imageView = (ImageView)findViewById(R.id.btn_ConfirmarComanda);
        imageView.setOnTouchListener(new View.OnTouchListener() {
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
                        int midaData = etData.getText().toString().trim().length();
                        int midaHora = etHora.getText().toString().trim().length();
                        int midanTaula = etNTaula.getText().toString().trim().length();
                        int midaPreu = tvPreuTotal.getText().toString().trim().length();
                        if (midaData == 0 || midaHora == 0 || midanTaula == 0 || midaPreu == 0) {
                            Toast.makeText(mContext, "Falten camps!", Toast.LENGTH_SHORT).show();
                        } else {
                            int nTaula = Integer.parseInt(etNTaula.getText().toString());
                            if (nTaula < 1 || nTaula > 20) {
                                Toast.makeText(mContext, "Número de taula incorrecte.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Guardem producte a BD
                                Toast.makeText(mContext, "Tot correcte!", Toast.LENGTH_SHORT).show();
                                ComandesDataSource comandesDataSource = new ComandesDataSource(mContext);

                                String data = etData.getText().toString();
                                String hora = etHora.getText().toString();
                                String sPreu = tvPreuTotal.getText().toString();
                                String nouPreu = sPreu.replace("€", "");

                                Double preu = Double.valueOf(nouPreu);

                                comandesDataSource.updateRegister(idComanda, data, hora, preu, nTaula);
                                setResult(RESULT_OK, backData);
                                finish();
                            }

                        }
                        // ------------------------------------
                        break;
                    }
                }
                return true;
            }
        });

        ImageView imageView2 = (ImageView)findViewById(R.id.btn_CancelarComanda);
        imageView2.setOnTouchListener(new View.OnTouchListener() {
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
                        ComandesDataSource comandesDataSource = new ComandesDataSource(mContext);
                        comandesDataSource.deleteRegister(idComanda);
                        finish();
                        // ------------------------------------
                        break;
                    }
                }
                return true;
            }
        });

        ImageView btnCalendari = (ImageView)findViewById(R.id.btn_Calendari);
        btnCalendari.setOnTouchListener(new View.OnTouchListener() {
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
                        Calendar newCalendar = Calendar.getInstance();
                        fromDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, monthOfYear, dayOfMonth);
                                etData.setText(dateFormatter.format(newDate.getTime()));
                            }

                        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                        fromDatePickerDialog.show();
                        // ------------------------------------
                        break;
                    }
                }
                return true;
            }
        });

        ImageView btnHora = (ImageView)findViewById(R.id.btn_Hora);
        btnHora.setOnTouchListener(new View.OnTouchListener() {
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
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(AfegirComandaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                if (Integer.toString(selectedMinute).length() == 1) {
                                    etHora.setText( selectedHour + ":0" + selectedMinute);
                                } else {
                                    etHora.setText( selectedHour + ":" + selectedMinute);
                                }

                            }
                        }, hour, minute, true);
                        mTimePicker.setTitle("Hora Comanda");
                        mTimePicker.show();
                        // ------------------------------------
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void setDateTimeField() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main, menu);
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
        } else if (id == android.R.id.home) {
            if (esAfegir) {
                Intent backData = new Intent();
                setResult(RESULT_CANCELED, backData);
                ComandesDataSource comandesDataSource = new ComandesDataSource(this);
                comandesDataSource.deleteRegister(idComanda);
                finish();
            } else {
                Intent backData = new Intent();
                setResult(RESULT_CANCELED, backData);
                finish();
            }
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
            int nTaula = Integer.parseInt(etNTaula.getText().toString());
            if (nTaula < 1 || nTaula > 20) {
                Toast.makeText(this, "Número de taula incorrecte.", Toast.LENGTH_SHORT).show();
            } else {
                // Guardem producte a BD
                Toast.makeText(this, "Tot correcte!", Toast.LENGTH_SHORT).show();
                ComandesDataSource comandesDataSource = new ComandesDataSource(this);

                String data = etData.getText().toString();
                String hora = etHora.getText().toString();
                String sPreu = tvPreuTotal.getText().toString();
                String nouPreu = sPreu.replace("€", "");

                Double preu = Double.valueOf(nouPreu);

                comandesDataSource.updateRegister(idComanda, data, hora, preu, nTaula);
                setResult(RESULT_OK, backData);
                finish();
            }

        }
    }

    public void cancelarComanda(View view) {
        Intent backData = new Intent();
        setResult(RESULT_CANCELED, backData);
        ComandesDataSource comandesDataSource = new ComandesDataSource(this);
        comandesDataSource.deleteRegister(idComanda);
        finish();
    }


    public void afegirProductesComanda(View view) {
        Toast.makeText(AfegirComandaActivity.this,
                "He fet click per afegir Productes a la comanda!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AfegirProductesComandaActivity.class);
        intent.putExtra("idComanda", idComanda);
        startActivityForResult(intent, AFEGIR_PRODUCTES_COMANDA_REQUEST_CODE);
    }

    public void setDataComanda(View view) {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etData.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.show();
    }

    public void setHoraComanda(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AfegirComandaActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (Integer.toString(selectedMinute).length() == 1) {
                    etHora.setText( selectedHour + ":0" + selectedMinute);
                } else {
                    etHora.setText( selectedHour + ":" + selectedMinute);
                }

            }
        }, hour, minute, true);
        mTimePicker.setTitle("Hora Comanda");
        mTimePicker.show();

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
                tvPreuTotal.setText(String.format("%.2f", preuTotal) + " €");

            } else if (resultCode == RESULT_CANCELED) {
                Log.i("CANCELED", "CANCELED");
                idComanda = data.getIntExtra("idComanda", 0);
            }
        }
    }

}
