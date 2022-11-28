package com.mycompany.gestorpracticasgrupal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Actividad;
import models.Alumno;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class CrearActividadAlumno implements Initializable {
    ActividadDAO gestorActividades = new ActividadDAOHib();

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

    private final Actividad nuevaActividad = new Actividad();
    private Alumno alumno;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alumno = SessionData.getAlumno();
        rellenarComboTipos();

        /* Rellenar el Spinner */
        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        svf.setValue(1);
        spinnerHoras.setValueFactory(svf);

        pickerFecha.setValue(LocalDate.now());

    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        try {
            App.setRoot("ventana-alumno");
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


        nuevaActividad.setFecha(date);
        nuevaActividad.setTipo(tipo);
        nuevaActividad.setHoras(numeroHoras);
        nuevaActividad.setActividad(textoActividad);
        nuevaActividad.setObservaciones(textoObservaciones);
        nuevaActividad.setAlumno(alumno);

        SessionData.setCambios(gestorActividades.crearActividad(nuevaActividad));

        try {
            App.setRoot("ventana-alumno");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void rellenarComboTipos() {
        ObservableList<String> tipos = FXCollections.observableArrayList();
        tipos.add("DUAL");
        tipos.add("FCT");

        comboTipo.setItems(tipos);
        comboTipo.getSelectionModel().selectFirst();

    }


}
