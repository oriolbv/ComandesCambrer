package com.example.oriolburgaya.comandescambrer.models;

/**
 * Created by oriolbv on 29/12/15.
 */
public class ProductesComanda {
    private int idProducte;
    private int idComanda;
    private int qttProducte;
    private double preuTotal;

    public int getIdProducte() {
        return idProducte;
    }

    public void setIdProducte(int idProducte) {
        this.idProducte = idProducte;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getQttProducte() {
        return qttProducte;
    }

    public void setQttProducte(int qttProducte) {
        this.qttProducte = qttProducte;
    }

    public double getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(double preuTotal) {
        this.preuTotal = preuTotal;
    }
}
