package com.mycompany.gestorpracticasgrupal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Actividad;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ModificarActividadAlumno implements Initializable {
    ActividadDAO gestorActividad = new ActividadDAOHib();
    private static Actividad actividadAModificar;

    @FXML
    private DatePicker pickerFecha;
    @FXML
    private ComboBox<String> comboTipo;
    @FXML
    private Spinner<Integer> spinnerHoras;
    @FXML
    private TextField tfActividad;
    @FXML
    private TextField tfObservaciones;

    @FXML
    private void btnCancelar(ActionEvent event) {
        try {
            App.setRoot("ventana-alumno");
            SessionData.setActividad(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnCrear(ActionEvent event) throws ParseException {
        String textoActividad = tfActividad.getText();
        String textoObservaciones = tfObservaciones.getText();
        Integer numeroHoras = spinnerHoras.getValue();
        String tipo = comboTipo.getSelectionModel().getSelectedItem();
        LocalDate fecha = pickerFecha.getValue();
        String fechaString = fecha.getYear() + "/" + fecha.getMonthValue() + "/" + fecha.getDayOfMonth();
        Date date = new SimpleDateFormat("yyyy/MM/dd").parse(fechaString);


        actividadAModificar.setFecha(date);
        actividadAModificar.setTipo(tipo);
        actividadAModificar.setHoras(numeroHoras);
        actividadAModificar.setActividad(textoActividad);
        actividadAModificar.setObservaciones(textoObservaciones);
        actividadAModificar.setAlumno(actividadAModificar.getAlumno());

        SessionData.setCambios(gestorActividad.modificarActividad(actividadAModificar));

        try {
            App.setRoot("ventana-alumno");
            SessionData.setActividad(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actividadAModificar = SessionData.getActividad();

        Date input = actividadAModificar.getFecha();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        pickerFecha.setValue(date);

        rellenarComboTipos();
        rellenarSpinnerHoras();
        tfActividad.setText(actividadAModificar.getActividad());
        tfObservaciones.setText(actividadAModificar.getObservaciones());
    }

    private void rellenarComboTipos() {
        ObservableList<String> tipos = FXCollections.observableArrayList();
        tipos.add("DUAL");
        tipos.add("FCT");

        comboTipo.setItems(tipos);
        comboTipo.getSelectionModel().select(actividadAModificar.getTipo());

    }

    private void rellenarSpinnerHoras() {
        /* Rellenar el Spinner */
        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        svf.setValue(actividadAModificar.getHoras());
        spinnerHoras.setValueFactory(svf);
    }
}
