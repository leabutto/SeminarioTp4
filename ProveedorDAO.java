import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    private Connection conexion;

    public ProveedorDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarProveedor(Proveedor proveedor) throws SQLException {
        String sql = "INSERT INTO proveedor (nombre, telefono, direccion, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, proveedor.getNombre());
            statement.setInt(2, proveedor.getTelefono());
            statement.setString(3, proveedor.getDireccion());
            statement.setString(4, proveedor.getEmail());
            statement.executeUpdate();
        }
    }

    public List<Proveedor> listarProveedores() throws SQLException {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";
        try (Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Proveedor proveedor = new Proveedor(
                        resultSet.getInt("idProveedor"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("telefono"),
                        resultSet.getString("direccion"),
                        resultSet.getString("email")
                );
                proveedores.add(proveedor);
            }
        }
        return proveedores;
    }

}