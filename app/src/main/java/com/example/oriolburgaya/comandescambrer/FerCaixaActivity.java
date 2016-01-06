package com.example.oriolburgaya.comandescambrer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.BD.ComandesDataSource;
import com.example.oriolburgaya.comandescambrer.models.Comanda;

import java.io.InterruptedIOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by oriolbv on 03/01/16.
 */
public class FerCaixaActivity extends BaseActivity {

    private ListView listView;
    EditText etData;
    EditText etDataFinal;
    TextView tvGuanysTotals;
    private DatePickerDialog fromDatePickerDialog;
    ComandesDataSource comandesDataSource;
    Context mContext;


    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_fer_caixa, frameLayout);
        mContext = this;
        etData = (EditText) findViewById(R.id.et_DataFerCaixa);
        etDataFinal = (EditText) findViewById(R.id.et_DataFinalFerCaixa);
        tvGuanysTotals = (TextView) findViewById(R.id.tv_GuanysTotals);
        listView = (ListView) findViewById(R.id.listView_FerCaixa);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        Calendar calendar = Calendar.getInstance();

        etData.setText(dateFormatter.format(calendar.getTime()));
        etDataFinal.setText(dateFormatter.format(calendar.getTime()));
        comandesDataSource = new ComandesDataSource(this);
        ArrayList<Comanda> comandes = comandesDataSource.getAllComandesData(etData.getText().toString());
        listView.setAdapter(new ItemListComandesAdapter(this, comandes));

        double guanys = 0;
        for (int i = 0; i < comandes.size(); ++i) {
            guanys = guanys + comandes.get(i).getPreu();
        }

        tvGuanysTotals.setText(String.format("%.2f", guanys) + " €");

        etData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() == 10) {
                    if (etDataFinal.getText().toString().length() == 10) {
                        ArrayList<Date> dates = getDates(etData.getText().toString(), etDataFinal.getText().toString());
                        ArrayList<Comanda> comandesTotals = new ArrayList<Comanda>();
                        for (int j = 0; j < dates.size(); ++j) {
                            Date data1 = dates.get(j);
                            String data = dateFormatter.format(data1);
                            ArrayList<Comanda> comandes = comandesDataSource.getAllComandesData(data);
                            for (int k = 0; k < comandes.size(); ++k) {
                                comandesTotals.add(comandes.get(k));
                            }
                        }
                        listView.setAdapter(new ItemListComandesAdapter(mContext, comandesTotals));

                        double guanys = 0;
                        for (int j = 0; j < comandesTotals.size(); ++j) {
                            guanys = guanys + comandesTotals.get(j).getPreu();
                        }

                        tvGuanysTotals.setText(String.format("%.2f", guanys) + " €");
                        if (etData.getText().toString().equals(etDataFinal.getText().toString())) {
                            Toast.makeText(mContext, etData.getText().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Del "+etData.getText().toString()+ " fins al " + etDataFinal.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etDataFinal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() == 10) {
                    if (etData.getText().toString().length() == 10) {
                        ArrayList<Date> dates = getDates(etData.getText().toString(), etDataFinal.getText().toString());
                        ArrayList<Comanda> comandesTotals = new ArrayList<Comanda>();
                        for (int j = 0; j < dates.size(); ++j) {
                            Date data1 = dates.get(j);
                            String data = dateFormatter.format(data1);
                            ArrayList<Comanda> comandes = comandesDataSource.getAllComandesData(data);
                            for (int k = 0; k < comandes.size(); ++k) {
                                comandesTotals.add(comandes.get(k));
                            }
                        }
                        listView.setAdapter(new ItemListComandesAdapter(mContext, comandesTotals));

                        double guanys = 0;
                        for (int j = 0; j < comandesTotals.size(); ++j) {
                            guanys = guanys + comandesTotals.get(j).getPreu();
                        }

                        tvGuanysTotals.setText(String.format("%.2f", guanys) + " €");
                        if (etData.getText().toString().equals(etDataFinal.getText().toString())) {
                            Toast.makeText(mContext, etData.getText().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Del "+etData.getText().toString()+ " fins al " + etDataFinal.getText().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ImageView btnDataInicial = (ImageView)findViewById(R.id.btn_DataInicialFerCaixa);
        btnDataInicial.setOnTouchListener(new View.OnTouchListener() {
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

        ImageView btnDataFinal = (ImageView)findViewById(R.id.btn_DataFinalFerCaixa);
        btnDataFinal.setOnTouchListener(new View.OnTouchListener() {
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
                                etDataFinal.setText(dateFormatter.format(newDate.getTime()));
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.help_fer_caixa).setTitle("Ajuda Fer Caixa")
                    .setPositiveButton("D'acord", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private static ArrayList<Date> getDates(String dataInicial, String dataFinal)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date data1 = null;
        Date data2 = null;

        try {
            data1 = dateFormat.parse(dataInicial);
            data2 = dateFormat.parse(dataFinal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(data1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(data2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public void setDataFerCaixa(View view) {
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

    public void setDataFinalFerCaixa(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (etDataFinal.getText().toString().length() == 10) {
            if (etData.getText().toString().length() == 10) {
                ArrayList<Date> dates = getDates(etData.getText().toString(), etDataFinal.getText().toString());
                ArrayList<Comanda> comandesTotals = new ArrayList<Comanda>();
                for (int j = 0; j < dates.size(); ++j) {
                    Date data1 = dates.get(j);
                    String data = dateFormatter.format(data1);
                    ArrayList<Comanda> comandes = comandesDataSource.getAllComandesData(data);
                    for (int k = 0; k < comandes.size(); ++k) {
                        comandesTotals.add(comandes.get(k));
                    }
                }
                listView.setAdapter(new ItemListComandesAdapter(mContext, comandesTotals));

                double guanys = 0;
                for (int j = 0; j < comandesTotals.size(); ++j) {
                    guanys = guanys + comandesTotals.get(j).getPreu();
                }

                tvGuanysTotals.setText(String.format("%.2f", guanys) + " €");
                if (etData.getText().toString().equals(etDataFinal.getText().toString())) {
                    Toast.makeText(mContext, etData.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Del "+etData.getText().toString()+ " fins al " + etDataFinal.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fer_caixa, menu);
        return true;
    }

}
