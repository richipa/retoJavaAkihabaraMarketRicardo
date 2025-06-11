package modelo;
import java.sql.*;
import java.util.*;

public class ProductoDAO {
	Connection conexion;
	
	
    public void agregarProducto(ProductoOtaku producto) {
        String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR al añadir un producto" + e.getMessage());;
        }
    }
    
    
    public ProductoOtaku obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener este producto" + e.getMessage());;
        }
        return null;
    }
    
    
    public List<ProductoOtaku> obtenerTodosLosProductos(){
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener todos los productos" + e.getMessage());;
        }
		return productos;
    	
    }
    
    public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar este producto" + e.getMessage());;
            return false;
        }
    }
    
    
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
        	System.out.println("Error al eliminar este producto" + e.getMessage());;
            return false;
        }
    }
}
