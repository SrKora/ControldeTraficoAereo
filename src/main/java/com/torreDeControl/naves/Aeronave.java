package com.torreDeControl.naves;

import java.time.LocalDateTime;

public abstract class Aeronave {
    protected final String id;
    protected String tipoAeronave;
    protected int prioridad;
    protected LocalDateTime fechaAeronave;

    public Aeronave(String id, String tipoAeronave, int prioridad) {
        this.id = id;
        this.tipoAeronave = tipoAeronave;
        this.prioridad = prioridad;
        this.fechaAeronave =  LocalDateTime.now();
    }

    @Override
    public String toString() {
        String cadena = "ID: " + id;
        cadena += ", Tipo: " + tipoAeronave;
        cadena += ", Prioridad: " + prioridad;
        return cadena;
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

    public LocalDateTime getFechaAeronave() {
        return fechaAeronave;
    }

    public void setFechaAeronave(LocalDateTime fechaAeronave) {
        this.fechaAeronave = fechaAeronave;
    }
}