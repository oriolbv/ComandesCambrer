package com.example.oriolburgaya.comandescambrer.models;

import android.graphics.Bitmap;
import android.support.v7.internal.app.WindowDecorActionBar;

/**
 * Created by oriolbv on 26/12/15.
 */
public class Producte {
    private String id;
    private String nom;
    private double preu;
    private String tipus;
    private byte[] imatge;
    private int stock;
    private boolean bDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public byte[] getImatge() {
        return imatge;
    }

    public void setImatge(byte[] imatge) {
        this.imatge = imatge;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean getbDeleted() {
        return bDeleted;
    }

    public void setbDeleted(boolean bDeleted) {
        this.bDeleted = bDeleted;
    }


}
