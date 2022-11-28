package com.mycompany.gestorpracticasgrupal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Alumno;
import models.Empresa;
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

public class ModificarAlumno implements Initializable {

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

    @FXML
    private ComboBox<Empresa> cbEmpresa;

    private Alumno alumno = SessionData.getAlumno();
    private Profesor profesorSesion = SessionData.getProfesor();
    private EmpresaDAO gestorEmpresas = new EmpresaDAOHib();
    private AlumnoDAO gestorAlumnos = new AlumnoDAOHib();
    @FXML
    private TextField tfHorasTotalDual;
    @FXML
    private TextField tfHorasTotalFct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Date input = alumno.getNacimiento();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpNacimiento.setValue(date);
        System.out.println(alumno.getNacimiento());

        tfNombre.setText(alumno.getNombre());
        tfApellidos.setText(alumno.getApellidos());
        tfDni.setText(alumno.getDni());
        tfPassword.setText(alumno.getPassword());
        tfPassword.setEditable(false);
        tfCorreo.setText(alumno.getCorreo());
        tfObservaciones.setText(alumno.getObservaciones());
        tfTelefono.setText(alumno.getTelefono());
        tfHorasTotalDual.setText(alumno.getTotalDual().toString());
        tfHorasTotalFct.setText(alumno.getTotalFCT().toString());

        rellenarComboEmpresas();


        lblBienvenida.setText("Bienvenid@, " + profesorSesion.getNombre() + " " + profesorSesion.getApellidos());

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
        alumno.setId(alumno.getId());
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
        alumno.setActividades(alumno.getActividades());

        if (gestorAlumnos.modificarAlumno(alumno)) {
            try {
                App.setRoot("ventana-profesor-alumnos");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
        cbEmpresa.getSelectionModel().select(alumno.getEmpresa());
    }

}
