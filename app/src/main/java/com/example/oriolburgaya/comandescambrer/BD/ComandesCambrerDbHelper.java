package com.example.oriolburgaya.comandescambrer.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.oriolburgaya.comandescambrer.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by oriolbv on 24/12/15.
 */
public class ComandesCambrerDbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "ComandesCambrer";
    public static int DATABASE_VERSION = 5;
    public Context context;
    public SQLiteDatabase db;


    public ComandesCambrerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        // Creem la taula Comandes
        db.execSQL(ComandesDataSource.CREATE_COMANDES_SCRIPT);
        // Insertem registres inicials
        db.execSQL(ComandesDataSource.INSERT_COMANDES_SCRIPT);
        // Creem la taula Comandes
        db.execSQL(ProductesDataSource.CREATE_PRODUCTES_SCRIPT);

        //insertProductesInicials();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newerVersion) {

    }

    public void insertProductesInicials() {
        byte[] img=null;
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.chicken);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
        img=bos.toByteArray();
        ProductesDataSource productesDataSource = new ProductesDataSource(context);
        //productesDataSource.insertRegister("Pollastre", 12.3, "Primer", img, 3);
        db.execSQL("insert into PRODUCTES values("+
                    "\"Pollastre\"," +
                    12.3+"," +
                    "\"Primer\"," +
                    img+"," +
                    3+")");
        //------------------------
        img=null;
        bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.hamburger);
        bos=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
        img=bos.toByteArray();
        //productesDataSource.insertRegister("Hamburguesa", 5.5, "Primer", img, 20);
        //------------------------
        img=null;
        bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.coca);
        bos=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
        img=bos.toByteArray();
        //productesDataSource.insertRegister("Coca", 4.5, "Primer", img, 10);
        //------------------------
        img=null;
        bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.salmon);
        bos=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
        img=bos.toByteArray();
        //productesDataSource.insertRegister("Salmó", 5.5, "Primer", img, 10);
        // -----------------------

    }
}
