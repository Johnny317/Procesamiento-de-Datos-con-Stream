package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author farez
 */
public class Conexion {
    private static Connection conexion;
    private static final String url = "jdbc:mysql://127.0.0.1:3306/bdd_Notas";
    private static final String usuario = "root";
    private static final String contraseña = "1234";

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {  // ✅ Si está cerrada, vuelve a abrirla
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(url, usuario, contraseña);
                System.out.println("🔗 Conexión establecida.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
        }
        return conexion;
    }
}

