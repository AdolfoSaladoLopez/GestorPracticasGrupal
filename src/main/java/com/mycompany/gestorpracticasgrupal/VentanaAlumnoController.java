/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gestorpracticasgrupal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static java.util.Collections.list;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableColumn<?, ?> tcFecha1;
    @FXML
    private TableColumn<?, ?> tcTipo1;
    @FXML
    private TableColumn<?, ?> tcHoras1;
    @FXML
    private TableColumn<?, ?> tcActividad1;
    @FXML
    private TableColumn<?, ?> tcObservaciones1;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Alumno alumno = SessionData.getAlumno();
        Integer horasTotalDual = alumno.getTotalDual();
        Integer horasTotalFct = alumno.getTotalFCT();


        //tcFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        tcTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        tcHoras.setCellValueFactory(new PropertyValueFactory("horas"));
        tcActividad.setCellValueFactory(new PropertyValueFactory("actividad"));
        tcObservaciones.setCellValueFactory(new PropertyValueFactory("observaciones"));

        tcFecha.setCellValueFactory( (var fila)->{
            Actividad a = fila.getValue();
            return new SimpleStringProperty(a.getFecha().toString().split(" ")[0]);
        });

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

    private void actualizarTabla() {
        List<Actividad> listadoActividades = new ArrayList<>();
        listadoActividades = gestorAlumno.obtenerAlumnoId(SessionData.getAlumno().getId()).getActividades();

        List<Actividad> listadoActividadesFct = new ArrayList<>();

        for (Actividad actividad : listadoActividades) {
            if (actividad.getTipo().equals("FCT")) {
                listadoActividadesFct.add(actividad);
            }
        }

        ObservableList<Actividad> datos = FXCollections.observableArrayList();
        datos.addAll(listadoActividadesFct);

        tFct.getItems().clear();
        tFct.getItems().addAll(datos);

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

}
