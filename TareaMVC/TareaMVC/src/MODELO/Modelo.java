package MODELO;

import Conexion.Conexion; // Asegúrate de tener este import
import java.sql.*;
import java.util.ArrayList;

public class Modelo {

    private String nombre;
    private String curso;
    private String carrera;
    private String sexo;
    private String cedula;
    private String correo;

    private static ArrayList<Modelo> listaEstudiantes = new ArrayList<>();

    public Modelo() {
    }

    // Constructor
    public Modelo(String nombre, String curso, String carrera, String sexo, String cedula, String correo) {
        this.nombre = nombre;
        this.curso = curso;
        this.carrera = carrera;
        this.sexo = sexo;
        this.cedula = (cedula != null) ? cedula : "Sin cédula";  // ✅ Evita que sea null
        this.correo = correo;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSexo() {
        return sexo;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void guardar() {
        Connection conn = Conexion.getConexion();
        String sql = "INSERT INTO estudiantespv (nombre, curso, carrera, sexo, cedula, correo) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, curso);
            ps.setString(3, carrera);
            ps.setString(4, sexo);
            ps.setString(5, cedula != null ? cedula : "");  // 🚀 Asegurar que no sea NULL
            ps.setString(6, correo);
            ps.executeUpdate();
            System.out.println("Estudiante guardado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public boolean eliminar(String cedula) {
        Connection conn = Conexion.getConexion();
        String sql = "DELETE FROM estudiantespv WHERE cedula = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("✅ Estudiante eliminado correctamente.");
                return true;
            } else {
                System.out.println("❌ No se encontró un estudiante con esa cédula.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }

    // Método para editar un estudiante en la base de datos
    public void editar() {
        Conexion conexionBD = new Conexion();
        Connection conn = conexionBD.getConexion();

        if (conn != null) {
            String sql = "UPDATE estudiantespv SET nombre = ?, curso = ?, carrera = ?, sexo = ?, correo = ? WHERE cedula = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Establecer los parámetros para la consulta
                ps.setString(1, this.nombre);
                ps.setString(2, this.curso);
                ps.setString(3, this.carrera);
                ps.setString(4, this.sexo);
                ps.setString(5, this.correo);
                ps.setString(6, cedula != null ? cedula : ""); // ✅ Evita que se guarde como NULL

                // Ejecutar la actualización
                int filasActualizadas = ps.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Estudiante actualizado en MySQL correctamente.");
                } else {
                    System.out.println("No se encontró el estudiante con la cédula proporcionada.");
                }
            } catch (SQLException e) {
                System.out.println("Error al actualizar en MySQL: " + e.getMessage());
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();  // Cerrar la conexión después de usarla
                    }
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    // Método para obtener la lista de estudiantes desde la base de datos
    public static ArrayList<Modelo> getListaEstudiantes() {
        ArrayList<Modelo> lista = new ArrayList<>();
        Connection conn = Conexion.getConexion();

        if (conn != null) {
            String sql = "SELECT nombre, curso, carrera, sexo, cedula, correo FROM estudiantespv";

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String curso = rs.getString("curso");
                    String carrera = rs.getString("carrera");
                    String sexo = rs.getString("sexo");
                    String correo = rs.getString("correo");
                    String cedula = rs.getObject("cedula") != null ? rs.getString("cedula") : "Sin cédula";

                    // 🔎 Imprimir datos recuperados desde MySQL antes de agregarlos a la lista
                    System.out.println("🔎 Recuperando estudiante: " + nombre + " | Curso: " + curso + " | Cédula desde BD: " + cedula);

                    Modelo estudiante = new Modelo(nombre, curso, carrera, sexo, cedula != null ? cedula : "Sin cédula", correo);

                    lista.add(estudiante);
                }

            } catch (SQLException e) {
                System.out.println("Error al cargar los estudiantes desde MySQL: " + e.getMessage());
            }
        }
        return lista;
    }

}
