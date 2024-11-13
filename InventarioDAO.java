import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {
    private final Connection conexion;

    public InventarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarInventario(Inventario inventario) throws SQLException {
        String sql = "INSERT INTO inventario (idProducto, cantidad) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, inventario.getProducto().getIdProducto());
            stmt.setInt(2, inventario.getCantidad());
            stmt.executeUpdate();
        }
    }

    public List<Inventario> listarInventarios() throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String sql = "SELECT idInventario, idProducto, cantidad FROM inventario";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idInventario = rs.getInt("idInventario");
                int idProducto = rs.getInt("idProducto");
                int cantidad = rs.getInt("cantidad");


                Producto producto = new Producto(idProducto);
                inventarios.add(new Inventario(idInventario, producto, cantidad));
            }
        }
        return inventarios;
    }
}