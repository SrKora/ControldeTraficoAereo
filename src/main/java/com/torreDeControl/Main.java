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
        do {
            do {
                System.out.println("""
                    Selecciona una opción:
                    1. Registrar nueva aeronave
                    2. Organizar aterrizaje
                    3. Organizar despegue
                    4. Listar cola de aterrizaje
                    5. Listar cola de despegue
                    6. Salir
                    """);
                decision = sc.nextInt();
            } while (decision < 1  || decision > 6);
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
                    } while (!id.matches("[A-Z]{1,2}[0-9]{1,4}"));
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
                    if (!t.getColaDeAterrizajes().isEmpty()) {
                        if (t.pista == false) {
                            // La pista se ocupa
                            t.setAeronaveEnPista(t.getColaDeAterrizajes().getFirst());
                            t.setPista(true);

                            t.getColaDeAterrizajes().removeFirst();

                            t.registrarAeronaveParaDespegue(t.getAeronaveEnPista());

                            log.infoLog("Aeronave aterrizando en pista A1 ID: " + t.getAeronaveEnPista().getId());
                            System.out.println("Aeronave aterrizando en pista A1 ID: " + t.getAeronaveEnPista().getId());

                            t.setPista(true);
                        } else {
                            log.errorLog("Pista ocupada, aterrizaje no permitido");
                            System.err.println("Pista ocupada, aterrizaje no permitido");
                        }
                    } else {
                        log.errorLog("Ningúna aeronave registrada");
                        System.err.println("Ningúna aeronave registrada");
                    }
                    break;
                case 3:
                    if (t.pista == true) {
                        // La pista se desocupa


                        log.infoLog("Aeronave despegando ID: " + t.getAeronaveEnPista().getId() + ", pista A1 despejada");
                        System.out.println("Aeronave despegando ID: " + t.getAeronaveEnPista().getId() + ", pista A1 despejada");
                        t.setPista(true);
                        t.setAeronaveEnPista(null);
                    } else {
                        log.errorLog("Pista vacía, no hay aeronaves que puedan despegar");
                        System.err.println("Pista vacía, no hay aeronaves que puedan despegar");
                    }
                    break;
                case 4:
                    t.listarNaves(t.getColaDeAterrizajes());
                    break;
                case 5:
                    t.listarNaves(t.getColaDeDespegues());
                    break;
                default:
                    sw = false;
                    break;
            }
        } while (sw);
    }
}