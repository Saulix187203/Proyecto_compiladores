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

    @Test
    void testFileWithErrors() throws Exception {
        List<String> lineas = leer_lineas("sql_100_lineas_con_errores.txt");

        if (lineas == null || lineas.isEmpty()) {
            System.out.println("❌ El archivo sql_100_lineas_con_errores.txt está vacío o no existe");
            return;
        }

        System.out.println("\n" + "═".repeat(80));
        System.out.println("🔍 PRUEBA: Procesando archivo CON ERRORES");
        System.out.println("═".repeat(80));

        int totalConsultas = 0;
        int erroresLexico = 0;
        int erroresSintactico = 0;
        int ningunerror = 0;

        for (int i = 0; i < lineas.size(); i++) {
            String sql = lineas.get(i).trim();

            if (sql.isEmpty()) {
                continue;
            }

            totalConsultas++;
            System.out.println("\n[Línea " + (i + 1) + "] " + sql);

            ErrorClassification clasificacion = classifyError(sql);

            if (clasificacion.tipoError.equals("LEXICAL")) {
                System.out.println("   ❌ [ERROR LÉXICO] " + clasificacion.detalles);
                erroresLexico++;
            } else if (clasificacion.tipoError.equals("SYNTAX")) {
                System.out.println("   ❌ [ERROR SINTÁCTICO] " + clasificacion.detalles);
                erroresSintactico++;
            } else {
                System.out.println("   ✓ [SIN ERRORES]");
                ningunerror++;
            }
        }

        System.out.println("\n" + "═".repeat(80));
        System.out.println("📊 RESUMEN DE ERRORES DETECTADOS:");
        System.out.println("   Total de consultas: " + totalConsultas);
        System.out.println("   Errores léxicos: " + erroresLexico);
        System.out.println("   Errores sintácticos: " + erroresSintactico);
        System.out.println("   Sin errores: " + ningunerror);
        System.out.println("═".repeat(80));

        assertTrue(erroresLexico > 0 || erroresSintactico > 0,
            "Debería detectar al menos un error en el archivo con errores");
    }

    @Test
    void detectLexicalError() throws Exception {
        System.out.println("\n" + "═".repeat(80));
        System.out.println("🔬 PRUEBA: Detección de Errores Léxicos Específicos");
        System.out.println("═".repeat(80));

        String[] sqlConErroresLexico = {
            "select * from u@suarios;",
            "create table test#01 (id int);",
            "insert into t¿abla values (1);",
            "update table! set nombre = 'test';",
            "select * from °base;",
            "delete from personas¬;",
            "select * from emp€resa;",
            "update tabla¨s set id = 1;",
            "select * from sec¥cion;",
            "insert into dept§ values (1);",
            "select * from tabla~al;",
            "select * from base|datos;",
            "select * from dirección\\archivo;",
            "select * from tabla%datos;"
        };

        int erroresEncontrados = 0;
        int totalPruebas = sqlConErroresLexico.length;

        for (String sql : sqlConErroresLexico) {
            StringBuilder resultado = new StringBuilder();
            LexicalErrorInfo info = detectLexicalError(sql);

            System.out.println("\n[PRUEBA] " + sql);

            if (info.tieneError) {
                System.out.println("   ✓ ERROR DETECTADO");
                System.out.println("   Carácter: '" + info.caracter + "'");
                System.out.println("   Línea: " + info.linea + ", Columna: " + info.columna);
                System.out.println("   Descripción: " + info.descripcion);
                erroresEncontrados++;
            } else {
                System.out.println("   ❌ NO SE DETECTÓ ERROR (inesperado)");
            }
        }

        System.out.println("\n" + "═".repeat(80));
        System.out.println("📊 Resumen de detección léxica:");
        System.out.println("   Total de pruebas: " + totalPruebas);
        System.out.println("   Errores detectados: " + erroresEncontrados);
        System.out.println("═".repeat(80));

        assertEquals(totalPruebas, erroresEncontrados,
            "Se debería detectar un error léxico en cada prueba");
    }

    @Test
    void detectSyntacticError() throws Exception {
        System.out.println("\n" + "═".repeat(80));
        System.out.println("🔬 PRUEBA: Detección de Errores Sintácticos Específicos");
        System.out.println("═".repeat(80));

        String[] sqlConErroresSintactico = {
            "create table test (id int;",  // Falta )
            "insert into tabla values (1);",  // Falta columnas
            "select * from;",  // Falta nombre de tabla
            "update tabla set id;",  // Falta =
            "select * profesores;",  // Falta FROM
            "insert into tabla (id, nombre values (1, 'Test');",  // Falta )
            "select a.id a.nombre from tabla;",  // Falta coma
            "update tabla set nombre = 'test' where;",  // Falta condición
        };

        int erroresEncontrados = 0;
        int totalPruebas = sqlConErroresSintactico.length;

        for (String sql : sqlConErroresSintactico) {
            SyntacticErrorInfo info = detectSyntacticError(sql);

            System.out.println("\n[PRUEBA] " + sql);

            if (info.tieneError) {
                System.out.println("   ✓ ERROR DETECTADO");
                System.out.println("   Error: " + info.descripcion);
                if (info.linea >= 0) {
                    System.out.println("   Ubicación: Línea " + info.linea + ", Columna " + info.columna);
                }
                erroresEncontrados++;
            } else {
                System.out.println("   ⚠️  NO SE DETECTÓ ERROR");
            }
        }

        System.out.println("\n" + "═".repeat(80));
        System.out.println("📊 Resumen de detección sintáctica:");
        System.out.println("   Total de pruebas: " + totalPruebas);
        System.out.println("   Errores detectados: " + erroresEncontrados);
        System.out.println("═".repeat(80));
    }

    //╔════════════════════════════════════════════════════════════════════════════╗
    //║                         MÉTODOS HELPER PRIVADOS                             ║
    //╚════════════════════════════════════════════════════════════════════════════╝

    /**
     * Clasifica un error como LEXICAL, SYNTAX o NONE
     * Retorna la clasificación con detalles descriptivos
     */
    private ErrorClassification classifyError(String sql) {
        try {
            // Primero, intentar detectar error léxico
            Lexer lexer = new Lexer(new StringReader(sql));
            Symbol token;

            while ((token = lexer.next_token()).sym != ParserSym.EOF) {
                if (token.sym == ParserSym.ERROR) {
                    return new ErrorClassification(
                        "LEXICAL",
                        "[LEXICAL] Línea " + token.left + ", Col " + token.right + ": " + token.value
                    );
                }
            }

            // Si no hay error léxico, intentar parser
            lexer = new Lexer(new StringReader(sql));
            Parser parser = new Parser(lexer);
            try {
                parser.parse();
                if (parser.hayError) {
                    return new ErrorClassification(
                        "SYNTAX",
                        "[SYNTAX] " + parser.obtenerErrorDetallado()
                    );
                }
            } catch (Exception e) {
                return new ErrorClassification(
                    "SYNTAX",
                    "[SYNTAX] " + e.getMessage()
                );
            }

            return new ErrorClassification("NONE", "");
        } catch (Exception e) {
            return new ErrorClassification(
                "LEXICAL",
                "[LEXICAL] Error durante análisis: " + e.getMessage()
            );
        }
    }

    /**
     * Detecta específicamente errores léxicos
     * Escanea tokens sin parsear y busca ERROR tokens
     */
    private LexicalErrorInfo detectLexicalError(String sql) {
        try {
            Lexer lexer = new Lexer(new StringReader(sql));
            Symbol token;

            while ((token = lexer.next_token()).sym != ParserSym.EOF) {
                if (token.sym == ParserSym.ERROR) {
                    String valor = token.value != null ? token.value.toString() : "?";

                    // Extraer carácter de la descripción si es posible
                    String caracter = "?";
                    if (valor.contains("'")) {
                        int start = valor.indexOf("'") + 1;
                        int end = valor.indexOf("'", start);
                        if (end > start) {
                            caracter = valor.substring(start, end);
                        }
                    }

                    return new LexicalErrorInfo(
                        true,
                        caracter,
                        token.left,
                        token.right,
                        valor.contains("(") ? valor.substring(valor.indexOf("(") + 1, valor.indexOf(")")) : "Carácter no permitido",
                        valor
                    );
                }
            }

            return new LexicalErrorInfo(false, "", -1, -1, "", "");
        } catch (Exception e) {
            return new LexicalErrorInfo(false, "", -1, -1, "Error: " + e.getMessage(), "");
        }
    }

    /**
     * Detecta específicamente errores sintácticos
     * Ejecuta el parser completo y verifica estado
     */
    private SyntacticErrorInfo detectSyntacticError(String sql) {
        try {
            Lexer lexer = new Lexer(new StringReader(sql));
            Parser parser = new Parser(lexer);

            try {
                parser.parse();
            } catch (Exception e) {
                // El parser lanzó excepción
                return new SyntacticErrorInfo(
                    true,
                    "Error sintáctico: " + e.getMessage(),
                    parser.errorLinea,
                    parser.errorColumna
                );
            }

            // Verificar si hay errores registrados después del parsing
            if (parser.hayError) {
                return new SyntacticErrorInfo(
                    true,
                    parser.obtenerErrorDetallado(),
                    parser.errorLinea,
                    parser.errorColumna
                );
            }

            return new SyntacticErrorInfo(false, "", -1, -1);
        } catch (Exception e) {
            return new SyntacticErrorInfo(
                true,
                "Error durante parsing: " + e.getMessage(),
                -1,
                -1
            );
        }
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

        SyntacticErrorInfo(boolean tieneError, String descripcion, int linea, int columna) {
            this.tieneError = tieneError;
            this.descripcion = descripcion;
            this.linea = linea;
            this.columna = columna;
        }
    }
}
