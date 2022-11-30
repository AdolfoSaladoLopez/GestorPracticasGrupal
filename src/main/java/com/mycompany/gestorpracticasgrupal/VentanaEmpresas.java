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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VentanaEmpresas implements Initializable {

    @FXML
    private Label lblNombreEmpresa;
    @FXML
    private Label lblResponsable;
    @FXML
    private Label lblCorreo;
    @FXML
    private Label lblTelefono;
    @FXML
    private TableView<Empresa> tablaEmpresas;
    @FXML
    private TableColumn<Empresa, String> tcNombreEmpresa;
    @FXML
    private TableView<Alumno> tablaAlumnosAsociados;
    @FXML
    private TableColumn<Empresa, String> tcNombreAlumno;
    @FXML
    private TableColumn<Empresa, String> tcApellidos;
    @FXML
    private TableColumn<Empresa, String> tcCorreoAlumno;

    EmpresaDAO gestorEmpresas = new EmpresaDAOHib();
    AlumnoDAO gestorAlumnos = new AlumnoDAOHib();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* Celdas tabla Empresa */
        tcNombreEmpresa.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        /* Celdas tabla Alumnos */
        tcNombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        tcCorreoAlumno.setCellValueFactory(new PropertyValueFactory<>("correo"));
        actualizarTablaEmpresas();
    }

    @FXML
    private void btnGestionarAlumnos(ActionEvent event) {
        try {
            App.setRoot("ventana-profesor-alumnos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnCerrarSesion(ActionEvent event) {
        try {
            App.setRoot("inicio");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnInformacionSoftware(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Acerca de...");
        alert.setContentText("Software realizado por Adolfo Salado López.");
        alert.showAndWait();
    }

    private void actualizarTablaEmpresas() {
        var listadoEmpresas = new ArrayList<Empresa>(gestorEmpresas.obtenerListadoEmpresas());
        ObservableList<Empresa> datosEmpresa = FXCollections.observableArrayList();
        datosEmpresa.addAll(listadoEmpresas);

        tablaEmpresas.getItems().clear();
        tablaEmpresas.getItems().addAll(datosEmpresa);
    }

    @FXML
    private void empresaSeleccionada(MouseEvent event) {
        Empresa empresaSeleccionada = new Empresa();

        if (tablaEmpresas.getSelectionModel().getSelectedItem() != null) {
            empresaSeleccionada = tablaEmpresas.getSelectionModel().getSelectedItem();

            ObservableList<Alumno> alumnosEmpresa = FXCollections.observableArrayList();
            alumnosEmpresa.addAll(empresaSeleccionada.getAlumnos());

            tablaAlumnosAsociados.getItems().clear();
            tablaAlumnosAsociados.getItems().addAll(alumnosEmpresa);

            SessionData.setEmpresa(tablaEmpresas.getSelectionModel().getSelectedItem());

            lblNombreEmpresa.setText(empresaSeleccionada.getNombre());
            lblResponsable.setText(empresaSeleccionada.getResponsable());
            lblCorreo.setText(empresaSeleccionada.getCorreo());
            lblTelefono.setText(empresaSeleccionada.getTelefono());

        }
    }

    @FXML
    private void btnAñadir(ActionEvent event) {
        try {
            App.setRoot("crear-empresa");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        try {
            App.setRoot("modificar-empresa");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void establecerEtiquetasSinAsignacion() {
        lblNombreEmpresa.setText("Sin asignación");
        lblResponsable.setText("Sin asignación");
        lblCorreo.setText("Sin asignación");
        lblTelefono.setText("Sin asignación");
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        if (tablaEmpresas.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Eliminación del alumno");
            alert.setTitle("Confirmación");
            alert.setContentText("¿Estas seguro de confirmar la acción?");
            Optional<ButtonType> action = alert.showAndWait();
            // Si hemos pulsado en aceptar
            if (action.get() == ButtonType.OK) {
                var array = tablaEmpresas.getSelectionModel().getSelectedItem().getAlumnos();
                for (Alumno a : array) {
                    a.setEmpresa(gestorEmpresas.obtenerEmpresaId(4));
                    gestorAlumnos.modificarAlumno(a);
                }
                gestorEmpresas.eliminarEmpresa(tablaEmpresas.getSelectionModel().getSelectedItem());
                establecerEtiquetasSinAsignacion();

                actualizarTablaEmpresas();

            }
        }
    }


}
