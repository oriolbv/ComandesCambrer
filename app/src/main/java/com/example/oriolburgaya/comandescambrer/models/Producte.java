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
    private int imatge;
    private int stock;

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

    public int getImatge() {
        return imatge;
    }

    public void setImatge(int imatge) {
        this.imatge = imatge;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


}
