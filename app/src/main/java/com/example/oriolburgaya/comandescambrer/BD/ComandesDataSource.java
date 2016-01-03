package com.example.oriolburgaya.comandescambrer.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.oriolburgaya.comandescambrer.models.Comanda;

import java.util.ArrayList;

/**
 * Created by oriolbv on 24/12/15.
 */
public class ComandesDataSource {
    public static final String COMANDES_TABLE_NAME =  "Comandes";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String REAL_TYPE = "real";

    public static class ColumnComandes {
        public static final String ID_COMANDA = BaseColumns._ID;
        public static final String DATA_COMANDA = "data";
        public static final String HORA_COMANDA = "hora";
        public static final String PREU_COMANDA = "preu";
        public static final String NTAULA_COMANDA = "nTaula";
    }

    public static final String CREATE_COMANDES_SCRIPT =
            "create table "+COMANDES_TABLE_NAME+"(" +
                    ColumnComandes.ID_COMANDA+" "+INT_TYPE+" primary key," +
                    ColumnComandes.DATA_COMANDA+" "+STRING_TYPE+" null," +
                    ColumnComandes.HORA_COMANDA+" "+STRING_TYPE+" null," +
                    ColumnComandes.PREU_COMANDA+" "+REAL_TYPE+" null," +
                    ColumnComandes.NTAULA_COMANDA+" "+REAL_TYPE+" null)";

    public static final String INSERT_COMANDES_SCRIPT =
            "insert into "+COMANDES_TABLE_NAME+" values(" +
                    "1," +
                    "\"24/12/2015\"," +
                    "\"22:29\"," +
                    34+"," +
                    2+")";

    private ComandesCambrerDbHelper openHelper;
    private SQLiteDatabase database;

    public ComandesDataSource(Context context) {
        // Creem una instancia cap a la base de dades
        openHelper = new ComandesCambrerDbHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public ArrayList<Comanda> getAllComandes() {
        ArrayList<Comanda> comandes = new ArrayList<Comanda>();

        Cursor mCursor = database.query(
                COMANDES_TABLE_NAME,  //Nom de la taula
                null,  //Lista de Columnas a consultar
                null,  //Columnas para la clausula WHERE
                null,  //Valores a comparar con las columnas del WHERE
                null,  //Agrupar con GROUP BY
                null,  //Condición HAVING para GROUP BY
                null  //Clausula ORDER BY
        );

        if (mCursor.moveToFirst()) {
            do {
                Comanda comanda = new Comanda();
                comanda.setId(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnComandes.ID_COMANDA)));
                comanda.setData(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnComandes.DATA_COMANDA)));
                comanda.setHora(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnComandes.HORA_COMANDA)));
                comanda.setPreu(mCursor.getDouble(mCursor.getColumnIndexOrThrow(ColumnComandes.PREU_COMANDA)));
                comanda.setnTaula(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnComandes.NTAULA_COMANDA)));
                comandes.add(comanda);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        return comandes;
    }

    public Comanda getComanda(int id) {
        Comanda comanda = new Comanda();

        Cursor mCursor = database.query(
                COMANDES_TABLE_NAME,  //Nom de la taula
                null, 
                ColumnComandes.ID_COMANDA + "=?",
                new String[] {""+id},
                null,
                null,
                null
        );
        if (mCursor.moveToFirst()) {
            do {
                comanda.setId(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnComandes.ID_COMANDA)));
                comanda.setData(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnComandes.DATA_COMANDA)));
                comanda.setHora(mCursor.getString(mCursor.getColumnIndexOrThrow(ColumnComandes.HORA_COMANDA)));
                comanda.setPreu(mCursor.getDouble(mCursor.getColumnIndexOrThrow(ColumnComandes.PREU_COMANDA)));
                comanda.setnTaula(mCursor.getInt(mCursor.getColumnIndexOrThrow(ColumnComandes.NTAULA_COMANDA)));
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return comanda;
    }

    public int getNouIdentificador() {
        ArrayList<Comanda> comandes = getAllComandes();
        int idMax = 0;
        for (int i = 0; i < comandes.size(); ++i) {
            if (Integer.parseInt(comandes.get(i).getId()) > idMax) {
                idMax = Integer.parseInt(comandes.get(i).getId());
            }
        }
        return idMax + 1;
    }

    public void insertRegister(int id, String data, String hora, double preu, int nTaula) {
        ContentValues cv = new ContentValues();
        cv.put(ColumnComandes.ID_COMANDA, id);
        cv.put(ColumnComandes.DATA_COMANDA, data);
        cv.put(ColumnComandes.HORA_COMANDA, hora);
        cv.put(ColumnComandes.PREU_COMANDA, preu);
        cv.put(ColumnComandes.NTAULA_COMANDA, nTaula);
        database.insert(COMANDES_TABLE_NAME, null, cv);
    }

    public void updateRegister(int id, String data, String hora, double preu, int nTaula) {
        ContentValues cv = new ContentValues();
        cv.put(ColumnComandes.DATA_COMANDA, data);
        cv.put(ColumnComandes.HORA_COMANDA, hora);
        cv.put(ColumnComandes.PREU_COMANDA, preu);
        cv.put(ColumnComandes.NTAULA_COMANDA, nTaula);
        database.update(COMANDES_TABLE_NAME, cv, "_id="+id, null);
    }

    public void deleteRegister(int id) {
        database.delete(COMANDES_TABLE_NAME, "_id="+id, null);
    }
}
