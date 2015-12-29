package com.example.oriolburgaya.comandescambrer.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.oriolburgaya.comandescambrer.models.Producte;
import com.example.oriolburgaya.comandescambrer.models.ProductesComanda;

import java.util.ArrayList;

/**
 * Created by oriolbv on 28/12/15.
 */
public class ProductesComandaDataSource {
    public static final String PRODUCTES_COMANDA_TABLE_NAME = "PRODUCTES_COMANDA";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String REAL_TYPE = "real";

    public static class ColumnProductesComanda {
        public static final String ID_PRODUCTESCOMANDA = BaseColumns._ID;
        public static final String ID_PRODUCTE = "id_producte";
        public static final String ID_COMANDA = "id_comanda";
        public static final String QTT_PRODUCTE = "qtt_producte";
        public static final String PREU_TOTAL_PRODUCTESCOMANDA = "preu";
    }

    public static final String CREATE_PRODUCTES_COMANDA_SCRIPT =
            "create table " + PRODUCTES_COMANDA_TABLE_NAME + "(" +
                    ColumnProductesComanda.ID_PRODUCTESCOMANDA + " " + INT_TYPE + " primary key autoincrement," +
                    ColumnProductesComanda.ID_PRODUCTE + " " + INT_TYPE + " not null," +
                    ColumnProductesComanda.ID_COMANDA + " " + INT_TYPE + " not null," +
                    ColumnProductesComanda.QTT_PRODUCTE + " " + INT_TYPE + " not null," +
                    ColumnProductesComanda.PREU_TOTAL_PRODUCTESCOMANDA + " " + REAL_TYPE + " not null)";

    private ComandesCambrerDbHelper openHelper;
    private SQLiteDatabase database;

    public ProductesComandaDataSource(Context context) {
        // Creem una instancia cap a la base de dades
        openHelper = new ComandesCambrerDbHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public int getQttProductesComanda(int idProducte, int idComanda) {
        String sIdProducte = Integer.toString(idProducte);
        String sIdComanda = Integer.toString(idComanda);
        Cursor mCursor = database.query(
                PRODUCTES_COMANDA_TABLE_NAME,  //Nom de la taula
                null,
                ColumnProductesComanda.ID_PRODUCTE + "=?" + " and " + ColumnProductesComanda.ID_COMANDA + "=?",
                new String[] {sIdProducte, sIdComanda},
                null,
                null,
                null
        );
        if (mCursor.getCount() == 0) {
            return 0;
        } else {
            int qtt = 0;
            if (mCursor.moveToFirst()) {
                do {
                    qtt = mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductesComanda.QTT_PRODUCTE));
                } while (mCursor.moveToNext());
            }
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            return qtt;
        }
    }

    public double getPreuTotalProductesComanda(int idProducte, int idComanda) {
        String sIdProducte = Integer.toString(idProducte);
        String sIdComanda = Integer.toString(idComanda);
        Cursor mCursor = database.query(
                PRODUCTES_COMANDA_TABLE_NAME,  //Nom de la taula
                null,
                ColumnProductesComanda.ID_PRODUCTE + "=?" + " and " + ColumnProductesComanda.ID_COMANDA + "=?",
                new String[] {sIdProducte, sIdComanda},
                null,
                null,
                null
        );
        if (mCursor.getCount() == 0) {
            return 0;
        } else {

            double preu = 0;
            if (mCursor.moveToFirst()) {
                do {
                    preu = mCursor.getDouble(mCursor.getColumnIndexOrThrow(ColumnProductesComanda.PREU_TOTAL_PRODUCTESCOMANDA));
                } while (mCursor.moveToNext());
            }
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            return preu;
        }
    }


    public void modificarProductesComanda(int idProducte, int idComanda, int qtt) {

        String sIdProducte = Integer.toString(idProducte);
        String sIdComanda = Integer.toString(idComanda);

        Cursor mCursor = database.query(
                PRODUCTES_COMANDA_TABLE_NAME,  //Nom de la taula
                null,
                ColumnProductesComanda.ID_PRODUCTE + "=?" + " and " + ColumnProductesComanda.ID_COMANDA + "=?",
                new String[] {sIdProducte, sIdComanda},
                null,
                null,
                null
        );

        Producte producte = getProducteById(idProducte);

        Log.i("modificarProductesComanda", "IdProducte : " + sIdProducte + " IdComanda : "+ sIdComanda + " ----- " +mCursor.getCount());
        if (mCursor.getCount() == 0) {
            // Encara no s'ha seleccionat cap element d'aquest producte
            // Creem registre inicial!
            ContentValues cv = new ContentValues();
            cv.put(ColumnProductesComanda.ID_COMANDA, idComanda);
            cv.put(ColumnProductesComanda.ID_PRODUCTE, idProducte);
            cv.put(ColumnProductesComanda.QTT_PRODUCTE, qtt);
            cv.put(ColumnProductesComanda.PREU_TOTAL_PRODUCTESCOMANDA, (producte.getPreu())*qtt);
            database.insert(PRODUCTES_COMANDA_TABLE_NAME, null, cv);

        } else {
            // Ja està creat!
            String id = "";
            if (qtt == 0) {
                // Esborrem registre
                if (mCursor.moveToFirst()) {
                    do {
                        id = mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductesComanda.ID_PRODUCTESCOMANDA));
                    } while (mCursor.moveToNext());
                }
                if (mCursor != null && !mCursor.isClosed()) {
                    mCursor.close();
                }
                if (!id.equals("")) database.delete(PRODUCTES_COMANDA_TABLE_NAME, "_id="+id, null);

            } else {
                // Fem un update de la qtt i el preu
                if (mCursor.moveToFirst()) {
                    do {
                        id = mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnProductesComanda.ID_PRODUCTESCOMANDA));
                    } while (mCursor.moveToNext());
                }
                if (mCursor != null && !mCursor.isClosed()) {
                    mCursor.close();
                }
                double nouPreu = (producte.getPreu())*qtt;
                ContentValues cv = new ContentValues();
                cv.put(ColumnProductesComanda.QTT_PRODUCTE, qtt);
                cv.put(ColumnProductesComanda.PREU_TOTAL_PRODUCTESCOMANDA, nouPreu);
                if (!id.equals("")) database.update(PRODUCTES_COMANDA_TABLE_NAME, cv, "_id="+id, null);

            }
        }
    }

    public Producte getProducteById(int id) {
        Producte producte = null;
        String sIdProducte = Integer.toString(id);
        Cursor mCursor = database.query(
                ProductesDataSource.PRODUCTES_TABLE_NAME,  //Nom de la taula
                null,
                ProductesDataSource.ColumnProductes.ID_PRODUCTE + "=?",
                new String[] {sIdProducte},
                null,
                null,
                null
        );

        if (mCursor.moveToFirst()) {
            do {
                producte = new Producte();
                producte.setId(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductesDataSource.ColumnProductes.ID_PRODUCTE)));
                producte.setNom(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductesDataSource.ColumnProductes.NOM_PRODUCTE)));
                producte.setPreu(mCursor.getDouble(mCursor.getColumnIndexOrThrow(ProductesDataSource.ColumnProductes.PREU_PRODUCTE)));
                producte.setTipus(mCursor.getString(mCursor.getColumnIndexOrThrow(ProductesDataSource.ColumnProductes.TIPUS_PRODUCTE)));
                producte.setImatge(mCursor.getBlob(mCursor.getColumnIndexOrThrow(ProductesDataSource.ColumnProductes.IMATGE_PRODUCTE)));
                producte.setStock(mCursor.getInt(mCursor.getColumnIndexOrThrow(ProductesDataSource.ColumnProductes.STOCK_PRODUCTE)));
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return producte;
    }

    public ArrayList<ProductesComanda> getProductesComanda(int idComanda) {
        ArrayList<ProductesComanda> productesComandes = new ArrayList<ProductesComanda>();
        String sIdComanda = Integer.toString(idComanda);

        Cursor mCursor = database.query(
                PRODUCTES_COMANDA_TABLE_NAME,  //Nom de la taula
                null,
                ColumnProductesComanda.ID_COMANDA + "=?",
                new String[] {sIdComanda},
                null,
                null,
                null
        );
        ProductesComanda producteComanda;
        if (mCursor.moveToFirst()) {
            do {
                producteComanda = new ProductesComanda();
                producteComanda.setIdComanda(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductesComanda.ID_COMANDA)));
                producteComanda.setIdProducte(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductesComanda.ID_PRODUCTE)));
                producteComanda.setPreuTotal(mCursor.getDouble(mCursor.getColumnIndexOrThrow(ColumnProductesComanda.PREU_TOTAL_PRODUCTESCOMANDA)));
                producteComanda.setQttProducte(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnProductesComanda.QTT_PRODUCTE)));
                productesComandes.add(producteComanda);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return productesComandes;
    }

    public void deleteRegisters(int idComanda) {
        database.delete(PRODUCTES_COMANDA_TABLE_NAME, "id_comanda="+idComanda, null);
    }




}
