package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DecodificadorCesar {

    private static final String ALFABETO = "abcdefghijklmnñopqrstuvwxyz";

    public void descifrarAutomaticamente(String mensajeCifrado) {
        String mensaje = mensajeCifrado.toLowerCase();

        // Probamos claves 1..ALFABETO.length() inclusive
        for (int clave = 1; clave <= ALFABETO.length(); clave++) {

            String candidato = descifrar(mensaje, clave);
            String primeraPalabra = "";
            String[] partes = candidato.trim().split("\\s+");
            if (partes.length > 0) primeraPalabra = partes[0];

            if (!primeraPalabra.isEmpty() && esPalabraValida(primeraPalabra)) {
                System.out.println("Mensaje Descifrado: " + candidato + " (Clave: " + clave + ")");
                return;
            }
        }

        System.out.println("No se pudo descifrar el mensaje con ninguna clave.");
    }

    private String descifrar(String mensaje, int clave) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char caracter : mensaje.toCharArray()) {
            int indice = ALFABETO.indexOf(caracter);

            if (indice != -1) {
                int nuevoIndice = (indice - clave);
                if (nuevoIndice < 0) {
                    nuevoIndice += ALFABETO.length();
                }
                stringBuilder.append(ALFABETO.charAt(nuevoIndice));
            } else {
                stringBuilder.append(caracter);
            }
        }
        return stringBuilder.toString();
    }

    private boolean esPalabraValida(String palabra) {
        try {
            String texto = URLEncoder.encode(palabra, StandardCharsets.UTF_8);
            String url = "https://api.languagetool.org/v2/check?language=es&text=" + texto;

            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

            String json = cliente.send(request, HttpResponse.BodyHandlers.ofString()).body();

            return !json.contains("misspelling");

        } catch (Exception e) {
            return false;
        }
    }
}