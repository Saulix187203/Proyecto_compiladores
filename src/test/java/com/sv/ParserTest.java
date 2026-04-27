package com.sv;

import java_cup.runtime.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

/**
 * Pruebas unitarias para el Parser y Lexer
 */
public class ParserTest {

    private Parser parser;
    private Lexer lexer;

    @BeforeEach
    public void setUp() {
        // Se inicializa antes de cada prueba
    }

    /**
     * Prueba 1: Entrada simple con variable string
     */
    @Test
    public void testVariableString() throws Exception {
        String input = "hola_mundo = \"hola mundo\"";
        lexer = new Lexer(new StringReader(input));
        parser = new Parser(lexer);
        Object result = parser.parse();
        assertNotNull(result, "El parser debería retornar un resultado");
        System.out.println("[TEST 1] Entrada: " + input);
        System.out.println("[TEST 1] Resultado: " + result);
    }

    /**
     * Prueba 2: Variable con número entero
     */
    @Test
    public void testVariableInt() throws Exception {
        String input = "numero1 = 5";
        lexer = new Lexer(new StringReader(input));
        parser = new Parser(lexer);
        Object result = parser.parse();
        assertNotNull(result, "El parser debería retornar un resultado");
        System.out.println("[TEST 2] Entrada: " + input);
        System.out.println("[TEST 2] Resultado: " + result);
    }

    /**
     * Prueba 3: Variable con número flotante
     */
    @Test
    public void testVariableFloat() throws Exception {
        String input = "flotante = 2.5";
        lexer = new Lexer(new StringReader(input));
        parser = new Parser(lexer);
        Object result = parser.parse();
        assertNotNull(result, "El parser debería retornar un resultado");
        System.out.println("[TEST 3] Entrada: " + input);
        System.out.println("[TEST 3] Resultado: " + result);
    }

    /**
     * Prueba 4: Variable con string vacío
     */
    @Test
    public void testVariableEmptyString() throws Exception {
        String input = "random = \"\"";
        lexer = new Lexer(new StringReader(input));
        parser = new Parser(lexer);
        Object result = parser.parse();
        assertNotNull(result, "El parser debería retornar un resultado");
        System.out.println("[TEST 4] Entrada: " + input);
        System.out.println("[TEST 4] Resultado: " + result);
    }

    /**
     * Prueba 5: Múltiples asignaciones (entrada completa)
     */
    @Test
    public void testMultipleAssignments() throws Exception {
        String input = "hola_mundo = \"hola mundo\"\n" +
                       "numero1 = 5\n" +
                       "flotante = 2.5\n" +
                       "random = \"\"";
        lexer = new Lexer(new StringReader(input));
        parser = new Parser(lexer);
        Object result = parser.parse();
        assertNotNull(result, "El parser debería retornar un resultado para múltiples asignaciones");
        System.out.println("[TEST 5] Entrada:\n" + input);
        System.out.println("[TEST 5] Resultado: " + result);
    }

    /**
     * Prueba 6: Variable con nombre que usa guion bajo
     */
    @Test
    public void testVariableWithUnderscore() throws Exception {
        String input = "mi_variable_test = 42";
        lexer = new Lexer(new StringReader(input));
        parser = new Parser(lexer);
        Object result = parser.parse();
        assertNotNull(result, "El parser debería aceptar variables con guion bajo");
        System.out.println("[TEST 6] Entrada: " + input);
        System.out.println("[TEST 6] Resultado: " + result);
    }

    /**
     * Prueba 7: Número negativo
     */
    @Test
    public void testNegativeNumber() throws Exception {
        String input = "negativo = -10";
        lexer = new Lexer(new StringReader(input));
        parser = new Parser(lexer);
        Object result = parser.parse();
        assertNotNull(result, "El parser debería aceptar números negativos");
        System.out.println("[TEST 7] Entrada: " + input);
        System.out.println("[TEST 7] Resultado: " + result);
    }

    /**
     * Prueba 8: Número decimal negativo
     */
    @Test
    public void testNegativeFloat() throws Exception {
        String input = "decimal_negativo = -3.14";
        lexer = new Lexer(new StringReader(input));
        parser = new Parser(lexer);
        Object result = parser.parse();
        assertNotNull(result, "El parser debería aceptar decimales negativos");
        System.out.println("[TEST 8] Entrada: " + input);
        System.out.println("[TEST 8] Resultado: " + result);
    }
}
