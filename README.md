# retoJavaAkihabaraMarketRicardo
Este es mi trabajo de prácticas para 1ºDAM, hecho en Java.
---

## ¿Qué es Akihabara Market?

Es una aplicación de consola y escritorio que simula la gestión de una tienda otaku: puedes añadir productos o clientes, gestionarlos, verlos en una interfaz gráfica moderna y hasta pedir ayuda a una IA para describir productos o sugerir categorías.

---

## Tecnologías utilizadas

- Java 17+
- MySQL + JDBC
- Gson (Google)
- Swing
- OpenRouter API (IA)
- FlatLaf (tema visual moderno)
- Patrón MVC (Modelo-Vista-Controlador)

---

## Estructura del Proyecto

### Paquete `modelo`
| Clase             | Descripción |
|------------------|-------------|
| `ProductoOtaku`  | Representa los productos del inventario. |
| `ProductoDAO`    | Acceso a la base de datos para CRUD de productos. |
| `ClienteOtaku`   | Representa a los clientes de la tienda. |
| `ClienteDAO`     | Acceso a la base de datos para CRUD de clientes. |
| `Conexion`       | Abre la conexión con MySQL. |
| `LlmService`     | Permite interactuar con la IA de OpenRouter. |

### Paquete `vista`
| Clase              | Descripción |
|-------------------|-------------|
| `InterfazConsola` | Menús y formularios por consola. |
| `VentanaGrafica`  | Interfaz gráfica principal con botones y subventanas. |

### Paquete `controlador`
| Clase             | Descripción |
|------------------|-------------|
| `ControladorMenu`| Lógica del programa y conexión entre modelo y vista (consola). |
| `Main`           | Punto de entrada para consola. |
| `MainGrafico`    | Punto de entrada para interfaz gráfica. |

### Paquete `config`
| Clase           | Descripción |
|----------------|-------------|
| `Configuracion`| Carga la API key desde un archivo externo. |

---

## Script de Base de Datos

```sql
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    categoria VARCHAR(255),
    precio DOUBLE,
    stock INT
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_registro DATE DEFAULT CURRENT_DATE
);
```

---

## Configuración de la API (IA)

1. Ve a [https://openrouter.ai](https://openrouter.ai), crea cuenta y copia tu API key.
2. Crea una carpeta `config` y dentro un archivo `configuracion.properties` con este contenido:

```
API_KEY=sk-or-v1-tu-clave-aqui
```

3. La clase `Configuracion.java` la leerá automáticamente.

---

## Cómo ejecutar

### Modo consola

```bash
javac controlador/Main.java
java controlador.Main
```

### Modo Swing (Gráfico)

```bash
javac controlador/MainGrafico.java
java controlador.MainGrafico
```

No olvides tener en tu classpath `gson.jar` y el conector de MySQL.

---

## Funcionalidades

### Productos

- Crear producto
- Consultar por ID
- Listar todos
- Editar producto
- Eliminar producto
- Generar descripción IA
- Sugerir categoría IA

### Clientes (BONUS II)

- Crear cliente
- Consultar por ID
- Listar todos
- Editar cliente
- Eliminar cliente

### Interfaz Swing (BONUS I)

- Menú visual con botones grandes
- Tablas dinámicas de productos y clientes
- Formulario de entrada
- Edición y borrado desde ventanas emergentes

---

## Pruebas recomendadas

- Añadir producto sin datos ➜ error esperado.
- Precio negativo ➜ validación rechazada.
- Buscar ID que no existe ➜ aviso correcto.
- Consultar cliente recién añadido.
- Ejecutar IA sin clave válida ➜ error 401.
- Interactuar con Swing y ver si la tabla se actualiza.

---

## Notas finales

Este proyecto lo he desarrollado paso a paso como parte de mi aprendizaje en Java. Ha sido una forma muy práctica de entender cómo funcionan las bases de datos, cómo aplicar el patrón MVC y cómo crear interfaces gráficas reales. Además, integrar una IA me ha parecido una forma divertida y útil de modernizar la aplicación.

---

**Gracias por visitar este repositorio. Espero que te sirva de ayuda para aprender o como inspiración para tus propios proyectos.**


---

### Cambios de Última Hora

Se ha añadido un **menú principal en la consola** como entrada principal del programa. Ahora el menú de productos (MenuProductos) ya no actúa como menú principal, sino que está accesible desde esta nueva pantalla principal junto al resto de funcionalidades como gestión de clientes e IA.
