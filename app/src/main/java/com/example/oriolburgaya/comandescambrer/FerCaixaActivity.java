package com.example.oriolburgaya.comandescambrer;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.oriolburgaya.comandescambrer.BD.ComandesDataSource;
import com.example.oriolburgaya.comandescambrer.models.Comanda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by oriolbv on 03/01/16.
 */
public class FerCaixaActivity extends BaseActivity {

    private ListView listView;
    EditText etData;
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
        tvGuanysTotals = (TextView) findViewById(R.id.tv_GuanysTotals);
        listView = (ListView) findViewById(R.id.listView_FerCaixa);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        Calendar calendar = Calendar.getInstance();

        etData.setText(dateFormatter.format(calendar.getTime()));

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
                    ArrayList<Comanda> comandes = comandesDataSource.getAllComandesData(etData.getText().toString());
                    listView.setAdapter(new ItemListComandesAdapter(mContext, comandes));

                    double guanys = 0;
                    for (int j = 0; j < comandes.size(); ++j) {
                        guanys = guanys + comandes.get(j).getPreu();
                    }

                    tvGuanysTotals.setText(String.format("%.2f", guanys) + " €");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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

    @Override
    public void onResume() {
        super.onResume();
        ComandesDataSource comandesDataSource = new ComandesDataSource(this);
        ArrayList<Comanda> comandes = comandesDataSource.getAllComandesData(etData.getText().toString());
        listView.setAdapter(new ItemListComandesAdapter(this, comandes));
        double guanys = 0;
        for (int i = 0; i < comandes.size(); ++i) {
            guanys = guanys + comandes.get(i).getPreu();
        }

        tvGuanysTotals.setText(String.format("%.2f", guanys) + " €");

    }

}
