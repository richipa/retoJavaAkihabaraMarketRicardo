package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {
    public static String obtenerApiKey() {
        Properties properties = new Properties();
        try (FileInputStream archivo = new FileInputStream("config/configuracion.properties")) {
            properties.load(archivo);
            return properties.getProperty("API_KEY");
        } catch (IOException e) {
            System.out.println("Error al leer la API Key: " + e.getMessage());
            return null;
        }
    }
}
