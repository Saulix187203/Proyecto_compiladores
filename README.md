# Proyecto / Laboratorio: Gramática SQL simplificada con JFlex y CUP

## 📌 Objetivo general

Diseñar e implementar una gramática que permita reconocer y validar instrucciones básicas de un lenguaje SQL simplificado. El proyecto deberá integrar:

   - Un analizador léxico en JFlex.
   - Un analizador sintáctico en CUP.
   - Una clase principal en Java para ejecutar y probar el análisis.

## 🧩 Descripción del problema

Se desea construir un analizador que permita reconocer y validar instrucciones SQL simplificadas relacionadas con:

   - CREATE TABLE
   - SELECT
   - UPDATE
   - INSERT

El sistema deberá leer una o varias instrucciones de entrada y determinar si la sintaxis es válida según la gramática definida.

## 🛠 Instrucciones que debe reconocer la gramática
### 1. CREATE TABLE

La gramática deberá permitir sentencias como:


    create table estudiante (
        edad int,
        nombre varchar(50),
        fecha_nacimiento datetime,
        score decimal(18,2)
    );

Debe reconocer:

   - Nombre de tabla
   - Lista de columnas
   - Tipos de datos:
       * int
       * varcharNo
       * datetime
       * decimal(p,s)

### 2. SELECT

La gramática deberá reconocer sentencias como:

    select conteo(*) from tb_estudiante;
    select * from tabla1 a join tabla2 b on (b.id = a.id);


Debe reconocer:

   - Selección de *
   - Lista de campos
   - Función conteo(*)
   - Cláusula from
   - Alias de tablas
   - Cláusula join ... on (...)

### 3. UPDATE

La gramática deberá reconocer sentencias como:

    update tabla1 set campo = '123' where condicion = 123;


Debe reconocer:

   - Nombre de tabla
   - Cláusula set
   - Asignaciones
   - Cláusula opcional where

### 4. INSERT INTO ... VALUES

La gramática deberá reconocer sentencias como:

    insert into tabla1(campo1, campo2) values (123, 'Juan');


Debe reconocer:

   - Nombre de tabla
   - Lista opcional de columnas
   - Lista de valores

### 5. INSERT INTO ... SELECT

La gramática deberá reconocer sentencias como:

    insert into tabla1 select * from tabla2;
    insert into tabla1(campo1, campo2)
    select a.campo1, a.campo2
    from tabla2 a join tabla3 b on (b.id = a.id);

Debe reconocer:

   - Nombre de tabla
   - Lista opcional de columnas
   - Una consulta select como fuente de datos

## ⚙ Requerimientos técnicos

### 1. Analizador léxico en JFlex

El archivo léxico deberá reconocer, como mínimo, los siguientes elementos:

   - Palabras reservadas:
       * <span style="color:red"> create, table, select, from, where </span>
       * <span style="color:red"> update, set, insert, into, values </span>
       * <span style="color:red"> join, on, as </span>
       * <span style="color:red"> int, varchar, datetime, decimal </span>
   - Identificadores
   - Números
   - Cadenas de texto
   - Símbolos especiales:
     * <span style="color:red"> (, ), ,, ; </span>
     * <span style="color:red"> ., *, = </span>

### 2. Analizador sintáctico en CUP

El archivo de CUP deberá definir la gramática necesaria para validar las sentencias descritas en este enunciado.

La gramática deberá contemplar al menos:

   - Símbolo inicial
   - Lista de sentencias
   - Reglas para CREATE TABLE
   - Reglas para SELECT
   - Reglas para UPDATE
   - Reglas para INSERT
   - Condiciones simples en cláusulas ON y WHERE

### 3. Programa principal en Java

Se deberá implementar una clase principal que:

   - Lea una entrada de texto
   - Invoque el analizador léxico y sintáctico
   - Muestre si la sentencia es válida o no
   - Reporte errores léxicos o sintácticos cuando existan

## 📏 Reglas mínimas del lenguaje

   - Cada sentencia debe finalizar con ;
   - Se pueden procesar múltiples sentencias en un mismo archivo de entrada
   - Los identificadores representan nombres de tablas, columnas o alias
   - Las condiciones podrán manejar, como mínimo, expresiones del tipo:
        *  <span style="color:red">campo = valor</span>
        *  <span style="color:red">tabla1.campo = tabla2.campo</span>
   - Los valores podrán ser:
       * Números
       * Cadenas
       * Referencias a campos


✅ Ejemplos de entrada válidos

    create table estudiante (
    edad int,
    nombre varchar(50),
    fecha_nacimiento datetime,
    score decimal(18,2)
    );

    select conteo(*) from tb_estudiante;
    
    select * from tabla1 a join tabla2 b on (b.id = a.id);
    
    update tabla1 set campo = '123' where condicion = 123;
    
    insert into tabla1(campo1, campo2) values (123, 'Juan');
    
    insert into tabla1 select * from tabla2;
    
    insert into tabla1(campo1, campo2) select a.campo1, a.campo2 from tabla2 a join tabla3 b on (b.id = a.id);

## ⚠ Consideraciones importantes

   - No se evaluará la ejecución real de SQL sobre una base de datos.
   - El proyecto únicamente debe validar la estructura léxica y sintáctica.
   - No es necesario implementar semántica avanzada.
   - Se recomienda mantener la gramática organizada y modular.
   - Se valorará el manejo adecuado de errores.

![img.png](img.png)

## 📌 Validación de 1ra. Fase del Proyecto

En esta actividad se realizará la validación de la primera fase del proyecto, con el objetivo de comprobar el correcto funcionamiento del analizador léxico y sintáctico desarrollado.
# 🧩 Instrucciones

En esta actividad se evaluará la capacidad del sistema para procesar archivos de entrada válidos e inválidos.

El sistema debe permitir la carga y análisis de dos archivos distintos:

1. Archivo sin errores:
    Debe demostrar que el sistema es capaz de procesar correctamente todas las sentencias sin generar errores.
    Se debe incluir evidencia de la ejecución completa del archivo.


2. Archivo con errores:
    Debe demostrar que el sistema detecta correctamente errores léxicos y sintácticos.
    Se debe incluir evidencia de los errores generados por el sistema.

# ⚙ Requerimientos técnicos

- El sistema debe permitir cargar archivos de entrada.
- Debe mostrar claramente si las sentencias son válidas o contienen errores.
- Debe identificar errores léxicos y sintácticos de forma diferenciada.
- Los mensajes de error deben ser claros y comprensibles.


## Comandos para usar
- Para compilar el proyecto:
    ```bash
    mvn jflex:generate
    mvn cup:generate
    mvn clean verify
    ```
- Para eliminar los archivos generados por JFlex y CUP dentro de target:
    ```bash
    mvn clean
    ```

## respuesta
Voy a indicar los cambios necesarios y dar ejemplos de código (exactos) para que puedas construir un árbol AST útil y luego imprimir cada parte en pantalla en el formato que indicas (valor:tipo, una por línea).
Checklist de cambios a aplicar (no los voy a modificar yo — sigue siendo lectura/guía):
1. Añadir métodos de ayuda en ASTNode.java para añadir hijos y para imprimir el árbol.
2. Cambiar las acciones en parser.cup para construir un nodo padre que agregue como hijos los ASTNode de los tokens y subproducciones (ahora las acciones crean varios RESULT = new ASTNode(...) y se pisan; hay que crear un root y hacer root.children.add(...) y devolver root).
3. En App.java imprimir el AST retornado por parser.parse() (castearlo a ASTNode y llamar al método de impresión).
4. Regenerar parser/jflex y compilar (ej.: mvn clean package).
A continuación tienes los snippets concretos que debes aplicar.
   1. ASTNode.java — añade helpers y un método de impresión plana (valor:tipo por línea). Reemplaza el contenido actual por este (o añade los métodos indicados):
```java
package com.sv;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    public String type;
    public String value;
    public List<ASTNode> children;

    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    // helper para añadir hijo
    public void add(ASTNode child) {
        if (child != null) this.children.add(child);
    }

    // helper para añadir muchos hijos
    public void addAll(List<ASTNode> list) {
        if (list != null) this.children.addAll(list);
    }

    // imprime cada nodo en una línea: valor:tipo (preorder)
    public void printFlat() {
        printFlat("");
    }

    private void printFlat(String indent) {
        // imprime solo valor:tipo, sin indentación extra (pero mantengo indent como opción)
        String v = value != null ? value : "";
        String t = type != null ? type : "";
        System.out.println(v + ":" + t);
        for (ASTNode c : children) {
            c.printFlat(indent + "  ");
        }
    }

    @Override
    public String toString() {
        return (value != null ? value : "") + ":" + (type != null ? type : "");
    }
} 
```
   2. Cambios en parser.cup — ejemplo para las producciones relevantes (sobre todo sql, update_stmt, assign_condition_list). Necesitas nombrar las subproducciones para poder usarlas en las acciones (ej: assign_condition_list:acl), y construir un root que agrupe. Aquí unos ejemplos concretos (sustituye las acciones actuales por las que siguen):
    
    Producción sql (para devolver el árbol con la oración y el DOT_COMA):
```java
sql ::=
    sentence:sent DOT_COMA:dotcoma {: 
        ASTNode root = new ASTNode("sql", "");
        // añadir la sentence (si ya es ASTNode)
        if (sent != null) root.add((ASTNode) sent);
        root.add(new ASTNode("dot_coma", dotcoma.toString()));
        RESULT = root;
    :}
    | sql:previous sentence:sent DOT_COMA:dotcoma {: 
        ASTNode root = new ASTNode("sql", "");
        if (previous != null) root.add((ASTNode) previous);
        if (sent != null) root.add((ASTNode) sent);
        root.add(new ASTNode("dot_coma", dotcoma.toString()));
        RESULT = root;
    :}
    | ERROR:error_sql{: RESULT = new ASTNode("error_sql", error_sql.toString());:}
    ;
```
    Producción update_stmt (para agrupar UPDATE, table_name, SET, assign_condition_list y opcional WHERE):

```java
update_stmt ::=
    UPDATE:update VAR:var SET:set assign_condition_list:acl WHERE:where condition:cond {:
        ASTNode root = new ASTNode("update_stmt", "");
        root.add(new ASTNode("update", update.toString()));
        root.add(new ASTNode("var", var.toString()));
        root.add(new ASTNode("set", set.toString()));
        // acl viene de assign_condition_list (puede ser un nodo que contenga varias asignaciones)
        if (acl != null) root.add((ASTNode) acl);
        root.add(new ASTNode("where", where.toString()));
        if (cond != null) root.add((ASTNode) cond);
        RESULT = root;
    :}
    ;
```
    Producción assign_condition_list (para agrupar varias asignaciones o condiciones):
```java
assign_condition_list ::=
    VAR:var ASSIGN:assign primitives:pr {:
        ASTNode pair = new ASTNode("assign_pair", "");
        pair.add(new ASTNode("var", var.toString()));
        pair.add(new ASTNode("=", assign.toString()));
        // primitives (INT/DECI/STR) ya genera su propio ASTNode en la producción primitives
        if (pr != null) pair.add((ASTNode) pr);
        RESULT = pair;
    :}
    | assign_condition_list:list COMA:coma VAR:var ASSIGN:assign primitives:pr {:
        ASTNode root = new ASTNode("assign_condition_list", "");
        // traer asignaciones previas (list)
        if (list != null) root.add((ASTNode) list);
        // coma token para separar (opcional)
        root.add(new ASTNode("coma", coma.toString()));
        ASTNode pair = new ASTNode("assign_pair", "");
        pair.add(new ASTNode("var", var.toString()));
        pair.add(new ASTNode("=", assign.toString()));
        if (pr != null) pair.add((ASTNode) pr);
        root.add(pair);
        RESULT = root;
    :}
    ;
``` 
    Notas sobre los cambios en parser.cup:
- He usado nombres (:sent, :dotcoma, :acl, :cond, :pr, :list) para poder acceder a valores dentro de la acción.
- En las acciones convierto esos objetos a ASTNode cuando son producciones que ya retornan ASTNodes.
- El root que devuelves debería representar la estructura completa de la sentencia. Ajusta los nombres de tipo/valor si quieres otros nombres.

    3.App.java — para imprimir el AST después de parsear. Aquí un ejemplo de cómo hacerlo (sustituye el contenido del main por esto):
```java
try {
    Parser parser = new Parser(new Lexer(new StringReader(input)));
    Object result = parser.parse();
    if (result instanceof ASTNode) {
        ASTNode root = (ASTNode) result;
        // imprime cada nodo como valor:tipo (preorder)
        root.printFlat();
    } else {
        System.out.println("Resultado: " + result);
    }
} catch (Exception e) {
System.out.println("✗ Entrada no aceptada. Error: " + e.getMessage());
}
```
