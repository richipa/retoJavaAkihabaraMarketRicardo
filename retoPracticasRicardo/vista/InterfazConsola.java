package vista;
import java.util.Scanner;
import java.util.List;
import modelo.ProductoOtaku;
import modelo.ClienteOtaku;

public class InterfazConsola {
	Scanner scanner = new Scanner(System.in);
	
// METODOS PARA MOSTRAR LOS MENUS
	
	//MENU PRINCIPAL
	public int mostrarMenuPrincipal() {
        System.out.println("\n=== AKIHABARA MARKET ===");
        System.out.println("1. Gestionar Productos");
        System.out.println("2. Gestionar Clientes");
        System.out.println("3. Salir");
        System.out.print("Selecciona una opción: ");
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Entrada no válida, escribe un número.");
            return -1; 
        }
    }
	
	//MENU PRODUCTOS
    public int mostrarMenuProductos() {
        System.out.println("\n=== GESTIÓN DE PRODUCTOS ===");
        System.out.println("1. Añadir nuevo producto");
        System.out.println("2. Consultar producto por ID");
        System.out.println("3. Ver todos los productos");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Utilizar asistente IA");
        System.out.println("7. Volver al menú principal");
        System.out.print("Selecciona una opción: ");
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Entrada no válida, escribe un número.");
            return -1; 
        }
    }
    
 // MENU DE CLIENTES
    public int mostrarMenuClientes() {
        System.out.println("\n=== GESTIÓN DE CLIENTES ===");
        System.out.println("1. Añadir nuevo cliente");
        System.out.println("2. Consultar cliente por ID");
        System.out.println("3. Ver todos los clientes");
        System.out.println("4. Actualizar cliente");
        System.out.println("5. Eliminar cliente");
        System.out.println("6. Volver al menú principal");
        System.out.print("Selecciona una opción: ");
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Entrada inválida.");
            return -1;
        }
    }
    
//MENU DE LA IA
    public int mostrarMenuIA() {
    	System.out.println("\n=== CONSULTA A LA IA ===");
    	System.out.println("1. Generar descripción de producto");
    	System.out.println("2. Sugerir categoría para nuevo producto");
    	System.out.println("3. Salir al menú principal");
    	System.out.println("Selecciona una opción: ");
    	
    	try {
    		return scanner.nextInt();
    	} catch (Exception e) {
    		scanner.nextLine();
    		System.out.println("Entrada no válida, escribe un numero.");
    		return -1;
    	}
    }
    
//METODOS DE PRODUCTOS Y CLIENTES
//--------------------------------
    
 //PEDIR NOMBRE DE PRODUCTO NUEVO (para la opcion 2 del menú ia)
    public String leerNombreParaCategoria() {
        scanner.nextLine();
        System.out.print("Introduce el nombre de un nuevo producto para ver de que categoría podría ser: ");
        return scanner.nextLine();
    }
    
    //SOLICITAR DATOS PARA CREAR UN PRODUCTO
    public ProductoOtaku leerDatosProductoNuevo() {
        scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Categoría: ");
        String categoria = scanner.nextLine();

        try {
            System.out.print("Precio: ");
            double precio = scanner.nextDouble();
            
            if (precio < 0) {
                System.out.println("El precio no puede ser negativo.");
                return null;
            }

            System.out.print("Stock: ");
            int stock = scanner.nextInt();

            if (stock < 0) {
                System.out.println("El stock no puede ser negativo.");
                return null;
            }

            return new ProductoOtaku(nombre, categoria, precio, stock);
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Entrada no válida, producto no creado.");
            return null;
        }
    }
    
    //SOLICITAR ID DE PRODUCTO
    public int leerIdProducto() {
        System.out.print("Introduce el ID del producto: ");
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Entrada no válida, escribe un número.");
            return -1;
        }
    }
    
    
    //MOSTRAR PRODUCTO
    public void mostrarProducto(ProductoOtaku producto) {
        if (producto != null) {
            System.out.printf("ID: %d | Nombre: %s | Categoría: %s | Precio: %.2f € | Stock: %d\n",
                    producto.getId(), producto.getNombre(), producto.getCategoria(),
                    producto.getPrecio(), producto.getStock());
        } else {
            System.out.println("Producto no encontrado.");
        }
    }
    
    
    //MOSTRAR LISTA DE TODOS LOS PRODUCTOS
    public void mostrarListaProductos(List<ProductoOtaku> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
        } else {
            for (ProductoOtaku p : lista) {
                mostrarProducto(p);
            }
        }
    }
    
    
    //MOSTRAR MENSAJE DE CUALQUIER TIPO
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    
    //ACTUALIZAR PRODUCTO
    public void actualizarProducto(ProductoOtaku producto) {
        scanner.nextLine();

        System.out.print("Nombre (" + producto.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) {
            producto.setNombre(nombre);
        }

        System.out.print("Categoría (" + producto.getCategoria() + "): ");
        String categoria = scanner.nextLine();
        if (!categoria.isEmpty()) {
            producto.setCategoria(categoria);
        }

        System.out.print("Precio (" + producto.getPrecio() + "): ");
        String precioInput = scanner.nextLine();
        if (!precioInput.isEmpty()) {
            try {
                producto.setPrecio(Double.parseDouble(precioInput));
            } catch (NumberFormatException e) {
                System.out.println("Precio inválido, no se modificó.");
            }
        }

        System.out.print("Stock (" + producto.getStock() + "): ");
        String stockInput = scanner.nextLine();
        if (!stockInput.isEmpty()) {
            try {
                producto.setStock(Integer.parseInt(stockInput));
            } catch (NumberFormatException e) {
                System.out.println("Stock inválido, no se modificó.");
            }
        }
    }
    
 // PEDIR DATOS PARA NUEVO CLIENTE
    public ClienteOtaku leerDatosClienteNuevo() {
        scanner.nextLine(); 
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        return new ClienteOtaku(nombre, email, telefono);
    }

    // PEDIR ID DE CLIENTE
    public int leerIdCliente() {
        System.out.print("Introduce el ID del cliente: ");
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Entrada inválida.");
            return -1;
        }
    }

    // MOSTRAR CLIENTE
    public void mostrarCliente(ClienteOtaku cliente) {
        if (cliente != null) {
            System.out.printf("ID: %d | Nombre: %s | Email: %s | Teléfono: %s | Fecha registro: %s\n",
                    cliente.getId(), cliente.getNombre(), cliente.getEmail(),
                    cliente.getTelefono(), cliente.getFechaRegistro());
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    // MOSTRAR LISTA DE CLIENTES
    public void mostrarListaClientes(List<ClienteOtaku> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (ClienteOtaku c : lista) {
                mostrarCliente(c);
            }
        }
    }
}
