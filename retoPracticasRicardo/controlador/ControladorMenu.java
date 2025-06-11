package controlador;

import modelo.*;
import vista.*;

public class ControladorMenu {
	
	//INSTANCIAS OBLIGATORIAS
	InterfazConsola vista = new InterfazConsola();
	ProductoDAO dao = new ProductoDAO();
	ClienteDAO clienteDAO = new ClienteDAO();
	
	//MENU PRINCIPAL
	public void menuPrincipal() {
		boolean salir = false;
        while (!salir) {
            int opcion = vista.mostrarMenuPrincipal();

            switch (opcion) {
                case 1:
                    menuProductos();
                    break;

                case 2:
                    menuClientes();
                    break;
                                        
                case 3:
                    vista.mostrarMensaje("¡Gracias por comprar en Akihabara Market!");
                    salir = true;
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }
	
	
	//MENU PRODUCTOS
    public void menuProductos() {
    	
    	// INSERTA PRODUCTOS DE EJEMPLO SI NO HAY
    	SetupDatos.PrimerosProductos(dao);
        boolean salir = false;

        while (!salir) {
            int opcion = vista.mostrarMenuProductos();

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;

                case 2:
                    consultarProductoPorId();
                    break;

                case 3:
                    vista.mostrarListaProductos(dao.obtenerTodosLosProductos());
                    break;

                case 4:
                    actualizarProducto();
                    break;

                case 5:
                    eliminarProducto();
                    break;

                case 6:
                    menuIA();
                    break;
                    
                    
                case 7:
                    salir = true;
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }
    
    
// --- PRODUCTOS ---

    //AGREGAR PRODUCTO
    public void agregarProducto() {
        ProductoOtaku nuevo = vista.leerDatosProductoNuevo();
        if (nuevo != null) {
            dao.agregarProducto(nuevo);
            vista.mostrarMensaje("Producto añadido con éxito.");
        }
    }
    
    
    //CONSULTAR UN PRODUCTO POR ID
    public void consultarProductoPorId() {
        int id = vista.leerIdProducto();
        if (id != -1) {
            ProductoOtaku producto = dao.obtenerProductoPorId(id);
            vista.mostrarProducto(producto);
        }
    }
    
    
    //ACTUALIZAR UN PRODUCTO
    public void actualizarProducto() {
        int id = vista.leerIdProducto(); 

        if (id != -1) {
            ProductoOtaku producto = dao.obtenerProductoPorId(id); 
            if (producto != null) {
                vista.actualizarProducto(producto); 
                boolean actualizadoOK = dao.actualizarProducto(producto); 

                if (actualizadoOK) {
                    vista.mostrarMensaje("Producto actualizado, inventario actualizado:");
                    vista.mostrarListaProductos(dao.obtenerTodosLosProductos());
                } else {
                    vista.mostrarMensaje("No se pudo actualizar.");
                }
            } else {
                vista.mostrarMensaje("Producto no encontrado.");
            }
        }
    }
    
    
    //ELIMINAR PRODUCTO
    public void eliminarProducto() {
    int id = vista.leerIdProducto();
    if (id != -1) {
        boolean eliminado = dao.eliminarProducto(id);
        if (eliminado) {
            vista.mostrarMensaje("Producto eliminado.");
        } else {
            vista.mostrarMensaje("No se encontró ningún producto con ese ID.");
        }
    } else {
        vista.mostrarMensaje("ID inválido. No se pudo eliminar.");
    }
    }
    
// --- CLIENTES ---
    
    //MENU CLIENTES
    public void menuClientes() {
        boolean salir = false;
        while (!salir) {
            int opcion = vista.mostrarMenuClientes();
            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    consultarClientePorId();
                    break;
                case 3:
                	vista.mostrarListaClientes(clienteDAO.obtenerTodosLosClientes());
                    break;
                case 4:
                    actualizarCliente();
                    break;
                case 5:
                    eliminarCliente();
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }
    
    private void agregarCliente() {
        ClienteOtaku nuevo = vista.leerDatosClienteNuevo();
        if (nuevo != null) {
            clienteDAO.agregarCliente(nuevo);
            vista.mostrarMensaje("Cliente añadido correctamente.");
        }
    }

    private void consultarClientePorId() {
        int id = vista.leerIdCliente();
        ClienteOtaku cliente = clienteDAO.obtenerClientePorId(id);
        vista.mostrarCliente(cliente);
    }

    private void actualizarCliente() {
        int idActualizar = vista.leerIdCliente();
        ClienteOtaku existente = clienteDAO.obtenerClientePorId(idActualizar);
        if (existente != null) {
            ClienteOtaku actualizado = vista.leerDatosClienteNuevo();
            if (actualizado != null) {
                actualizado.setId(idActualizar);
                clienteDAO.actualizarCliente(actualizado);
                vista.mostrarMensaje("Cliente actualizado.");
            }
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void eliminarCliente() {
        int idEliminar = vista.leerIdCliente();
        boolean eliminado = clienteDAO.eliminarCliente(idEliminar);
        if (eliminado) {
            vista.mostrarMensaje("Cliente eliminado.");
        } else {
            vista.mostrarMensaje("No se pudo eliminar.");
        }
    }

    
// --- INTELIGENCIA ARTIFICIAL ---
    public void menuIA() {
        boolean salir = false;

        while (!salir) {
            int opcionIA = vista.mostrarMenuIA();

            switch (opcionIA) {
                case 1: // DESCRIPCION
                    int id = vista.leerIdProducto();
                    if (id != -1) {
                        ProductoOtaku producto = dao.obtenerProductoPorId(id);
                        if (producto != null) {
                            String prompt = "Genera una descripción muy breve (en español) de marketing breve y atractiva para el producto otaku: " +
                                    producto.getNombre() + " de la categoría " + producto.getCategoria() + ".";
                            String resultado = LlmService.consultaIA(prompt);
                            vista.mostrarMensaje("Descripción generada:\n" + resultado);
                        } else {
                            vista.mostrarMensaje("Producto no encontrado.");
                        }
                    }
                    break;

                case 2: // SUGERIR CATEGORIA
                    String nombre = vista.leerNombreParaCategoria();
                    String prompt = "Responde (en español) únicamente con una de estas categorías para el producto '" + nombre + "': Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
                    String resultado = LlmService.consultaIA(prompt);
                    vista.mostrarMensaje("Categoría sugerida por la IA: " + resultado);
                    break;

                case 3:
                    salir = true;
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }
    }
}