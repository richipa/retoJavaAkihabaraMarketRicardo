package modelo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import com.google.gson.*;

import config.Configuracion;

public class LlmService {
private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
private static final String MODELO = "mistralai/mistral-7b-instruct:free";
private static final Gson gson = new Gson();

//METODO QUE RECIBE EL PROMPT Y DEVUELVE LA RESPUESTA COMO STRING
public static String consultaIA(String prompt) {
	String apiKey = Configuracion.obtenerApiKey();
	if (apiKey == null || apiKey.isEmpty()) {
		return "Error al obtener la api key";
	}
	try {
		//CONSTRUIR EL JSON
		JsonObject cuerpo = new JsonObject();
		cuerpo.addProperty("model", MODELO);
		
		JsonArray mensajes = new JsonArray();
		JsonObject mensajeUsuario = new JsonObject();
		mensajeUsuario.addProperty("role", "user");
		mensajeUsuario.addProperty("content", prompt);
		mensajes.add(mensajeUsuario);
		cuerpo.add("messages", mensajes);
		
		//CONECTARSE A LA API
		URI uri = URI.create(API_URL);
		URL url = uri.toURL();
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("POST");
        conexion.setRequestProperty("Content-Type", "application/json");
        conexion.setRequestProperty("Accept", "application/json");
        conexion.setRequestProperty("Authorization", "Bearer " + Configuracion.obtenerApiKey());
        conexion.setRequestProperty("X-Title", "AkihabaraMarket");
        conexion.setDoOutput(true);
        
        //ENVIAR EL JSON AL SERVIDOR
        try (OutputStreamWriter writer = new OutputStreamWriter(conexion.getOutputStream())) {
            writer.write(gson.toJson(cuerpo));
        }
        
        //RESPUESTA
        int status = conexion.getResponseCode();
        if (status == 200 || status == 201) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            reader.close();

            String respuesta = json
                    .getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();

            return respuesta.trim();
        } else {
            return "Error HTTP: " + status;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return "Error al conectar con la IA.";
	
    	}
	}
}
