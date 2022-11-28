package com.mycompany.gestorpracticasgrupal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import models.Empresa;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CrearEmpresa implements Initializable {

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

    EmpresaDAO gestorEmpresas = new EmpresaDAOHib();
    Empresa nuevaEmpresa = new Empresa();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        nuevaEmpresa.setNombre(tfNombreEmpresa.getText());
        nuevaEmpresa.setResponsable(tfResponsable.getText());
        nuevaEmpresa.setCorreo(tfCorreo.getText());
        nuevaEmpresa.setTelefono(tfTelefono.getText());
        nuevaEmpresa.setObservaciones(tfObservaciones.getText());
        nuevaEmpresa.setAlumnos(null);

        if (gestorEmpresas.crearEmpresa(nuevaEmpresa)) {
            try {
                App.setRoot("ventana-empresas");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
