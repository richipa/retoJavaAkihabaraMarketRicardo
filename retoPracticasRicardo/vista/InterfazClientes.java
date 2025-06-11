package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.ClienteOtaku;
import modelo.ClienteDAO;

public class InterfazClientes {

    // MOSTRAR VENTANA PARA GESTIÓN DE CLIENTES
    public static void mostrarVentanaClientes() {

        JFrame ventana = new JFrame("Gestión de Clientes");
        ventana.setSize(600, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        ClienteDAO dao = new ClienteDAO();

        // PANEL FORMULARIO CLIENTES
        JPanel panelFormulario = new JPanel(new GridLayout(6, 3));
        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtTelefono = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);
        
        //BOTON AGREGAR
        JButton btnAgregar = new JButton("Agregar cliente");
        panelFormulario.add(btnAgregar);
        
        //BOTON CONSULTAR
        JButton btnConsultar = new JButton("Consultar cliente por ID");
        panelFormulario.add(btnConsultar);
        
        //BOTON ACTUALIZAR
        JButton btnActualizar = new JButton("Actualizar cliente");
        panelFormulario.add(btnActualizar);
        
        //BOTON ELIMINAR
        JButton btnEliminar = new JButton("Eliminar cliente");
        panelFormulario.add(btnEliminar);

        ventana.add(panelFormulario, BorderLayout.NORTH);
        
        //BOTON VOLVER AL MENU PRINCIPAL
        JButton btnVolver = new JButton("Volver al menú principal");
        btnVolver.addActionListener(e -> ventana.dispose());
        ventana.add(btnVolver, BorderLayout.SOUTH);

        // TABLA CLIENTES
        String[] columnas = {"ID", "Nombre", "Correo", "Teléfono"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        ventana.add(new JScrollPane(tabla), BorderLayout.CENTER);

        //SE RECARGA LA TABLA
        Runnable cargarClientes = () -> {
            modeloTabla.setRowCount(0);
            for (ClienteOtaku c : dao.obtenerTodosLosClientes()) {
                modeloTabla.addRow(new Object[]{
                        c.getId(), c.getNombre(), c.getEmail(), c.getTelefono()
                });
            }
        };
        cargarClientes.run();

        // ACCIÓN BOTÓN AGREGAR CLIENTE
        btnAgregar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String correo = txtCorreo.getText().trim();
                String telefono = txtTelefono.getText().trim();

                if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(ventana, "Todos los campos deben estar completos.");
                    return;
                }

                ClienteOtaku nuevo = new ClienteOtaku(nombre, correo, telefono);
                dao.agregarCliente(nuevo);
                JOptionPane.showMessageDialog(ventana, "Cliente añadido correctamente.");

                txtNombre.setText("");
                txtCorreo.setText("");
                txtTelefono.setText("");
                cargarClientes.run();

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(ventana, "Error al agregar cliente: " + e1.getMessage());
            }
        });

        // ACCIÓN BOTÓN CONSULTAR CLIENTE POR ID
        btnConsultar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "Introduce el ID del cliente:");
            try {
                int id = Integer.parseInt(input);
                ClienteOtaku cliente = dao.obtenerClientePorId(id);
                if (cliente != null) {
                    JOptionPane.showMessageDialog(ventana,
                            "ID: " + cliente.getId() + "\n" +
                            "Nombre: " + cliente.getNombre() + "\n" +
                            "Correo: " + cliente.getEmail() + "\n" +
                            "Teléfono: " + cliente.getTelefono());
                } else {
                    JOptionPane.showMessageDialog(ventana, "Cliente no encontrado.");
                }
            } catch (NumberFormatException e2) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });

        // ACCIÓN BOTÓN ACTUALIZAR
        btnActualizar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "Introduce el ID del cliente a actualizar:");
            try {
                int id = Integer.parseInt(input);
                ClienteOtaku cliente = dao.obtenerClientePorId(id);

                if (cliente != null) {
                    
                    String nuevoNombre = JOptionPane.showInputDialog(ventana, "Nuevo nombre:", cliente.getNombre());
                    String nuevoCorreo = JOptionPane.showInputDialog(ventana, "Nuevo correo:", cliente.getEmail());
                    String nuevoTelefono = JOptionPane.showInputDialog(ventana, "Nuevo teléfono:", cliente.getTelefono());

                    //ACTUALIZAR EL OBJETO CLIENTE
                    cliente.setNombre(nuevoNombre);
                    cliente.setEmail(nuevoCorreo);
                    cliente.setTelefono(nuevoTelefono);

                    boolean actualizado = dao.actualizarCliente(cliente);
                    if (actualizado == true) {
                        JOptionPane.showMessageDialog(ventana, "Cliente actualizado correctamente.");
                        cargarClientes.run();
                    } else {
                        JOptionPane.showMessageDialog(ventana, "No se pudo actualizar el cliente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ventana, "Cliente no encontrado.");
                }
            } catch (NumberFormatException e3) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });
        
        // ACCIÓN BOTÓN ELIMINAR
        btnEliminar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "Introduce el ID del cliente a eliminar:");
            try {
                int id = Integer.parseInt(input);
                ClienteOtaku cliente = dao.obtenerClientePorId(id);
                if (cliente != null) {
                	boolean eliminado = dao.eliminarCliente(id);
                    if (eliminado == true) {
                        JOptionPane.showMessageDialog(ventana, "Cliente eliminado.");
                        cargarClientes.run();
                    } else {
                        JOptionPane.showMessageDialog(ventana, "No se pudo eliminar el cliente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ventana, "Cliente no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });

        
        ventana.setVisible(true);
    }
}

