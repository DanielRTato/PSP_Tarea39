package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

public class AuditoriaHash {
    private final String hashAdmin = "4a630b8e79a0cd2fbae3f58e751abb28d0f4918f76af188d8996f13fabe08af8";
    private final String rutaDiccionario = "diccionario.txt";


    public void buscarEnDiccionario() {
        try {
            List<String> contrasenas = Files.readAllLines(Paths.get(rutaDiccionario));
            boolean encontrado = false;

            for (String contrasena : contrasenas) {
                String hashContrasena = Hash.getPasswordHash(contrasena);

                if (hashContrasena.equals(hashAdmin)) {
                    System.out.println("¡CONTRASEÑA ENCONTRADA! La clave es: " + contrasena);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontró la contraseña en el diccionario.");
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        }
    }


}
