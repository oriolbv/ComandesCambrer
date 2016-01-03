package com.example.oriolburgaya.comandescambrer.models;

/**
 * Created by oriolbv on 24/12/15.
 */
public class Comanda {
    private String id;
    private String data;
    private String hora;
    private double preu;
    private int nTaula;

    public Comanda() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public int getnTaula() {
        return nTaula;
    }

    public void setnTaula(int nTaula) {
        this.nTaula = nTaula;
    }
}
