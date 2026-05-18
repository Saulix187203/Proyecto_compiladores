<?php
//validamos datos del servidor
$user = "COYVA";
$pass = "Saulo*18072003";
$host = "%";

$connection = mysqli_connect($host, $user, $pass);

// Verificamos la conexión
if (!$connection) {
  die("No se ha podido conectar con el servidor: " . mysqli_connect_error());
} else {
  echo "<b><h3>Hemos conectado al servidor</h3></b>";
}

// Seleccionar la base de datos
$datab = "dbFerreteria";
$db = mysqli_select_db($connection, $datab);

if (!$db) {
  die("No se ha podido encontrar la base de datos: " . mysqli_error($connection));
} else {
  echo "<h3>Base de datos seleccionada:</h3>";
}

// Escapar las entradas para evitar inyección SQL
$nombre = mysqli_real_escape_string($connection, $_POST["name"]);
$cantidad = mysqli_real_escape_string($connection, $_POST["cant"]);
$precio = mysqli_real_escape_string($connection, $_POST["much"]);

// Insertar datos en la tabla
$instruccion_SQL = "INSERT INTO PRODUCTO (nombre, cantidad, precio) 
                    VALUES ('$nombre', '$cantidad', '$precio')";

$resultado = mysqli_query($connection, $instruccion_SQL);

if (!$resultado) {
  echo "Error al insertar datos: " . mysqli_error($connection);
} else {
  echo "Datos insertados correctamente.";
}

// Consultar datos
$consulta = "SELECT * FROM PRODUCTO";
$result = mysqli_query($connection, $consulta);

if (!$result) {
  die("No se ha podido realizar la consulta: " . mysqli_error($connection));
}

// Mostrar resultados en una tabla
echo "<table>";
echo "<tr>";
echo "<th><h1>id</th></h1>";
echo "<th><h1>Nombre</th></h1>";
echo "<th><h1>Cantidad</th></h1>";
echo "<th><h1>Precio</th></h1>";
echo "</tr>";

while ($colum = mysqli_fetch_array($result)) {
  echo "<tr>";
  echo "<td><h2>" . $colum['idproducto'] . "</td></h2>";
  echo "<td><h2>" . $colum['nombre'] . "</td></h2>";
  echo "<td><h2>" . $colum['cantidad'] . "</td></h2>";
  echo "<td><h2>" . $colum['precio'] . "</td></h2>";
  echo "</tr>";
}
echo "</table>";

// Cerrar la conexión
mysqli_close($connection);

echo '<a href="index.html"> Volver Atrás</a>';
?>

