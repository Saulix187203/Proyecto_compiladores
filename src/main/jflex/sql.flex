package com.sv;
import java_cup.runtime.*;

%%
%public
%class Lexer
%unicode
%cup
%line
%column

// Macros
LETRA      = [a-zA-Z]
NUMERO     = [0-9]
ENTERO     = ("-"?{NUMERO}+)
DECIMAL    = ("-"?{NUMERO}+"."{NUMERO}+)
STRING     = "'" [^']* "'"
ESPACIO    = [ \t\r\n]+
ID         = {LETRA}({LETRA}|{NUMERO}|_)*
NO_EQUAL  = ("!="|"<>")

%{
    private java_cup.runtime.Symbol symbol(int type) {
        return new java_cup.runtime.Symbol(type, yyline+1, yycolumn+1);
    }
    private java_cup.runtime.Symbol symbol(int type, Object value) {
        return new java_cup.runtime.Symbol(type, yyline+1, yycolumn+1, value);
    }
%}

%eofval{
  return symbol(ParserSym.EOF);
%eofval}

%%
// tokens simples
"("     { return symbol(ParserSym.PAREN_O); }
")"     { return symbol(ParserSym.PAREN_I); }
","     { return symbol(ParserSym.COMA); }
";"     { return symbol(ParserSym.DOT_COMA); }
"."     { return symbol(ParserSym.DOT); }
"*"     { return symbol(ParserSym.ALL); }
"="     { return symbol(ParserSym.ASSIGN); }
">"     { return symbol(ParserSym.MAYOR); }
"<"     { return symbol(ParserSym.MENOR); }
">="    { return symbol(ParserSym.MAYOR_IGUAL); }
"<="    { return symbol(ParserSym.MENOR_IGUAL); }
{NO_EQUAL} { return symbol(ParserSym.NOT_EQUAL); }
// valores primitivos
{DECIMAL}  { return symbol(ParserSym.DECI, Float.valueOf(yytext())); }
{ENTERO}   { return symbol(ParserSym.INT, Integer.valueOf(yytext())); }
{STRING}   { return symbol(ParserSym.STR, yytext()); }
{ESPACIO}  { /* ignorar */ }
{ID} {
  String lex = yytext().toLowerCase(); // normaliza todo a minúsculas
  switch(lex) {
    // JOINS
    case "inner": return symbol(ParserSym.INNER_JOIN);
    case "left":  return symbol(ParserSym.LEFT_JOIN);
    case "right": return symbol(ParserSym.RIGHT_JOIN);
    case "full":  return symbol(ParserSym.FULL_JOIN);
    case "join":  return symbol(ParserSym.JOIN);

    // Palabras reservadas
    case "create": return symbol(ParserSym.CREATE);
    case "table":  return symbol(ParserSym.TABLE);
    case "select": return symbol(ParserSym.SELECT);
    case "from":   return symbol(ParserSym.FROM);
    case "where":  return symbol(ParserSym.WHERE);
    case "update": return symbol(ParserSym.UPDATE);
    case "set":    return symbol(ParserSym.SET);
    case "disctinct": return symbol(ParserSym.DISTINCT);
    case "conteo": return symbol(ParserSym.COUNT);
    case "insert": return symbol(ParserSym.INSERT);
    case "into":   return symbol(ParserSym.INTO);
    case "values": return symbol(ParserSym.VALUES);
    case "on":     return symbol(ParserSym.ON);
    case "as":     return symbol(ParserSym.AS);

    // Tipos de datos numéricos
    case "int":     return symbol(ParserSym.DT_INT);
    case "decimal": return symbol(ParserSym.DT_DECIMAL);
    case "float":   return symbol(ParserSym.DT_FLOAT);
    case "numeric": return symbol(ParserSym.DT_NUMERIC);

    // Tipos de texto
    case "char":    return symbol(ParserSym.DT_CHAR);
    case "varchar": return symbol(ParserSym.DT_VARCHAR);
    case "text":    return symbol(ParserSym.DT_TEXT);

    // Tipos de fecha y hora
    case "date":     return symbol(ParserSym.DT_DATE);
    case "time":     return symbol(ParserSym.DT_TIME);
    case "datetime": return symbol(ParserSym.DT_DATETIME);

    // Tipo booleano
    case "bit": return symbol(ParserSym.DT_BOOLEAN);

    default: return symbol(ParserSym.VAR, yytext());
  }
}

/* Caracter no reconocido*/
. { System.out.println("Carácter no permitido: " + ParserSym.ERROR + " en linea " + (yyline+1)+ ", columna" + (yycolumn+1));
      return symbol(ParserSym.ERROR, yytext());}


