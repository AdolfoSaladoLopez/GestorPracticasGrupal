/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gestorpracticasgrupal;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static java.util.Collections.list;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Actividad;
import models.Alumno;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class VentanaAlumnoController implements Initializable {
    AlumnoDAO gestorAlumno = new AlumnoDAOHib();
    ActividadDAO gestorActividad = new ActividadDAOHib();

    @FXML
    private TableView<Actividad> tFct;
    @FXML
    private TableColumn<Actividad, String> tcFecha;
    @FXML
    private TableColumn<Actividad, String> tcTipo;
    @FXML
    private TableColumn<Actividad, Integer> tcHoras;
    @FXML
    private TableColumn<Actividad, String> tcActividad;
    @FXML
    private TableColumn<Actividad, String> tcObservaciones;
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
    private Label lblNombreProfesor;
    @FXML
    private Label lblCorreoProfesor;
    @FXML
    private Label lblEmpresaDual;
    @FXML
    private Label lblCorreoDual;
    @FXML
    private Label lblResponsableDual;
    @FXML
    private Label lblHorasTotalesDual;
    @FXML
    private Label lblHorasTotalesFct;
    @FXML
    private Label lblHorasRealizadasDual;
    @FXML
    private Label lblHorasRealizadasFct;
    @FXML
    private TableView<Actividad> tDual;
    @FXML
    private TableColumn<Actividad, String> tcFechaDual;
    @FXML
    private TableColumn<Actividad, String> tcTipoDual;
    @FXML
    private TableColumn<Actividad, Integer> tcHorasDual;
    @FXML
    private TableColumn<Actividad, String> tcActividadDual;
    @FXML
    private TableColumn<Actividad, String> tcObservacionesDual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Alumno alumno = SessionData.getAlumno();
        Integer horasTotalDual = alumno.getTotalDual();
        Integer horasTotalFct = alumno.getTotalFCT();

        establecerCeldas();

        /* ETIQUETAS ALUMNO */
        lblBienvenida.setText("Bienvenid@, " + SessionData.getAlumno().getNombre() + " " + SessionData.getAlumno().getApellidos());
        lblDnIAlumno.setText(SessionData.getAlumno().getDni());
        lblNacimientoAlumno.setText(SessionData.getAlumno().getNacimiento().toString().split(" ")[0]);
        lblCorreoAlumno.setText(SessionData.getAlumno().getCorreo());
        lblNumeroAlumno.setText(SessionData.getAlumno().getTelefono());

        /* ETIQUETAS PROFESOR */
        lblNombreProfesor.setText(SessionData.getAlumno().getProfesor().getNombre() + " " + SessionData.getAlumno().getProfesor().getApellidos());
        lblCorreoProfesor.setText(SessionData.getAlumno().getProfesor().getCorreo());

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

    private void establecerCeldas() {
        //tcFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tcHoras.setCellValueFactory(new PropertyValueFactory<>("horas"));
        tcActividad.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        tcObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

        tcFecha.setCellValueFactory((var fila) -> {
            Actividad a = fila.getValue();
            return new SimpleStringProperty(a.getFecha().toString().split(" ")[0]);
        });

        tcFechaDual.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tcTipoDual.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tcHorasDual.setCellValueFactory(new PropertyValueFactory<>("horas"));
        tcActividadDual.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        tcObservacionesDual.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

        tcFechaDual.setCellValueFactory((var fila) -> {
            Actividad a = fila.getValue();
            return new SimpleStringProperty(a.getFecha().toString().split(" ")[0]);
        });
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
    void itemAcercaDe(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Acerca de...");
        alert.setContentText("Software realizado por Adolfo Salado López.");
        alert.showAndWait();
    }

    @FXML
    void itemCerrarSesion(ActionEvent event) throws IOException {
        SessionData.setAlumno(null);
        App.setRoot("inicio");
    }

    @FXML
    private void btnCrearActividad(ActionEvent event) {
        try {
            App.setRoot("crear-actividad-alumno");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnModificarActividad(ActionEvent event) {
        if (tFct.getSelectionModel().getSelectedItem() != null ) {
            SessionData.setActividad(tFct.getSelectionModel().getSelectedItem());

            try {
                App.setRoot("modificar-actividad-alumno");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (tDual.getSelectionModel().getSelectedItem() != null) {
            SessionData.setActividad(tDual.getSelectionModel().getSelectedItem());

            try {
                App.setRoot("modificar-actividad-alumno");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void btnEliminarActividad(ActionEvent event) {
        if (tFct.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Eliminación del pedido");
            alert.setTitle("Confirmación");
            alert.setContentText("¿Estas seguro de confirmar la acción?");
            Optional<ButtonType> action = alert.showAndWait();
            // Si hemos pulsado en aceptar
            if (action.get() == ButtonType.OK) {
                gestorActividad.eliminarActividad(tFct.getSelectionModel().getSelectedItem());
                actualizarTabla();
                lblHorasRealizadasFct.setText(String.valueOf(gestorAlumno.calcularHorasFct(SessionData.getAlumno()).get(0)));
            }
        }
        if (tDual.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Eliminación del pedido");
            alert.setTitle("Confirmación");
            alert.setContentText("¿Estas seguro de confirmar la acción?");
            Optional<ButtonType> action = alert.showAndWait();
            // Si hemos pulsado en aceptar
            if (action.get() == ButtonType.OK) {
                gestorActividad.eliminarActividad(tDual.getSelectionModel().getSelectedItem());
                actualizarTabla();
                lblHorasRealizadasDual.setText(String.valueOf(gestorAlumno.calcularHorasDual(SessionData.getAlumno()).get(0)));
            }
        }
    }

}
