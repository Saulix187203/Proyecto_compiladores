package com.sv;

import java_cup.runtime.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void testSelectConCount() throws Exception {
        String sql = "CREATE TABLE productos (id INT, precio DECIMAL(10,2));";
        Lexer lex = new Lexer(new StringReader(sql));
        Parser parser = new Parser(lex);
        Symbol result = parser.parse();
        assertNotNull(result, "El parse de CREATE TABLE con DECIMAL debe ser exitoso");
    }

}
