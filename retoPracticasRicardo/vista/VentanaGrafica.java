package vista;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatDarculaLaf; 
public class VentanaGrafica extends JFrame {

    public JButton btnGestionarProductos;
    public JButton btnGestionarClientes;
    public JButton btnSalir;

    public VentanaGrafica() {
    	
        //PRUEBO A USAR FLATLAF
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            System.out.println("Error al cargar FlatLaf");
        }

        // CONFIGURACIÓN DE LA VENTANA PRINCIPAL
        getContentPane().setBackground(Color.DARK_GRAY); 
        setTitle("Gestor de Akihabara Market");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));


        // INICIALIZACIÓN DE BOTONES
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        
        //BOTON PRODUCTOS Y TAMAÑO
        btnGestionarProductos = new JButton("Gestionar Productos");
        btnGestionarProductos.setPreferredSize(new Dimension(200, 200));
        
        
        //BOTON CLIENTES Y TAMAÑO
        btnGestionarClientes = new JButton("Gestionar Clientes");
        btnGestionarClientes.setPreferredSize(new Dimension(200, 200));
        
        
        //BOTON SALIR Y TAMAÑO Y COLOR
        btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(420, 50));
        btnSalir.setBackground(Color.red);

        // AÑADIR BOTONES A LA VENTANA
        add(btnGestionarProductos);
        add(btnGestionarClientes);
        add(btnSalir);

        // VINCULAR EVENTOS A LOS BOTONES
        EventosVentana.asignarEventos(this);
        setVisible(true);
        


    }
}

