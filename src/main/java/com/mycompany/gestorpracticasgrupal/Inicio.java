package com.mycompany.gestorpracticasgrupal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

public class Inicio implements Initializable {
    
    @FXML
    private VBox vbox;
    @FXML
    private Button btnProfesores;
    @FXML
    private Button btnAlumnos;
    @FXML
    private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void btnProfesores(ActionEvent event) {
        SessionData.setPlataforma("Profesor");
        try {
            App.setRoot("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnAlumnos(ActionEvent event) {
        SessionData.setPlataforma("Alumno");
        try {
            App.setRoot("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void btnSalir(ActionEvent event) {
        System.exit(0);
    }

}
