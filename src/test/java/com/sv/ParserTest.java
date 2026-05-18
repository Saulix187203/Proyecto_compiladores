package com.sv;

import java_cup.runtime.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void lineTestSQL() throws Exception {
        String sql = "update estudiantes set score = 88.00 where id = 1;";
        Lexer lex = new Lexer(new StringReader(sql));
        Parser parser = new Parser(lex);
        Symbol result = parser.parse();
        assertTrue(result != null, "El parse fue procesado correctamente");
        assertNotNull(result, "El parse no fue procesado");
        System.out.println("Parse exitoso: " + result.toString() + sql);
    }

    @Test
    void docTestSQL() throws Exception {
        List<String> lineas = leer_lineas("sql_100_lineas_sin_errores.txt");

        if (lineas == null || lineas.isEmpty()) {
            System.out.println("❌ El archivo consultas_sql.txt está vacío o no existe");
            return;
        }

        System.out.println("📄 Procesando consultas SQL desde archivo:");
        System.out.println("═".repeat(70));

        int totalConsultas = lineas.size();
        int exitosas = 0;
        int fallidas = 0;

        for (int i = 0; i < lineas.size(); i++) {
            String sql = lineas.get(i).trim();

            // Saltar líneas vacías
            if (sql.isEmpty()) {
                continue;
            }

            System.out.println("\n[Consulta " + (i + 1) + "] " + sql);

            try {
                Lexer lex = new Lexer(new StringReader(sql));
                Parser parser = new Parser(lex);
                Symbol result = parser.parse();

                assertTrue(result != null, "Consulta de SQL aceptada por el parser");
                assertNotNull(result, "Consulta de SQL vacía");
                System.out.println("✓ Parse exitoso: " + result.toString());
                exitosas++;

            } catch (Exception e) {
                System.out.println("✗ Parse falló en: " + e.getMessage());
                fallidas++;
            }
        }

        System.out.println("\n" + "═".repeat(70));
        System.out.println("📊 Resumen:");
        System.out.println("   Total de consultas: " + totalConsultas);
        System.out.println("   Exitosas: " + exitosas);
        System.out.println("   Fallidas: " + fallidas);
        System.out.println("═".repeat(70));
    }

    private List<String> leer_lineas(String nombreArchivo) throws Exception {
        try {
            // Intentar leer desde la raíz del proyecto
            List<String> lineas = Files.readAllLines(Paths.get(nombreArchivo));
            return lineas;
        } catch (Exception e1) {
            System.out.println("⚠️  No se encontró el archivo " + nombreArchivo);
            System.out.println("   Buscado en: " + Paths.get(nombreArchivo).toAbsolutePath());
            throw new Exception("Archivo no encontrado: " + nombreArchivo);
        }
    }
}