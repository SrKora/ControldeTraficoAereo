package com.torreDeControl;

import com.torreDeControl.naves.Aeronave;
import java.util.*;

public class TorreDeControl {
    ArrayList<Aeronave> colaDeAterrizaje = new ArrayList<>();
    ArrayList<Aeronave> colaDeDespegues = new ArrayList<>();
    Boolean pista = false;
    Aeronave aeronaveEnPista;

    public void registrarAeronaveParaAterrizaje(Aeronave a) {
        colaDeAterrizaje.add(a);

        colaDeAterrizaje.sort((a2, a1) -> {
            int cmp = Integer.compare(a1.getPrioridad(), a2.getPrioridad());
            if (cmp != 0) return cmp;
            return a1.getFechaAeronave().compareTo(a2.getFechaAeronave());
        });
    }

    public void registrarAeronaveParaDespegue(Aeronave a){
        colaDeDespegues.addLast(a);

        colaDeDespegues.sort((a2, a1) -> {
            int cmp = Integer.compare(a1.getPrioridad(), a2.getPrioridad());
            if (cmp != 0) return cmp;
            return a1.getFechaAeronave().compareTo(a2.getFechaAeronave());
        });
    }

    public void procesarSiguienteEvento() {
    }

    public void liberarPista() {
        pista = false;
    }

    public void listarNaves(ArrayList<Aeronave> naves) {
        naves.stream().map(Aeronave::toString).forEach(System.out::println);
    }

    public ArrayList<Aeronave> getColaDeAterrizaje() {
        return colaDeAterrizaje;
    }

    public void setColaDeAterrizaje(ArrayList<Aeronave> colaDeAterrizaje) {
        this.colaDeAterrizaje = colaDeAterrizaje;
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