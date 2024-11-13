import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBd {

    public static Connection conectarBD() {
        Connection conexion = null;
        String url = "jdbc:mysql://localhost:3306/holapacha";  // Incluye el nombre de la BD aquí
        String user = "root";
        String pass = null;

        System.out.println("Conectando...");

        try {
            conexion = DriverManager.getConnection(url, user, pass);
            conexion.setAutoCommit(false);
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            System.out.println("Conexion fallida: " + e.getMessage());
            e.printStackTrace();
        }

        return conexion;
    }
}

