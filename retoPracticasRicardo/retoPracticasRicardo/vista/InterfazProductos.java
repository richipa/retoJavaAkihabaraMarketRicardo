package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.ProductoOtaku;
import modelo.ProductoDAO;

public class InterfazProductos {

    // MOSTRAR VENTANA PARA GESTIÓN DE Productos
    public static void mostrarVentanaProductos() {

        JFrame ventana = new JFrame("Gestión de Productos");
        ventana.setSize(600, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        ProductoDAO dao = new ProductoDAO();

        // PANEL FORMULARIO PRODUCTOS
        JPanel panelFormulario = new JPanel(new GridLayout(6, 3));
        JTextField txtNombre = new JTextField();
        JTextField txtCategoria = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Categoria:"));
        panelFormulario.add(txtCategoria);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(new JTextField());
        
        //BOTON AGREGAR
        JButton btnAgregar = new JButton("Agregar Producto");
        panelFormulario.add(btnAgregar);
        
        //BOTON CONSULTAR
        JButton btnConsultar = new JButton("Consultar Producto por ID");
        panelFormulario.add(btnConsultar);
        
        //BOTON ACTUALIZAR
        JButton btnActualizar = new JButton("Actualizar Producto");
        panelFormulario.add(btnActualizar);
        
        //BOTON ELIMINAR
        JButton btnEliminar = new JButton("Eliminar Producto");
        panelFormulario.add(btnEliminar);

        ventana.add(panelFormulario, BorderLayout.NORTH);
        
        //BOTON VOLVER AL MENU PRINCIPAL
        JButton btnVolver = new JButton("Volver al menú principal");
        btnVolver.addActionListener(e -> ventana.dispose());
        ventana.add(btnVolver, BorderLayout.SOUTH);


        // TABLA PRODUCTOS
        String[] columnas = {"ID", "Nombre", "Categoria", "Precio", "Stock"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        ventana.add(new JScrollPane(tabla), BorderLayout.CENTER);

        //SE RECARGA LA TABLA
        Runnable cargarProductos = () -> {
            modeloTabla.setRowCount(0);
            for (ProductoOtaku p : dao.obtenerTodosLosProductos()) {
                modeloTabla.addRow(new Object[]{
                        p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()
                });
            }
        };
        cargarProductos.run();

        // ACCIÓN BOTÓN AGREGAR PRODUCTO
        btnAgregar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String categoria = txtCategoria.getText().trim();
                String precio = txtPrecio.getText().trim();
                String stock = txtStock.getText().trim();

                if (nombre.isEmpty() || categoria.isEmpty() || precio.isEmpty() || stock.isEmpty()) {
                    JOptionPane.showMessageDialog(ventana, "Todos los campos deben estar completos.");
                    return;
                }
                
                //PARSEO A DOUBLE E INTEGER
                double precioParseado = Double.parseDouble(precio);
                int stockParseado = Integer.parseInt(stock);
                
                ProductoOtaku nuevo = new ProductoOtaku(nombre, categoria, precioParseado, stockParseado);
                dao.agregarProducto(nuevo);
                JOptionPane.showMessageDialog(ventana, "Producto añadido correctamente.");

                txtNombre.setText("");
                txtCategoria.setText("");
                txtPrecio.setText("");
                txtStock.setText("");
                cargarProductos.run();

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(ventana, "Error al agregar Producto: " + e1.getMessage());
            }
        });

        // ACCIÓN BOTÓN CONSULTAR Producto POR ID
        btnConsultar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "Introduce el ID del Producto:");
            try {
                int id = Integer.parseInt(input);
                ProductoOtaku Producto = dao.obtenerProductoPorId(id);
                if (Producto != null) {
                    JOptionPane.showMessageDialog(ventana,
                            "ID: " + Producto.getId() + "\n" +
                            "Nombre: " + Producto.getNombre() + "\n" +
                            "Categoria: " + Producto.getCategoria() + "\n" +
                            "Precio: " + Producto.getPrecio() + "\n" +
                            "Teléfono: " + Producto.getStock()
                            );
                } else {
                    JOptionPane.showMessageDialog(ventana, "Producto no encontrado.");
                }
            } catch (NumberFormatException e2) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });

        // ACCIÓN BOTÓN ACTUALIZAR
        btnActualizar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "Introduce el ID del Producto a actualizar:");
            try {
                int id = Integer.parseInt(input);
                ProductoOtaku Producto = dao.obtenerProductoPorId(id);

                if (Producto != null) {
                    
                    String nuevoNombre = JOptionPane.showInputDialog(ventana, "Nuevo nombre:", Producto.getNombre());
                    String nuevaCategoria = JOptionPane.showInputDialog(ventana, "Nueva categoria:", Producto.getCategoria());
                    String nuevoPrecio = JOptionPane.showInputDialog(ventana, "Nuevo precio:", Producto.getPrecio());
                    String nuevoStock = JOptionPane.showInputDialog(ventana, "Nuevo stock:", Producto.getPrecio());
                    
                    //PARSEO A DOUBLE E INTEGER 
                    double precio = Double.parseDouble(nuevoPrecio);
                    int stock = Integer.parseInt(nuevoStock);
                    
                    // ACTUALIZAR EL OBJETO PRODUCTO
                    Producto.setNombre(nuevoNombre);
                    Producto.setCategoria(nuevaCategoria);
                    Producto.setPrecio(precio);
                    Producto.setStock(stock);

                    boolean actualizado = dao.actualizarProducto(Producto);
                    if (actualizado == true) {
                        JOptionPane.showMessageDialog(ventana, "Producto actualizado correctamente.");
                        cargarProductos.run();
                    } else {
                        JOptionPane.showMessageDialog(ventana, "No se pudo actualizar el Producto.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ventana, "Producto no encontrado.");
                }
            } catch (NumberFormatException e3) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });
        
        // ACCIÓN BOTÓN ELIMINAR
        btnEliminar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "Introduce el ID del Producto a eliminar:");
            try {
                int id = Integer.parseInt(input);
                ProductoOtaku Producto = dao.obtenerProductoPorId(id);
                if (Producto != null) {
                	boolean eliminado = dao.eliminarProducto(id);
                    if (eliminado == true) {
                        JOptionPane.showMessageDialog(ventana, "Producto eliminado.");
                        cargarProductos.run();
                    } else {
                        JOptionPane.showMessageDialog(ventana, "No se pudo eliminar el Producto.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ventana, "Producto no encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID no válido.");
            }
        });

        
        ventana.setVisible(true);
    }
}


