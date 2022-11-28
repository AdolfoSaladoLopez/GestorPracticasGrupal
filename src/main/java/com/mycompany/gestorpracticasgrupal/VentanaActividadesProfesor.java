package com.mycompany.gestorpracticasgrupal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Actividad;
import models.Alumno;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VentanaActividadesProfesor implements Initializable {

    @FXML
    private Label lblBienvenida;
    @FXML
    private Label lblDnIAlumno;
    @FXML
    private Label lblNacimientoAlumno;
    @FXML
    private Label lblCorreoAlumno;
    @FXML
    private Label lblNumeroAlumno;
    @FXML
    private Label lblEmpresaDual;
    @FXML
    private Label lblCorreoDual;
    @FXML
    private Label lblResponsableDual;
    @FXML
    private Label lblHorasTotalesDual;
    @FXML
    private Label lblHorasRealizadasDual;
    @FXML
    private Label lblHorasTotalesFct;
    @FXML
    private Label lblHorasRealizadasFct;
    @FXML
    private TableView<Actividad> tDual;
    @FXML
    private TableColumn<Actividad, String> tcFechaDual;
    @FXML
    private TableColumn<Actividad, String> tcTipoDual;
    @FXML
    private TableColumn<Actividad, String> tcHorasDual;
    @FXML
    private TableColumn<Actividad, String> tcActividadDual;
    @FXML
    private TableColumn<Actividad, String> tcObservacionesDual;
    @FXML
    private TableView<Actividad> tFct;
    @FXML
    private TableColumn<Actividad, String> tcFecha;
    @FXML
    private TableColumn<Actividad, String> tcTipo;
    @FXML
    private TableColumn<Actividad, String> tcHoras;
    @FXML
    private TableColumn<Actividad, String> tcActividad;
    @FXML
    private TableColumn<Actividad, String> tcObservaciones;

    Alumno alumno = SessionData.getAlumno();
    AlumnoDAO gestorAlumno = new AlumnoDAOHib();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Integer horasTotalDual = alumno.getTotalDual();
        Integer horasTotalFct = alumno.getTotalFCT();

        /* ETIQUETAS ALUMNO */
        lblBienvenida.setText("Bienvenid@, " + SessionData.getProfesor().getNombre() +
                ". Estos son las actividades de " + SessionData.getAlumno().getNombre() + " " + SessionData.getAlumno().getApellidos());
        lblDnIAlumno.setText(SessionData.getAlumno().getDni());
        lblNacimientoAlumno.setText(SessionData.getAlumno().getNacimiento().toString().split(" ")[0]);
        lblCorreoAlumno.setText(SessionData.getAlumno().getCorreo());
        lblNumeroAlumno.setText(SessionData.getAlumno().getTelefono());



        /* ETIQUETAS EMPRESA DUAL */
        lblEmpresaDual.setText(SessionData.getAlumno().getEmpresa().getNombre());
        lblCorreoDual.setText(SessionData.getAlumno().getEmpresa().getCorreo());
        lblResponsableDual.setText(SessionData.getAlumno().getEmpresa().getResponsable());
        if (horasTotalDual != 0) {
            lblHorasTotalesDual.setText(SessionData.getAlumno().getTotalDual().toString());
            lblHorasRealizadasDual.setText(String.valueOf(gestorAlumno.calcularHorasDual(alumno).get(0)));
        } else {
            lblHorasTotalesDual.setText("Sin asignar");
            lblHorasRealizadasDual.setText("Sin asignar");
        }
        lblHorasTotalesFct.setText(SessionData.getAlumno().getTotalFCT().toString());
        lblHorasRealizadasFct.setText(String.valueOf(gestorAlumno.calcularHorasFct(alumno).get(0)));

        actualizarTabla();
    }

    private void actualizarTabla() {
        SessionData.setActividad(null);
        List<Actividad> listadoActividades = gestorAlumno.obtenerAlumnoId(SessionData.getAlumno().getId()).getActividades();
        List<Actividad> listadoActividadesFct = new ArrayList<>();
        List<Actividad> listadoActividadesDual = new ArrayList<>();

        for (Actividad actividad : listadoActividades) {
            if (actividad.getTipo().equals("FCT")) {
                listadoActividadesFct.add(actividad);
            } else if (actividad.getTipo().equals("DUAL")) {
                listadoActividadesDual.add(actividad);
            }
        }

        ObservableList<Actividad> datos = FXCollections.observableArrayList();
        datos.addAll(listadoActividadesFct);
        ObservableList<Actividad> datosDual = FXCollections.observableArrayList();
        datosDual.addAll(listadoActividadesDual);


        tFct.getItems().clear();
        tFct.getItems().addAll(datos);

        tDual.getItems().clear();
        tDual.getItems().addAll(datosDual);

    }

    @FXML
    private void itemCerrarSesion(ActionEvent event) {
        try {
            App.setRoot("inicio");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void itemAcercaDe(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Acerca de...");
        alert.setContentText("Software realizado por Adolfo Salado López.");
        alert.showAndWait();
    }

    @FXML
    private void btnVolver(ActionEvent event) {
        try {
            App.setRoot("ventana-profesor-alumnos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
