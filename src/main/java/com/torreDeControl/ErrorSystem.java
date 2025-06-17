package com.torreDeControl;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorSystem {
    private final DateTimeFormatter fechaFormatContentLog = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
    private final DateTimeFormatter fechaFormatLogName = DateTimeFormatter.ofPattern("dd-MM-yy");
    private final LocalDateTime fecha = LocalDateTime.now();

    public ErrorSystem() {
    }

    public String errorLog(String error) {
        String cadena = "";
        try (FileWriter fw = new FileWriter("Log - " + fecha.format(fechaFormatLogName) + ".txt", true)) {
            cadena = "Error: " + error + fecha.format(fechaFormatContentLog) + "\n";
            fw.write(cadena);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return cadena;
    }

    public void infoLog(String info) {
        String cadena;
        try (FileWriter fw = new FileWriter("Log - " + fecha.format(fechaFormatLogName) + ".txt", true)) {
            cadena = "INFO: " + info + " Date: " + fecha.format(fechaFormatContentLog) + "\n";
            fw.write(cadena);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}