package com.mycompany.gestorpracticasgrupal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Alumno;
import models.Profesor;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import models.Empresa;

public class CrearAlumno implements Initializable {

    @FXML
    private Label lblBienvenida;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidos;
    @FXML
    private TextField tfDni;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private DatePicker dpNacimiento;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfObservaciones;
    @FXML
    private TextField tfTelefono;

    private Profesor profesorSesion = SessionData.getProfesor();
    private AlumnoDAO gestorAlumnos = new AlumnoDAOHib();
    private EmpresaDAO gestorEmpresas = new EmpresaDAOHib();
    private Alumno alumno = new Alumno();

    @FXML
    private ComboBox<Empresa> cbEmpresa;
    @FXML
    private TextField tfHorasTotalFct;
    @FXML
    private TextField tfHorasTotalDual;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rellenarDatePicker();
        lblBienvenida.setText("Bienvenid@, " + profesorSesion.getNombre() + " " + profesorSesion.getApellidos());
        rellenarComboEmpresas();

    }


    private void rellenarDatePicker() {
        dpNacimiento.setValue( LocalDate.now() );
    }

    private Date obtenerFecha() {
        LocalDate fecha = dpNacimiento.getValue();
        String fechaString = fecha.getYear() + "/" + fecha.getMonthValue() + "/" + fecha.getDayOfMonth();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(fechaString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    private void rellenarComboEmpresas() {
        var listadoEmpresas = new ArrayList<>(gestorEmpresas.obtenerListadoEmpresas());
        ObservableList<Empresa> empresas = FXCollections.observableArrayList();
        empresas.addAll(listadoEmpresas);

        cbEmpresa.setItems(empresas);
        cbEmpresa.setConverter(new EmpresaConverter());
        cbEmpresa.getSelectionModel().selectFirst();


    }

    @FXML
    private void btnVolver(ActionEvent event) {
        try {
            App.setRoot("ventana-profesor-alumnos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnCrear(ActionEvent event) {
        alumno.setId(null);
        alumno.setNombre(tfNombre.getText());
        alumno.setApellidos(tfApellidos.getText());
        alumno.setDni(tfDni.getText());
        alumno.setPassword(tfPassword.getText());
        alumno.setCorreo(tfCorreo.getText());
        alumno.setObservaciones(tfObservaciones.getText());
        alumno.setTelefono(tfTelefono.getText());
        alumno.setTotalDual(Integer.valueOf(tfHorasTotalDual.getText()));
        alumno.setTotalFCT(Integer.valueOf(tfHorasTotalFct.getText()));
        alumno.setNacimiento(obtenerFecha());
        alumno.setEmpresa(cbEmpresa.getValue());
        alumno.setProfesor(profesorSesion);
        alumno.setTelefono(tfTelefono.getText());
        alumno.setActividades(null);

        if (gestorAlumnos.crearAlumno(alumno)) {
            profesorSesion.getAlumnos().add(alumno);
            profesorSesion.setAlumnos(profesorSesion.getAlumnos());
            try {
                App.setRoot("ventana-profesor-alumnos");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
