package com.example.oriolburgaya.comandescambrer.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by oriolbv on 24/12/15.
 */
public class ComandesCambrerDbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "ComandesCambrer";
    public static int DATABASE_VERSION = 1;


    public ComandesCambrerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creem la taula Comandes
        db.execSQL(ComandesDataSource.CREATE_COMANDES_SCRIPT);
        // Insertem registres inicials
        db.execSQL(ComandesDataSource.INSERT_COMANDES_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newerVersion) {

    }
}
