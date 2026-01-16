package org.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class Hash {

    public static String getPasswordHash(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes).toLowerCase();
        } catch (Exception e) {
            throw new RuntimeException("Error calculando Hash", e);
        }
    }
}
