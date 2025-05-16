package tareamvc;

import MODELO.Modelo;
import VISTA.Formulario;
import CONTROLADOR.Controlador;
import Conexion.Conexion;

public class TareaMVC {
public static void main(String[] args) {
    Modelo modelo = new Modelo();
    Formulario formulario = new Formulario();
    Controlador controlador = new Controlador(modelo, formulario);
    Conexion conexion = new Conexion();

    // Verificar conexión a la base de datos
    if (conexion.getConexion() != null) {
        System.out.println("Conexión a la base de datos exitosa.");
    } else {
        System.out.println("Error al conectar con la base de datos.");
    }

    formulario.setVisible(true);
}

}
