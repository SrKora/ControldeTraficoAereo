package com.torreDeControl.naves;

public abstract class Aeronave {
    protected final String id;
    protected String tipoAeronave;
    protected int prioridad;

    public Aeronave(String id, String tipoAeronave, int prioridad) {
        this.id = id;
        this.tipoAeronave = tipoAeronave;
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        String cadena = "ID: " + id;
        cadena += ", Tipo: " + tipoAeronave;
        cadena += ", Prioridad: " + prioridad;
        return cadena;
    }

    public void solicitarAterrizaje() {

    }

    public void solicitarDespegue(){

    }

    public String getId() {
        return id;
    }

    public String getTipoAeronave() {
        return tipoAeronave;
    }

    public void setTipoAeronave(String tipoAeronave) {
        this.tipoAeronave = tipoAeronave;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
