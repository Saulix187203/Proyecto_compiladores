package com.sv;

import picocli.CommandLine;

import java.io.StringReader;
import java.util.Scanner;
import java.util.concurrent.Callable;
import static picocli.CommandLine.*;

@Command(name = "sv", mixinStandardHelpOptions = true, version = "1.0",
        description = "Un programa de ejemplo para demostrar picocli")

public class App implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al programa de análisis léxico y sintáctico. Escribe 'salir' para terminar.");

        String input;
        do {
            System.out.print("> ");
            input = scanner.nextLine();

            if (input.equals("salir")) {
                System.out.println("¡Hasta luego!");
                break;
            }

            if (input.trim().isEmpty()) {
                System.out.println("✗ Entrada vacía. Por favor ingresa una expresión válida.");
                continue;
            }

            try {
                Parser parser = new Parser(new Lexer(new StringReader(input)));
                Object result = parser.parse();
                System.out.println("✓ Entrada aceptada: " + result);
            } catch (Exception e) {
                System.out.println("✗ Entrada no aceptada. Error: " + e.getMessage());
            }

        } while (true);

        scanner.close();
        return 0;
    }

    public static void main(String[] args) {
        int excode = new CommandLine(new App()).execute(args);
        System.exit(excode);
    }
}
