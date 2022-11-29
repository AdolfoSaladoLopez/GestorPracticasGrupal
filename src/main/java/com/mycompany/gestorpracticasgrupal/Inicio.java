package com.mycompany.gestorpracticasgrupal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;

public class Inicio implements Initializable {
    @FXML
    private ImageView ivSchool;

    @FXML
    private VBox vbox;
    @FXML
    private Button btnProfesores;
    @FXML
    private Button btnAlumnos;
    @FXML
    private Button btnSalir;

    @FXML
    private Label ttulo;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ttulo.setStyle("-fx-stroke-width: 5;-fx-stroke: green;");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("src/main/resources/img/school.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image imagen = new Image(fis);
        ivSchool.setImage(imagen);

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
