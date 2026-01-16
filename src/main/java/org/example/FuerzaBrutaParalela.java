package org.example;

import java.util.concurrent.atomic.AtomicReference;

public class FuerzaBrutaParalela {

    private static final String HASH_OBJETIVO = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
    public static final AtomicReference<String> contrasenaEncontrada = new AtomicReference<>(null);

    public void iniciarAtaque() {

        Thread[] hilos = {
                new Thread(new TareaCrack('a', 'f'), "Hilo-1"),
                new Thread(new TareaCrack('g', 'm'), "Hilo-2"),
                new Thread(new TareaCrack('n', 's'), "Hilo-3"),
                new Thread(new TareaCrack('t', 'z'), "Hilo-4")
        };

        for (Thread t : hilos) t.start();
        try {
            for (Thread t : hilos) t.join();
        } catch (InterruptedException e) { e.printStackTrace(); }

        String pass = contrasenaEncontrada.get();
        if (pass != null) {
            System.out.println("CRACKEADO: " + pass);
        } else {
            System.out.println("FALLO: No encontrada.");
        }
    }

    private static class TareaCrack implements Runnable {
        private final char inicio;
        private final char fin;
        private static final char[] ALFABETO = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        public TareaCrack(char inicio, char fin) {
            this.inicio = inicio;
            this.fin = fin;
        }

        @Override
        public void run() {
            char[] password = new char[4];
            for (char c1 = inicio; c1 <= fin; c1++) {
                if (contrasenaEncontrada.get() != null) return;

                password[0] = c1;
                for (char c2 : ALFABETO) {
                    password[1] = c2;
                    for (char c3 : ALFABETO) {
                        password[2] = c3;
                        for (char c4 : ALFABETO) {

                            if (contrasenaEncontrada.get() != null) return;

                            password[3] = c4;
                            String candidato = new String(password);

                            if (Hash.getPasswordHash(candidato).equals(HASH_OBJETIVO)) {
                                if (contrasenaEncontrada.compareAndSet(null, candidato)) {
                                    System.out.println("ENCONTRADA por " + Thread.currentThread().getName() + "!");
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}