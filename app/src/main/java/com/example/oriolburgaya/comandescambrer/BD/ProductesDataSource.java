package com.example.oriolburgaya.comandescambrer.BD;

import android.content.ContentValues;
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
    public static final String NUMERIC_TYPE = "numeric";

    public static class ColumnProductes {
        public static final String ID_PRODUCTE = BaseColumns._ID;
        public static final String NOM_PRODUCTE = "nom";
        public static final String PREU_PRODUCTE = "preu";
        public static final String TIPUS_PRODUCTE = "tipus";
        public static final String IMATGE_PRODUCTE = "imatge";
        public static final String STOCK_PRODUCTE = "stock";
        public static final String DELETED_PRODUCTE = "deleted";
    }

    public static final String CREATE_PRODUCTES_SCRIPT =
            "create table "+PRODUCTES_TABLE_NAME+"(" +
                    ColumnProductes.ID_PRODUCTE+" "+INT_TYPE+" primary key autoincrement," +
                    ColumnProductes.NOM_PRODUCTE+" "+STRING_TYPE+" not null," +
                    ColumnProductes.PREU_PRODUCTE+" "+REAL_TYPE+" not null," +
                    ColumnProductes.TIPUS_PRODUCTE+" "+STRING_TYPE+" not null," +
                    ColumnProductes.IMATGE_PRODUCTE+" "+BLOB_TYPE+" not null," +
                    ColumnProductes.STOCK_PRODUCTE+" "+INT_TYPE+" not null," +
                    ColumnProductes.DELETED_PRODUCTE+" "+NUMERIC_TYPE+" not null)";




    private ComandesCambrerDbHelper openHelper;
    private SQLiteDatabase database;

    public ProductesDataSource(Context context) {
        // Creem una instancia cap a la base de dades
        openHelper = new ComandesCambrerDbHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public ArrayList<Producte> getAllProductes() {
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
                producte.setTipus(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductes.TIPUS_PRODUCTE)));
                producte.setImatge(mCursor.getBlob(mCursor.getColumnIndexOrThrow(ColumnProductes.IMATGE_PRODUCTE)));
                producte.setStock(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductes.STOCK_PRODUCTE)));
                productes.add(producte);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return productes;
    }

    public ArrayList<Producte> getAllProductesNoEliminats() {
        ArrayList<Producte> productes = new ArrayList<Producte>();

        Cursor mCursor = database.query(
                PRODUCTES_TABLE_NAME,  //Nom de la taula
                null,  //Lista de Columnas a consultar
                ColumnProductes.DELETED_PRODUCTE + "=?",
                new String[] {""+0},
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
                producte.setTipus(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductes.TIPUS_PRODUCTE)));
                producte.setImatge(mCursor.getBlob(mCursor.getColumnIndexOrThrow(ColumnProductes.IMATGE_PRODUCTE)));
                producte.setStock(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductes.STOCK_PRODUCTE)));
                int bDeleted = mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductes.DELETED_PRODUCTE));
                if (bDeleted == 0) {
                    producte.setbDeleted(false);
                } else {
                    producte.setbDeleted(true);
                }
                productes.add(producte);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return productes;
    }




    public Producte getProducteById(int id) {
        Producte producte = null;
        String sIdProducte = Integer.toString(id);
        Cursor mCursor = database.query(
                PRODUCTES_TABLE_NAME,  //Nom de la taula
                null,
                ColumnProductes.ID_PRODUCTE + "=?",
                new String[] {sIdProducte},
                null,
                null,
                null
        );

        if (mCursor.moveToFirst()) {
            do {
                producte = new Producte();
                producte.setId(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductes.ID_PRODUCTE)));
                producte.setNom(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductes.NOM_PRODUCTE)));
                producte.setPreu(mCursor.getDouble(mCursor.getColumnIndexOrThrow(ColumnProductes.PREU_PRODUCTE)));
                producte.setTipus(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductes.TIPUS_PRODUCTE)));
                producte.setImatge(mCursor.getBlob(mCursor.getColumnIndexOrThrow(ColumnProductes.IMATGE_PRODUCTE)));
                producte.setStock(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductes.STOCK_PRODUCTE)));
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return producte;
    }

    public ArrayList<Producte> getProductesTipus(String tipus) {
        ArrayList<Producte> productes = new ArrayList<Producte>();

        Cursor mCursor = database.query(
                PRODUCTES_TABLE_NAME,  //Nom de la taula
                null,
                ColumnProductes.TIPUS_PRODUCTE + "=? and "+ ColumnProductes.DELETED_PRODUCTE + "=?" ,
                new String[] {tipus, ""+0},
                null,
                null,
                null
        );

        if (mCursor.moveToFirst()) {
            do {
                Producte producte = new Producte();
                producte.setId(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductes.ID_PRODUCTE)));
                producte.setNom(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductes.NOM_PRODUCTE)));
                producte.setPreu(mCursor.getDouble(mCursor.getColumnIndexOrThrow(ColumnProductes.PREU_PRODUCTE)));
                producte.setTipus(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductes.TIPUS_PRODUCTE)));
                producte.setImatge(mCursor.getBlob(mCursor.getColumnIndexOrThrow(ColumnProductes.IMATGE_PRODUCTE)));
                producte.setStock(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductes.STOCK_PRODUCTE)));
                int bDeleted = mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductes.DELETED_PRODUCTE));
                if (bDeleted == 0) {
                    producte.setbDeleted(false);
                } else {
                    producte.setbDeleted(true);
                }
                productes.add(producte);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return productes;
    }

    public void insertRegister(String nom, double preu, String tipus, byte[] img, int stock) {
        ContentValues cv = new ContentValues();
        cv.put(ColumnProductes.NOM_PRODUCTE, nom);
        cv.put(ColumnProductes.PREU_PRODUCTE, preu);
        cv.put(ColumnProductes.TIPUS_PRODUCTE, tipus);
        cv.put(ColumnProductes.IMATGE_PRODUCTE, img);
        cv.put(ColumnProductes.STOCK_PRODUCTE, stock);
        cv.put(ColumnProductes.DELETED_PRODUCTE, false);
        database.insert(PRODUCTES_TABLE_NAME, null, cv);
    }

    public void updateRegister(int id, String nom , double preu, String tipus, byte[] img, int stock) {
        ContentValues cv = new ContentValues();
        cv.put(ColumnProductes.NOM_PRODUCTE, nom);
        cv.put(ColumnProductes.PREU_PRODUCTE, preu);
        cv.put(ColumnProductes.TIPUS_PRODUCTE, tipus);
        cv.put(ColumnProductes.IMATGE_PRODUCTE, img);
        cv.put(ColumnProductes.STOCK_PRODUCTE, stock);
        database.update(PRODUCTES_TABLE_NAME, cv, "_id="+id, null);
    }

    public void deleteProducte(Producte p) {
        ContentValues cv = new ContentValues();
        cv.put(ColumnProductes.NOM_PRODUCTE, p.getNom());
        cv.put(ColumnProductes.PREU_PRODUCTE, p.getPreu());
        cv.put(ColumnProductes.TIPUS_PRODUCTE, p.getTipus());
        cv.put(ColumnProductes.IMATGE_PRODUCTE, p.getImatge());
        cv.put(ColumnProductes.STOCK_PRODUCTE, p.getStock());
        cv.put(ColumnProductes.DELETED_PRODUCTE, true);
        database.update(PRODUCTES_TABLE_NAME, cv, "_id="+p.getId(), null);
    }
}
