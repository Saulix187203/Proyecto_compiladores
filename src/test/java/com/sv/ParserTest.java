package com.sv;

import java_cup.runtime.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private Parser parser;
    private Lexer lexer;

    @BeforeEach
    void setUp() {
        // Inicialización antes de cada prueba
    }

    // ==================== PRUEBAS CREATE TABLE ====================

    @Test
    void testCreateTableBasico() throws Exception {
        String sql = "CREATE TABLE usuarios (id INT);";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de CREATE TABLE básico debe ser exitoso");
    }

    @Test
    void testCreateTableMultipleColumnas() throws Exception {
        String sql = "CREATE TABLE usuarios (id INT, nombre VARCHAR(50), activo DT_BOOLEAN);";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de CREATE TABLE con múltiples columnas debe ser exitoso");
    }

    @Test
    void testCreateTableConDecimal() throws Exception {
        String sql = "CREATE TABLE productos (id INT, precio DECIMAL(10,2));";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de CREATE TABLE con DECIMAL debe ser exitoso");
    }

    @Test
    void testCreateTableConTiposCompletos() throws Exception {
        String sql = "CREATE TABLE registros (id INT, email VARCHAR(100), saldo NUMERIC(15,2), "
                + "descripcion TEXT, fecha_creacion DATE);";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de CREATE TABLE con tipos completos debe ser exitoso");
    }

    // ==================== PRUEBAS SELECT ====================

    @Test
    void testSelectTodos() throws Exception {
        String sql = "SELECT * FROM usuarios;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de SELECT * debe ser exitoso");
    }

    @Test
    void testSelectColumnasEspecificas() throws Exception {
        String sql = "SELECT id, nombre FROM usuarios;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de SELECT con columnas específicas debe ser exitoso");
    }

    @Test
    void testSelectConWhere() throws Exception {
        String sql = "SELECT id, nombre FROM usuarios WHERE id = 1;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de SELECT con WHERE debe ser exitoso");
    }

    @Test
    void testSelectConDistinct() throws Exception {
        String sql = "SELECT DISTINCT ciudad FROM usuarios;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de SELECT DISTINCT debe ser exitoso");
    }

    @Test
    void testSelectConCount() throws Exception {
        String sql = "SELECT COUNT(*) FROM usuarios;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de SELECT COUNT debe ser exitoso");
    }

    @Test
    void testSelectConJoinInner() throws Exception {
        String sql = "SELECT u.id, u.nombre FROM usuarios u INNER JOIN pedidos p ON u.id = p.usuario_id;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de SELECT con INNER JOIN debe ser exitoso");
    }

    @Test
    void testSelectConJoinLeft() throws Exception {
        String sql = "SELECT u.id FROM usuarios u LEFT JOIN pedidos ON u.id = pedidos.usuario_id;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de SELECT con LEFT JOIN debe ser exitoso");
    }

    @Test
    void testSelectConMultipleCondiciones() throws Exception {
        String sql = "SELECT * FROM usuarios WHERE id > 5;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de SELECT con múltiples condiciones debe ser exitoso");
    }

    // ==================== PRUEBAS UPDATE ====================

    @Test
    void testUpdateBasico() throws Exception {
        String sql = "UPDATE usuarios SET nombre = \"Juan\" WHERE id = 1;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de UPDATE básico debe ser exitoso");
    }

    @Test
    void testUpdateMultipleColumnas() throws Exception {
        String sql = "UPDATE usuarios SET nombre = \"Juan\", email = \"juan@example.com\" WHERE id = 1;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de UPDATE con múltiples columnas debe ser exitoso");
    }

    @Test
    void testUpdateConValoresNumericos() throws Exception {
        String sql = "UPDATE productos SET precio = 99.99 WHERE id = 5;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de UPDATE con valores numéricos debe ser exitoso");
    }

    @Test
    void testUpdateConCondicionesMayor() throws Exception {
        String sql = "UPDATE usuarios SET activo = 1 WHERE edad > 18;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de UPDATE con condición mayor debe ser exitoso");
    }

    // ==================== PRUEBAS INSERT ====================

    @Test
    void testInsertBasico() throws Exception {
        String sql = "INSERT INTO usuarios VALUES (1, \"Carlos\", \"carlos@example.com\");";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de INSERT básico debe ser exitoso");
    }

    @Test
    void testInsertConColumnasEspecificas() throws Exception {
        String sql = "INSERT INTO usuarios (id, nombre) VALUES (1, \"Carlos\");";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de INSERT con columnas específicas debe ser exitoso");
    }

    @Test
    void testInsertConValoresnumericos() throws Exception {
        String sql = "INSERT INTO productos (id, precio) VALUES (1, 99.99);";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de INSERT con valores numéricos debe ser exitoso");
    }

    @Test
    void testInsertConMultiplesValores() throws Exception {
        String sql = "INSERT INTO usuarios (id, nombre, email, activo) VALUES (1, \"Ana\", \"ana@example.com\", 1);";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de INSERT con múltiples valores debe ser exitoso");
    }

    @Test
    void testInsertDesdeSelect() throws Exception {
        String sql = "INSERT INTO usuarios_backup SELECT * FROM usuarios;";
        lexer = new Lexer(new StringReader(sql));
        parser = new Parser(lexer);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de INSERT desde SELECT debe ser exitoso");
    }

}
