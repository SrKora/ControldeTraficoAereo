package com.torreDeControl;

import com.torreDeControl.naves.Aeronave;
import com.torreDeControl.naves.subnaves.Carga;
import com.torreDeControl.naves.subnaves.Emergencia;
import com.torreDeControl.naves.subnaves.Militar;
import com.torreDeControl.naves.subnaves.Pasajeros;

import java.util.*;

public class TorreDeControl {
    ArrayList<Aeronave> colaDeAterrizaje = new ArrayList<>();
    ArrayList<Aeronave> colaDeDespegues = new ArrayList<>();
    Boolean pista = false;
    Aeronave aeronaveEnPista;

    ErrorSystem log = new ErrorSystem();

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

    public void procesarSiguienteEvento(TorreDeControl t) {
        if (t.getColaDeAterrizaje().isEmpty()) {
            crearAeronave(t, random(4));
        } else if (t.getColaDeDespegues().isEmpty()) {
            aterrizarAeronave(t);
        }  else {
            if (t.pista) {
                switch (random(2)){
                    case 0:
                        liberarPista(t);
                        break;
                    case 1:
                        despegarAeronave(t);
                        break;
                }
            } else {
                despegarAeronave(t);
            }
        }
    }

    private void despegarAeronave(TorreDeControl t) {
        if (!t.getColaDeDespegues().isEmpty()) {
            t.setAeronaveEnPista(t.getColaDeDespegues().getLast());
            // Si la pista está vacía saca una aeronave y la despega
            System.out.println("Avión " + t.aeronaveEnPista.getId() + " ocupando pista");
            if (!t.pista) {

                t.failError();

                t.getColaDeDespegues().removeLast();

                // Logs
                log.infoLog("Aeronave despegando ID: " + t.getAeronaveEnPista().getId()  + " Prioridad: " + t.getAeronaveEnPista().getPrioridad() + ", pista despejada");
                System.out.println("Aeronave despegando ID: " + t.getAeronaveEnPista().getId()  + " Prioridad: " + t.getAeronaveEnPista().getPrioridad() + ", pista despejada");
                t.setAeronaveEnPista(null);
                // Si no, la pista estará ocupada y despegará el avión que la estaba ocupando
            } else {

                System.out.println("Avión " + t.aeronaveEnPista.getId() + " despegando...");

                t.failError();

                t.getColaDeDespegues().removeLast();

                // logs
                log.infoLog("Aeronave despegando ID: " + t.getAeronaveEnPista().getId()  + " Prioridad: " + t.getAeronaveEnPista().getPrioridad() + ", pista despejada");
                System.out.println("Aeronave despegando ID: " + t.getAeronaveEnPista().getId()  + " Prioridad: " + t.getAeronaveEnPista().getPrioridad() + ", pista despejada");

                t.setAeronaveEnPista(null);

                t.liberarPista();
            }
        } else {
            // logs
            log.errorLog("Ningúna aeronave registrada para despegue");
            System.err.println("Ningúna aeronave registrada para despegue");
        }
    }

    private void liberarPista(TorreDeControl t) {
        if (t.pista) {
            t.liberarPista();

            // Logs
            log.infoLog("Aeronave ocupando pista ID: " + t.getAeronaveEnPista().getId() + " Prioridad: " + t.getAeronaveEnPista().getPrioridad());
            System.out.println("Aeronave ocupando pista ID: " + t.getAeronaveEnPista().getId() + " Prioridad: " + t.getAeronaveEnPista().getPrioridad());

            System.out.println("Desocupando pista...");

            t.failError();

            t.setAeronaveEnPista(null);
        } else {
            // Logs
            log.errorLog("Pista despejada, ninguna aeronave en pista");
            System.err.println("Pista despejada, ninguna aeronave en pista");
        }
    }

    private void aterrizarAeronave(TorreDeControl t) {
        if (!t.getColaDeAterrizaje().isEmpty()) {
            if (!t.pista) {
                // La pista se ocupa
                System.out.println("Aeronave " + t.getColaDeAterrizaje().getLast().getId() + " aterrizando...");
                t.setAeronaveEnPista(t.getColaDeAterrizaje().getLast());
                t.setPista(true);
                t.failError();

                // Elimina el avión acabado de aterrizar de la cola de aterrizajes
                t.getColaDeAterrizaje().removeLast();

                // Introduce el avión en pista a la cola de despegues
                t.registrarAeronaveParaDespegue(t.getAeronaveEnPista());

                // Logs
                log.infoLog("Aeronave aterrizada, ID: " + t.getAeronaveEnPista().getId() + " Prioridad: " + t.getAeronaveEnPista().getPrioridad());
                System.out.println("Aeronave aterrizada, ID: " + t.getAeronaveEnPista().getId() + " Prioridad: " + t.getAeronaveEnPista().getPrioridad());

                t.setPista(true);
            } else {
                // Logs
                log.errorLog("Pista ocupada, aterrizaje no permitido");
                System.err.println("Pista ocupada, aterrizaje no permitido");
            }
        } else {
            // Logs
            log.errorLog("Ningúna aeronave registrada para aterrizaje");
            System.err.println("Ningúna aeronave registrada para aterrizaje");
        }
    }

    public void crearAeronave (TorreDeControl t, int decision) {
        String id;
        do {
            id = randomID();
        } while (!id.matches("[A-Z]{1,2}[0-9]{1,4}") || t.comprobarID(id));
        switch (decision) {
            case 1:
                // Registra nave de Emergencias
                Emergencia e = new Emergencia(id, "Emergencias", 1);
                t.registrarAeronaveParaAterrizaje(e);
                System.out.println("Aeronave de Emergencias registrada ID: " + e.getId());
                log.infoLog("Aeronave de Emergencias registrada ID: " + e.getId());
                break;
            case 2:
                // Registra nave militar
                Militar m = new Militar(id, "Militar", 2);
                t.registrarAeronaveParaAterrizaje(m);
                System.out.println("Aeronave Militar registrada ID: " + m.getId());
                log.infoLog("Aeronave Militar registrada ID: " + m.getId());
                break;
            case 3:
                // Registra nave de pasajeros
                Pasajeros p = new Pasajeros(id, "Pasajeros", 3);
                t.registrarAeronaveParaAterrizaje(p);
                System.out.println("Aeronave de Pasajeros registrada ID: " + p.getId());
                log.infoLog("Aeronave de Pasajeros registrada ID: " + p.getId());
                break;
            case 4:
                // Registra nave de carga
                Carga c = new Carga(id, "Carga", 4);
                t.registrarAeronaveParaAterrizaje(c);
                System.out.println("Aeronave de Carga registrada ID: " + c.getId());
                log.infoLog("Aeronave de Carga registrada ID: " + c.getId());
                break;
        }
    }

    public String randomID() {
        String ID = String.valueOf(letraAleatoria());
        ID += String.valueOf(letraAleatoria());
        ID  += String.valueOf(1000 + random(9000));
        return ID;
    }

    public char letraAleatoria() {
        return (char) ('A' + (int)(Math.random() * 26));
    }

    public void failError(){
        if (random(100) <= 12){
            try {
                int ms = random(10000) + 3000;
                System.out.println("Fallo en el sistema. Tiempos de esperas cambiados, tiempo de espera " + ms/1000 + " seg");
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                log.errorLog(e.getMessage());
            }
        } else {
            try {
                Thread.sleep(3000 + random(1500));
            } catch (InterruptedException e) {
                log.errorLog(e.getMessage());
            }
        }
    }

    public boolean comprobarID(String id) {
        return colaDeAterrizaje.stream().anyMatch(a -> a.getId().equals(id)) ||
                colaDeDespegues.stream().anyMatch(a -> a.getId().equals(id));
    }

    public int random(int n) {
        return (int) (Math.random() * n);
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