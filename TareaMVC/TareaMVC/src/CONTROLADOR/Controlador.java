package CONTROLADOR;

import MODELO.Modelo;
import VISTA.Formulario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import CONTROLADOR.Validaciones;

public class Controlador {

    private Modelo modelo;
    private Formulario formulario;

    public Controlador(Modelo modelo, Formulario formulario) {
        this.modelo = modelo;
        this.formulario = formulario;
        CargarEstudiantesEnTabla();

        this.formulario.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedulaBuscar = formulario.getTxtCedulaEdit().getText().trim();
                ArrayList<Modelo> listaActualizada = Modelo.getListaEstudiantes(); // Recarga lista

                Modelo estudianteEncontrado = buscarEstudiantePorCedula(listaActualizada, cedulaBuscar);
                if (estudianteEncontrado != null) {
                    cargarDatosEstudiante(estudianteEncontrado);
                } else {
                    JOptionPane.showMessageDialog(formulario, "Estudiante no encontrado.");
                }
            }
        });
        ActionListener cerrarApp = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        formulario,
                        "¿Estás seguro de que deseas salir?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        };

        this.formulario.btnSalir.addActionListener(cerrarApp);
        this.formulario.btnSalir1.addActionListener(cerrarApp);

        this.formulario.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Seguro que quieres eliminar al estudiante?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    String cedula = formulario.getTxtCedulaEdit().getText().trim();
                    if (!cedula.isEmpty()) {
                        boolean eliminado = modelo.eliminar(cedula);
                        if (eliminado) {
                            CargarEstudiantesEnTabla(); 
                            formulario.getTxtCedulaEdit().setText(""); 
                                 limpiarCamposEdit();
                        }
                    }
                }
            }
        });

        this.formulario.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = formulario.getTxtNombre().getText();
                String curso = formulario.getTxtCurso().getText();
                String carrera = formulario.getComboBoxCarrera().getSelectedItem().toString();
                String sexo = formulario.getSelectedSexo();
                String cedula = formulario.getTxtCedula().getText();
                String correo = formulario.getTxtCorreo().getText();

                // Crear instancia de validaciones
                Validaciones val = new Validaciones();

                // Validar cédula
                if (!val.validarCedula(cedula)) {
                    JOptionPane.showMessageDialog(formulario, "❌ Cédula ecuatoriana inválida", "Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                // Validar correo
                if (!val.validarCorreo(correo)) {
                    JOptionPane.showMessageDialog(formulario, "❌ Correo electrónico inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Detener ejecución
                }

                // Si pasa validaciones, enviar al modelo
                modelo.setNombre(nombre);
                modelo.setCurso(curso);
                modelo.setCarrera(carrera);
                modelo.setSexo(sexo);
                modelo.setCedula(cedula);
                modelo.setCorreo(correo);

                modelo.guardar();
                CargarEstudiantesEnTabla();
                limpiarCamposEdit();

                JOptionPane.showMessageDialog(formulario, "✅ Datos guardados correctamente");
            }
        });

        this.formulario.btnGuardarEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene los datos del formulario
                String nombre = formulario.getTxtNombreEdit().getText();
                String curso = formulario.getTxtCursoEdit().getText();
                String carrera = formulario.getComboBoxCarreraEdit().getSelectedItem().toString();
                String sexo = formulario.getSelectedSexoEdit();
                String cedula = formulario.getTxtCedulaEdit().getText(); // Aquí asumes que la cédula no cambia
                String correo = formulario.getTxtCorreoEdit().getText();

                // Establece los valores en el modelo
                modelo.setNombre(nombre);
                modelo.setCurso(curso);
                modelo.setCarrera(carrera);
                modelo.setSexo(sexo);
                modelo.setCedula(cedula); // Cédula como clave para editar
                modelo.setCorreo(correo);

                // Llama al método editar() en el modelo
                modelo.editar();  // Método de actualización en la base de datos

                // Limpia los campos y recarga la tabla de estudiantes
                limpiarCamposEdit();
                CargarEstudiantesEnTabla(); // Recarga la tabla con los datos actualizados

                // Muestra un mensaje de éxito
                JOptionPane.showMessageDialog(formulario, "Estudiante actualizado correctamente");
            }
        });
    }

    private Modelo buscarEstudiantePorCedula(ArrayList<Modelo> listaEstudiantes, String cedula) {
        cedula = cedula.trim();

        System.out.println("Lista de estudiantes actualizada:");
        for (Modelo estudiante : listaEstudiantes) {  // ✅ Ahora usa la lista pasada como argumento
            System.out.println("Nombre: " + estudiante.getNombre() + " | Cédula: " + estudiante.getCedula());
        }

        // 🔍 Búsqueda del estudiante
        for (Modelo estudiante : listaEstudiantes) {  // ✅ Usa la lista correcta
            if (estudiante.getCedula() != null && estudiante.getCedula().trim().equalsIgnoreCase(cedula.trim())) {
                return estudiante;  // ✅ Retorna el estudiante si la cédula coincide
            }
        }

        return null;  // ❌ Retorna null si no se encuentra el estudiante
    }

    private void cargarDatosEstudiante(Modelo estudiante) {
        formulario.getTxtNombreEdit().setText(estudiante.getNombre());
        formulario.getTxtCursoEdit().setText(estudiante.getCurso());
        formulario.getComboBoxCarreraEdit().setSelectedItem(estudiante.getCarrera());

        if (estudiante.getSexo().equals("Masculino")) {
            formulario.RadioButtonMasculinoEdit.setSelected(true);
        } else if (estudiante.getSexo().equals("Femenino")) {
            formulario.RadioButtonFemeninoEdit.setSelected(true);
        }

        formulario.getTxtCorreoEdit().setText(estudiante.getCorreo());
        formulario.getTxtCedulaEdit().setText(estudiante.getCedula());
    }

    private void limpiarCampos() {
        formulario.getTxtNombre().setText("");
        formulario.getTxtCurso().setText("");
        formulario.getTxtCedula().setText("");
        formulario.getTxtCorreo().setText("");
        formulario.getGrupoSexo().clearSelection();
        formulario.getComboBoxCarrera().setSelectedIndex(0);
    }

    private void limpiarCamposEdit() {
        formulario.getTxtNombreEdit().setText("");
        formulario.getTxtCursoEdit().setText("");
        formulario.getTxtCedulaEdit().setText("");
        formulario.getTxtCorreoEdit().setText("");
        formulario.getGrupoSexoEdit().clearSelection();
        formulario.getComboBoxCarreraEdit().setSelectedIndex(0);
    }

    public void CargarEstudiantesEnTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) formulario.getTableReportes().getModel();
        modeloTabla.setRowCount(0); // ✅ Limpiar la tabla antes de llenarla

        for (Modelo estudiante : Modelo.getListaEstudiantes()) {
            Object[] fila = {
                estudiante.getNombre(),
                estudiante.getCurso(),
                estudiante.getCedula(),
                estudiante.getCorreo(),
                estudiante.getCarrera(),
                estudiante.getSexo()
            };

            modeloTabla.addRow(fila); // ✅ Agregar cada fila con los datos

            // 🔄 **Forzar actualización después de agregar cada fila**
            modeloTabla.fireTableDataChanged();
            formulario.getTableReportes().revalidate();
            formulario.getTableReportes().repaint();
        }

    }

}
