package org.example;

import java.util.Scanner;

public class SecuritySuite {

    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        while (true) {
            System.out.println("Bienvenido a SecuritySuite");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Auditoría de Hash");
            System.out.println("2. Decodificador César ");
            System.out.println("3. Fuerza Bruta Paralela (Multihilo)");
            System.out.println("4. Salir");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1": new AuditoriaHash().buscarEnDiccionario();
                case "2":
                    DecodificadorCesar descodificadorCesar = new DecodificadorCesar();
                    System.out.println("Ingrese el mensaje cifrado:");
                    String mensajeCifrado = scanner.nextLine();
                    descodificadorCesar.descifrarAutomaticamente(mensajeCifrado);
                case "3": break;
                case "4": System.out.println("Saliendo..."); return;
                default: System.out.println("Opción no válida. Intente de nuevo.");

            }
        }



    }
}