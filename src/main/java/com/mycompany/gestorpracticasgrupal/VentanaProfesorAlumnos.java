package com.mycompany.gestorpracticasgrupal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Alumno;
import models.Empresa;
import models.Profesor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VentanaProfesorAlumnos implements Initializable {

    @FXML
    private TableView<Alumno> tableAlumnos;
    @FXML
    private TableColumn<Alumno, String> tcNombre;
    @FXML
    private TableColumn<Alumno, String> tcApellidos;
    @FXML
    private Label lblNombreAlumno;
    @FXML
    private Label lblDniAlumno;
    @FXML
    private Label lblNacimientoAlumno;
    @FXML
    private Label lblCorreoAlumno;
    @FXML
    private Label lblTelefonoAlumno;
    @FXML
    private Label lblNombreEmpresa;
    @FXML
    private Label lblNombreResponsableEmpresa;
    @FXML
    private Label lblCorreoEmpresa;
    @FXML
    private Label lblTelefonoEmpresa;
    @FXML
    private Label lblHorasTotalDual;
    @FXML
    private Label lblHorasRealizadasDual;
    @FXML
    private Label lblHorasTotalFct;
    @FXML
    private Label lblHorasRealizadasFct;

    private AlumnoDAO gestorAlumnos = new AlumnoDAOHib();
    private Profesor profesorSesion = SessionData.getProfesor();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        actualizarTabla();
    }

    private void actualizarTabla() {
        var listadoAlumnos = new ArrayList<>(profesorSesion.getAlumnos());
        ObservableList<Alumno> listaAlumnos = FXCollections.observableArrayList();
        listaAlumnos.addAll(listadoAlumnos);

        tableAlumnos.getItems().clear();
        tableAlumnos.getItems().addAll(listaAlumnos);
    }

    @FXML
    private void btnGestionarEmpresas(ActionEvent event) {
        try {
            App.setRoot("ventana-empresas");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnCerrarSesion(ActionEvent event) {
        try {
            App.setRoot("inicio");
            SessionData.setProfesor(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Acerca de...");
        alert.setContentText("Software realizado por Adolfo Salado López.");
        alert.showAndWait();
    }

    @FXML
    private void alumnoSeleccionado(MouseEvent event) {
        if (tableAlumnos.getSelectionModel().getSelectedItem() != null) {
            Alumno alumnoSeleccionado = tableAlumnos.getSelectionModel().getSelectedItem();
            Empresa empresaAlumno = alumnoSeleccionado.getEmpresa();

            /* DATOS DEL ALUMNO */
            lblNombreAlumno.setText(alumnoSeleccionado.getNombre() + " " + alumnoSeleccionado.getApellidos());
            lblDniAlumno.setText(alumnoSeleccionado.getDni());
            lblNacimientoAlumno.setText(alumnoSeleccionado.getNacimiento().toString().split(" ")[0]);
            lblCorreoAlumno.setText(alumnoSeleccionado.getCorreo());
            lblTelefonoAlumno.setText(alumnoSeleccionado.getTelefono());

            /* DATOS DE LA EMPRESA */
            lblNombreEmpresa.setText(empresaAlumno.getNombre());
            lblNombreResponsableEmpresa.setText(empresaAlumno.getResponsable());
            lblCorreoEmpresa.setText(empresaAlumno.getCorreo());
            lblTelefonoEmpresa.setText(empresaAlumno.getTelefono());

            /* DATOS HORAS PRÁCTICAS */
            if (alumnoSeleccionado.getTotalDual() != 0 && alumnoSeleccionado.getTotalFCT() != 0) {
                lblHorasTotalDual.setText(alumnoSeleccionado.getTotalDual().toString());
                lblHorasRealizadasDual.setText(gestorAlumnos.calcularHorasDual(alumnoSeleccionado).toString().replace("[", "").replace("]", ""));
                lblHorasTotalFct.setText(alumnoSeleccionado.getTotalFCT().toString());
                lblHorasRealizadasFct.setText(gestorAlumnos.calcularHorasFct(alumnoSeleccionado).toString().replace("[", "").replace("]", ""));
            } else if (alumnoSeleccionado.getTotalFCT() != 0 && alumnoSeleccionado.getTotalDual() == 0) {
                lblHorasTotalDual.setText("Sin asignar");
                lblHorasRealizadasDual.setText("Sin asignar");
                lblHorasTotalFct.setText(alumnoSeleccionado.getTotalFCT().toString());
                lblHorasRealizadasFct.setText(gestorAlumnos.calcularHorasFct(alumnoSeleccionado).toString().replace("[", "").replace("]", ""));
            } else if (alumnoSeleccionado.getTotalDual() != 0 && alumnoSeleccionado.getTotalFCT() == 0) {
                lblHorasTotalDual.setText(alumnoSeleccionado.getTotalDual().toString());
                lblHorasRealizadasDual.setText(gestorAlumnos.calcularHorasDual(alumnoSeleccionado).toString().replace("[", "").replace("]", ""));
                lblHorasTotalFct.setText("Sin asignar");
                lblHorasRealizadasFct.setText("Sin asignar");
            } else {
                lblHorasTotalDual.setText("Sin asignar");
                lblHorasRealizadasDual.setText("Sin asignar");
                lblHorasTotalFct.setText("Sin asignar");
                lblHorasRealizadasFct.setText("Sin asignar");
            }
        }
    }

    @FXML
    private void btnAddAlumno(ActionEvent event) {
        try {
            App.setRoot("crear-alumno");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnModificarAlumno(ActionEvent event) {
        if (tableAlumnos.getSelectionModel().getSelectedItem() != null) {
            System.out.println(tableAlumnos.getSelectionModel().getSelectedItem());
            try {
                SessionData.setAlumno(tableAlumnos.getSelectionModel().getSelectedItem());
                SessionData.setProfesor(profesorSesion);
                App.setRoot("modificar-alumno");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void establecerEtiquetasSinAsignacion() {
        /* DATOS DEL ALUMNO */
        lblNombreAlumno.setText("Sin selección");
        lblDniAlumno.setText("Sin selección");
        lblNacimientoAlumno.setText("Sin selección");
        lblCorreoAlumno.setText("Sin selección");
        lblTelefonoAlumno.setText("Sin selección");

        /* DATOS DE LA EMPRESA */
        lblNombreEmpresa.setText("Sin selección");
        lblNombreResponsableEmpresa.setText("Sin selección");
        lblCorreoEmpresa.setText("Sin selección");
        lblTelefonoEmpresa.setText("Sin selección");

        lblHorasTotalDual.setText("Sin selección");
        lblHorasRealizadasDual.setText("Sin selección");
        lblHorasTotalFct.setText("Sin selección");
        lblHorasRealizadasFct.setText("Sin selección");
    }

    @FXML
    private void btnEliminarAlumno(ActionEvent event) {
        if (tableAlumnos.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Eliminación del alumno");
            alert.setTitle("Confirmación");
            alert.setContentText("¿Estas seguro de confirmar la acción?");
            Optional<ButtonType> action = alert.showAndWait();
            // Si hemos pulsado en aceptar
            if (action.get() == ButtonType.OK) {
                gestorAlumnos.eliminarAlumno(tableAlumnos.getSelectionModel().getSelectedItem());
                establecerEtiquetasSinAsignacion();

                if (profesorSesion.getAlumnos().size() > 0) {
                    profesorSesion.getAlumnos().remove(tableAlumnos.getSelectionModel().getSelectedItem());
                    actualizarTabla();
                } else {
                    tableAlumnos.getItems().clear();
                }
            }
        }
    }

    @FXML
    private void btnListadoActividades(ActionEvent event) {
        try {
            SessionData.setAlumno(tableAlumnos.getSelectionModel().getSelectedItem());
            App.setRoot("ventana-actividades-profesor");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
