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
"("     { return symbol(ParserSym.PAREN_O,"("); }
")"     { return symbol(ParserSym.PAREN_I,")"); }
","     { return symbol(ParserSym.COMA,","); }
";"     { return symbol(ParserSym.DOT_COMA,";"); }
"."     { return symbol(ParserSym.DOT,"."); }
"*"     { return symbol(ParserSym.ALL,"*"); }
"="     { return symbol(ParserSym.ASSIGN, "="); }
">"     { return symbol(ParserSym.MAYOR,">"); }
"<"     { return symbol(ParserSym.MENOR,"<"); }
">="    { return symbol(ParserSym.MAYOR_IGUAL,">="); }
"<="    { return symbol(ParserSym.MENOR_IGUAL,"<="); }
{NO_EQUAL} { return symbol(ParserSym.NOT_EQUAL,yytext()); }
// valores primitivos
{DECIMAL}  { return symbol(ParserSym.DECI, Float.valueOf(yytext())); }
{ENTERO}   { return symbol(ParserSym.INT, Integer.valueOf(yytext())); }
{STRING}   { return symbol(ParserSym.STR, yytext()); }
{ESPACIO}  { /* ignorar */ }
{ID} {
  String lex = yytext().toLowerCase(); // normaliza todo a minúsculas
  switch(lex) {
    // JOINS
    case "inner": return symbol(ParserSym.INNER_JOIN,"inner");
    case "left":  return symbol(ParserSym.LEFT_JOIN,"left");
    case "right": return symbol(ParserSym.RIGHT_JOIN,"right");
    case "full":  return symbol(ParserSym.FULL_JOIN,"full");
    case "join":  return symbol(ParserSym.JOIN,"join");

    // Palabras reservadas
    case "create": return symbol(ParserSym.CREATE,"create");
    case "table":  return symbol(ParserSym.TABLE,"table");
    case "select": return symbol(ParserSym.SELECT,"select");
    case "from":   return symbol(ParserSym.FROM,"from");
    case "where":  return symbol(ParserSym.WHERE,"where");
    case "update": return symbol(ParserSym.UPDATE,"update");
    case "set":    return symbol(ParserSym.SET,"set");
    case "disctinct": return symbol(ParserSym.DISTINCT,"distinct");
    case "conteo": return symbol(ParserSym.COUNT,"conteo");
    case "insert": return symbol(ParserSym.INSERT,"insert");
    case "into":   return symbol(ParserSym.INTO,"into");
    case "values": return symbol(ParserSym.VALUES,"values");
    case "on":     return symbol(ParserSym.ON,"on");
    case "as":     return symbol(ParserSym.AS,"as");

    // Tipos de datos numéricos
    case "int":     return symbol(ParserSym.DT_INT,"int");
    case "decimal": return symbol(ParserSym.DT_DECIMAL,"decimal");
    case "float":   return symbol(ParserSym.DT_FLOAT,"float");
    case "numeric": return symbol(ParserSym.DT_NUMERIC,"numeric");

    // Tipos de texto
    case "char":    return symbol(ParserSym.DT_CHAR,"char");
    case "varchar": return symbol(ParserSym.DT_VARCHAR,"varchar");
    case "text":    return symbol(ParserSym.DT_TEXT,"text");

    // Tipos de fecha y hora
    case "date":     return symbol(ParserSym.DT_DATE,"date");
    case "time":     return symbol(ParserSym.DT_TIME,"time");
    case "datetime": return symbol(ParserSym.DT_DATETIME,"datetime");

    // Tipo booleano
    case "bit": return symbol(ParserSym.DT_BOOLEAN,"bit");

    default: return symbol(ParserSym.VAR, yytext());
  }
}

/* Caracter no reconocido*/
. { System.out.println("Carácter no permitido: " + ParserSym.ERROR + " en linea " + (yyline+1)+ ", columna" + (yycolumn+1));
      return symbol(ParserSym.ERROR, yytext());}


