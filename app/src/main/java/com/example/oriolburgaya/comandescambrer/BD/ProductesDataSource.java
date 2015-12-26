package com.example.oriolburgaya.comandescambrer.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.oriolburgaya.comandescambrer.models.Producte;

import java.util.ArrayList;

/**
 * Created by oriolbv on 26/12/15.
 */
public class ProductesDataSource {
    public static final String PRODUCTES_TABLE_NAME =  "PRODUCTES";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String REAL_TYPE = "real";
    public static final String BLOB_TYPE = "blob";

    public static class ColumnProductes {
        public static final String ID_PRODUCTE = BaseColumns._ID;
        public static final String NOM_PRODUCTE = "nom";
        public static final String PREU_PRODUCTE = "preu";
        public static final String IMATGE_PRODUCTE = "imatge";
        public static final String STOCK_PRODUCTE = "stock";
    }

    public static final String CREATE_PRODUCTES_SCRIPT =
            "create table "+PRODUCTES_TABLE_NAME+"(" +
                    ColumnProductes.ID_PRODUCTE+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnProductes.NOM_PRODUCTE+" "+STRING_TYPE+" not null," +
                    ColumnProductes.PREU_PRODUCTE+" "+REAL_TYPE+" not null," +
                    ColumnProductes.IMATGE_PRODUCTE+" "+BLOB_TYPE+" not null," +
                    ColumnProductes.STOCK_PRODUCTE+" "+INT_TYPE+" not null)";


    private ComandesCambrerDbHelper openHelper;
    private SQLiteDatabase database;

    public ProductesDataSource(Context context) {
        // Creem una instancia cap a la base de dades
        openHelper = new ComandesCambrerDbHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public ArrayList<Producte> getAllComandes() {
    ArrayList<Producte> productes = new ArrayList<Producte>();

        Cursor mCursor = database.query(
                PRODUCTES_TABLE_NAME,  //Nom de la taula
                null,  //Lista de Columnas a consultar
                null,  //Columnas para la clausula WHERE
                null,  //Valores a comparar con las columnas del WHERE
                null,  //Agrupar con GROUP BY
                null,  //Condición HAVING para GROUP BY
                null  //Clausula ORDER BY
        );

        if (mCursor.moveToFirst()) {
            do {
                Producte producte = new Producte();
                producte.setId(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductes.ID_PRODUCTE)));
                producte.setNom(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductes.NOM_PRODUCTE)));
                producte.setPreu(mCursor.getDouble(mCursor.getColumnIndexOrThrow(ColumnProductes.PREU_PRODUCTE)));
                producte.setImatge(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductes.IMATGE_PRODUCTE)));
                producte.setStock(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductes.STOCK_PRODUCTE)));
                productes.add(producte);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return productes;
    }
}
