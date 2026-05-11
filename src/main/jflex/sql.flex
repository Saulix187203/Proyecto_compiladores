package com.sv;
import java_cup.runtime.*;

%%
%public
%class Lexer
%cup

// Macros
LETRA      = [a-zA-Z]
NUMERO     = [0-9]
ENTERO     = ("-"?{NUMERO}+)
DECIMAL    = ("-"?{NUMERO}+"."{NUMERO}+)
STRING     = "\"" [^\"]* "\""
ESPACIO    = [ \t\r\n]+
ID         = {LETRA}({LETRA}|{NUMERO}|_)*
NO_EQUAL  = ("!="|"<>")
// Palabras reservadas
RESERVADAS = ("create"|"table"|"select"|"from"|"where"|"update"|"set"|
              "insert"|"into"|"values"|"on"|"as"|"default"|"distinct"|"count")

// JOINS
INNER = "inner join"
LEFT  = "left join"
RIGHT = "right join"
FULL  = "full join"
JOIN  = "join"

// Data types SQL
DT_NUM   = ("int"|"decimal"|"float"|"numeric")
DT_STR   = ("char"|"varchar"|"text")
DT_DATE  = ("date"|"time"|"datetime")
DT_BOOL  = ("bit")

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
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
// JOINS
{INNER} { return symbol(ParserSym.INNER_JOIN); }
{LEFT}  { return symbol(ParserSym.LEFT_JOIN); }
{RIGHT} { return symbol(ParserSym.RIGHT_JOIN); }
{FULL}  { return symbol(ParserSym.FULL_JOIN); }
{JOIN}  { return symbol(ParserSym.JOIN); }
// Palabras reservadas
{RESERVADAS} {
  switch(yytext().toLowerCase()) {
    case "create": return symbol(ParserSym.CREATE);
    case "table":  return symbol(ParserSym.TABLE);
    case "select": return symbol(ParserSym.SELECT);
    case "from":   return symbol(ParserSym.FROM);
    case "where":  return symbol(ParserSym.WHERE);
    case "update": return symbol(ParserSym.UPDATE);
    case "set":    return symbol(ParserSym.SET);
    case "insert": return symbol(ParserSym.INSERT);
    case "into":   return symbol(ParserSym.INTO);
    case "values": return symbol(ParserSym.VALUES);
    case "on":     return symbol(ParserSym.ON);
    case "as":     return symbol(ParserSym.AS);
    case "default":return symbol(ParserSym.DEFAULT);
  }
}

// Tipos de datos numéricos
{DT_NUM} {
  switch(yytext().toLowerCase()) {
    case "int":     return symbol(ParserSym.DT_INT);
    case "decimal": return symbol(ParserSym.DT_DECIMAL);
    case "float":   return symbol(ParserSym.DT_FLOAT);
    case "numeric": return symbol(ParserSym.DT_NUMERIC);
  }
}
// Tipos de texto
{DT_STR} {
  switch(yytext().toLowerCase()) {
    case "char":    return symbol(ParserSym.DT_CHAR);
    case "varchar": return symbol(ParserSym.DT_VARCHAR);
    case "text":    return symbol(ParserSym.DT_TEXT);
  }
}
// Tipos de fecha y hora
{DT_DATE} {
  switch(yytext().toLowerCase()) {
    case "date":     return symbol(ParserSym.DT_DATE);
    case "time":     return symbol(ParserSym.DT_TIME);
    case "datetime": return symbol(ParserSym.DT_DATETIME);
  }
}
// Tipo booleano
{DT_BOOL} {
  return symbol(ParserSym.DT_BOOLEAN);
}
// Identificadores
{ID} {
  return symbol(ParserSym.VAR, yytext());
}
/* Caracter no reconocido */
. { return symbol(ParserSym.ERROR, yytext()); }
