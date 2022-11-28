package com.mycompany.gestorpracticasgrupal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Empresa;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModificarEmpresa implements Initializable {

    @FXML
    private TextField tfNombreEmpresa;
    @FXML
    private TextField tfResponsable;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfObservaciones;

    private Empresa empresaAModificar = SessionData.getEmpresa();
    private EmpresaDAO gestorEmpresas = new EmpresaDAOHib();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfNombreEmpresa.setText(empresaAModificar.getNombre());
        tfResponsable.setText(empresaAModificar.getResponsable());
        tfCorreo.setText(empresaAModificar.getCorreo());
        tfTelefono.setText(empresaAModificar.getTelefono());
        tfObservaciones.setText(empresaAModificar.getObservaciones());

    }

    @FXML
    private void btnVolver(ActionEvent event) {
        try {
            App.setRoot("ventana-empresas");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnCrear(ActionEvent event) {
        empresaAModificar.setNombre(tfNombreEmpresa.getText());
        empresaAModificar.setResponsable(tfResponsable.getText());
        empresaAModificar.setCorreo(tfCorreo.getText());
        empresaAModificar.setTelefono(tfTelefono.getText());
        empresaAModificar.setObservaciones(tfObservaciones.getText());
        empresaAModificar.setAlumnos(empresaAModificar.getAlumnos());

        if (gestorEmpresas.modificarEmpresa(empresaAModificar)) {
            try {
                App.setRoot("ventana-empresas");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("No ha sido posible modificar la empresa.");
            alert.showAndWait();
        }
    }

}
