/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gestorpracticasgrupal;

import java.net.URL;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Actividad;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        tcTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        tcHoras.setCellValueFactory(new PropertyValueFactory("horas"));
        tcActividad.setCellValueFactory(new PropertyValueFactory("actividad"));
        tcObservaciones.setCellValueFactory(new PropertyValueFactory("observaciones"));
    
        actualizarTabla();

    }    

    private void actualizarTabla() {
        List<Actividad> listadoActividades = new ArrayList<>();
        listadoActividades = gestorAlumno.obtenerAlumnoId(1).getActividades();
        System.out.println(listadoActividades);

        ObservableList<Actividad> datos = FXCollections.observableArrayList();
        datos.addAll(listadoActividades);

        tFct.getItems().clear();
        tFct.getItems().addAll(datos);

    }
    
}
