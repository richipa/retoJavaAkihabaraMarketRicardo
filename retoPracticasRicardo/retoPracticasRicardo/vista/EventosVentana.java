package vista;

public class EventosVentana {

    // ASIGNAR EVENTOS A LOS BOTONES DE VENTANA PRINCIPAL
    public static void asignarEventos(VentanaGrafica ventana) {

        // BOTÓN PRODUCTOS
        ventana.btnGestionarProductos.addActionListener(e -> {
           InterfazProductos.mostrarVentanaProductos();
        });

        // BOTÓN CLIENTES
        ventana.btnGestionarClientes.addActionListener(e -> {
            InterfazClientes.mostrarVentanaClientes();
        });

        // BOTÓN SALIR
        ventana.btnSalir.addActionListener(e -> ventana.dispose());//DISPOSE CIERRA LA VENTANA PERO NO LA APLICACIÓN ENTERA
    }
}
