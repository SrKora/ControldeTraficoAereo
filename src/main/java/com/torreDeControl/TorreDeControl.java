package com.torreDeControl;

import com.torreDeControl.naves.Aeronave;

import java.util.ArrayList;

public class TorreDeControl {
    ArrayList<Aeronave> colaDeAterrizajes = new ArrayList<>();
    ArrayList<Aeronave> colaDeDespegues = new ArrayList<>();
    Boolean pista = false;
    Aeronave aeronaveEnPista;

    public void registrarAeronaveParaAterrizaje(Aeronave a) {
        colaDeAterrizajes.add(a);
    }

    public void registrarAeronaveParaDespegue(Aeronave a){
        colaDeDespegues.addFirst(a);
    }

    public void procesarSiguienteEvento() {

    }

    public void liberarPista() {
        pista = false;
    }

    public void listarNaves(ArrayList<Aeronave> naves) {
        naves.stream().map(Aeronave::toString).forEach(System.out::println);
    }

    public ArrayList<Aeronave> getColaDeAterrizajes() {
        return colaDeAterrizajes;
    }

    public void setColaDeAterrizajes(ArrayList<Aeronave> colaDeAterrizajes) {
        this.colaDeAterrizajes = colaDeAterrizajes;
    }

    public ArrayList<Aeronave> getColaDeDespegues() {
        return colaDeDespegues;
    }

    public void setColaDeDespegues(ArrayList<Aeronave> colaDeDespegues) {
        this.colaDeDespegues = colaDeDespegues;
    }

    public Boolean getPista() {
        return pista;
    }

    public void setPista(Boolean pista) {
        this.pista = pista;
    }

    public Aeronave getAeronaveEnPista() {
        return aeronaveEnPista;
    }

    public void setAeronaveEnPista(Aeronave aeronaveEnPista) {
        this.aeronaveEnPista = aeronaveEnPista;
    }
}