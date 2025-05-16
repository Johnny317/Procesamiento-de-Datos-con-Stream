 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package CONTROLADOR;

/**
 *
 * @author Jhony Espinoza
 */
public class Validaciones {

    public boolean validarCedula(String cedula) {
        if (cedula == null || cedula.length() != 10) {
            return false;
        }

        try {
            int provincia = Integer.parseInt(cedula.substring(0, 2));
            if (provincia < 1 || provincia > 24) {
                return false;
            }

            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            int suma = 0;

            for (int i = 0; i < 9; i++) {
                int digito = Character.getNumericValue(cedula.charAt(i));
                int resultado = digito * coeficientes[i];
                if (resultado >= 10) {
                    resultado -= 9;
                }
                suma += resultado;
            }

            int ultimoDigito = Character.getNumericValue(cedula.charAt(9));
            int decenaSuperior = ((suma + 9) / 10) * 10;
            int digitoVerificador = decenaSuperior - suma;

            if (digitoVerificador == 10) {
                digitoVerificador = 0;
            }

            return digitoVerificador == ultimoDigito;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
public boolean validarCorreo(String correo) {
    if (correo == null) return false;

    return correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
}

}
