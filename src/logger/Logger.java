/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logger;

/**
 *
 * @author mescr
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private Path logFile;

    public Logger() {
        // Crea la carpeta 'logger' si no existe
        Path folderPath = Paths.get("logger");
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectory(folderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Crea el archivo de registro con la fecha actual como nombre
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String filename = LocalDateTime.now().format(formatter) + ".log";
        logFile = Paths.get("logger", filename);
        try {
            Files.createFile(logFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        // Añade el mensaje de error al archivo de registro
        String logMessage = LocalDateTime.now() + " - " + message + "\n";
        try {
            Files.write(logFile, logMessage.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

