import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductoDAO {
    private Connection connection;

    public ProductoDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertarProducto(Producto producto) throws SQLException {
        String sql = "INSERT INTO producto (nombre, descripcion, cantidad, precioCompra, precioVenta) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setInt(3, producto.getCantidad());
            statement.setFloat(4, producto.getPrecioCompra());
            statement.setFloat(5, producto.getPrecioVenta());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Producto agregado correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            throw e;
        }
    }

    public List<Producto> listarProductos() {
        return List.of();
    }


}
