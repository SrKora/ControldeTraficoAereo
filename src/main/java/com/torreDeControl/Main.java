package com.torreDeControl;

import com.torreDeControl.naves.subnaves.Carga;
import com.torreDeControl.naves.subnaves.Emergencia;
import com.torreDeControl.naves.subnaves.Militar;
import com.torreDeControl.naves.subnaves.Pasajeros;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        TorreDeControl t = new TorreDeControl();

        ErrorSystem log = new ErrorSystem();

        int decision;
        boolean sw = true;
        String id;
        try{
            do {
                do {
                    System.out.println("""
                    Selecciona una opción:
                    1. Registrar nueva aeronave
                    2. Organizar aterrizaje
                    3. Organizar pista
                    4. Organizar despegue
                    5. Procesar siguiente evento
                    6. Listar cola de aterrizaje
                    7. Listar cola de despegue
                    8. Salir
                    """);
                    decision = sc.nextInt();
                } while (decision < 1  || decision > 8);
                switch (decision) {
                    case 1:
                        do {
                            System.out.println("Que tipo de Aeronave quiere registrar?");
                            System.out.println("""
                            1. Aeronave de Emergencias
                            2. Aeronave Militar
                            3. Aeronave para Pasajeros
                            4. Aeronave Carga
                            """);
                            decision = sc.nextInt();
                        } while (decision < 1  || decision > 4);
                        do {
                            System.out.println("Ingresa el ID de la aeronave:   (Ej: IB3721)");
                            id = sc.next();
                        } while (!id.matches("[A-Z]{1,2}[0-9]{1,4}") || t.comprobarID(id));
                        switch (decision) {
                            case 1:
                                // Registra nave de Emergencias
                                Emergencia e = new Emergencia(id, "Emergencias", 1);
                                t.registrarAeronaveParaAterrizaje(e);
                                System.out.println("Aeronave de Emergencias registrada");
                                log.infoLog("Aeronave de Emergencias registrada ID: " + e.getId());
                                break;
                            case 2:
                                // Registra nave militar
                                Militar m = new Militar(id, "Militar", 2);
                                t.registrarAeronaveParaAterrizaje(m);
                                System.out.println("Aeronave Militar registrada");
                                log.infoLog("Aeronave Militar registrada ID: " + m.getId());
                                break;
                            case 3:
                                // Registra nave de pasajeros
                                Pasajeros p = new Pasajeros(id, "Pasajeros", 3);
                                t.registrarAeronaveParaAterrizaje(p);
                                System.out.println("Aeronave de Pasajeros registrada");
                                log.infoLog("Aeronave de Pasajeros registrada ID: " + p.getId());
                                break;
                            case 4:
                                // Registra nave de carga
                                Carga c = new Carga(id, "Carga", 4);
                                t.registrarAeronaveParaAterrizaje(c);
                                System.out.println("Aeronave de Carga registrada");
                                log.infoLog("Aeronave de Carga registrada ID: " + c.getId());
                                break;
                        }
                        break;
                    case 2:
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
                        break;
                    case 3:
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
                        break;
                    case 4:
                        if (!t.getColaDeDespegues().isEmpty()) {
                            // Si la pista está vacía saca una aeronave y la despega
                            if (!t.pista) {
                                System.out.println("Avión " + t.getColaDeDespegues().getLast().getId() + " ocupando pista");
                                t.setAeronaveEnPista(t.getColaDeDespegues().getLast());

                                t.failError();

                                t.getColaDeDespegues().removeLast();

                                // Logs
                                log.infoLog("Aeronave despegando ID: " + t.getAeronaveEnPista().getId()  + " Prioridad: " + t.getAeronaveEnPista().getPrioridad() + ", pista despejada");
                                System.out.println("Aeronave despegando ID: " + t.getAeronaveEnPista().getId()  + " Prioridad: " + t.getAeronaveEnPista().getPrioridad() + ", pista despejada");
                                t.setAeronaveEnPista(null);
                            // Si no, la pista estará ocupada y despegará el avión que la estaba ocupando
                            } else {
                                System.out.println("Avión " + t.aeronaveEnPista.getId() + " ocupando pista");

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
                        break;
                    case 5:
                        t.procesarSiguienteEvento(t);
                        break;
                    case 6:
                        t.listarNaves(t.getColaDeAterrizaje());
                        break;
                    case 7:
                        t.listarNaves(t.getColaDeDespegues());
                        break;
                    default:
                        sw = false;
                        break;
                }
            } while (sw);
        } catch (Exception e) {
            log.errorLog(e.getMessage());
        }

    }
}