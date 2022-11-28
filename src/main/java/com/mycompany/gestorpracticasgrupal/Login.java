package com.mycompany.gestorpracticasgrupal;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Alumno;
import models.Profesor;
import org.hibernate.query.Query;

public class Login implements Initializable {

    @FXML
    private Label info;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void aceptar(ActionEvent event) {
        if (SessionData.getPlataforma().equals("Profesor")) {
            try (var s = HibernateUtil.getSessionFactory().openSession()) {
                Query q = s.createQuery("from Profesor where correo=:param and password=md5(:pwd)");
                q.setParameter("param", txtUser.getText());
                q.setParameter("pwd", txtPassword.getText());

                ArrayList<Profesor> resultado = (ArrayList<Profesor>) q.list();

                if (resultado.size() > 0) {
                    info.setText("Usuario existe");
                    info.setStyle("-fx-background-color:green; -fx-text-fill:white;");

                    SessionData.setProfesor(resultado.get(0));

                    try {
                        App.setRoot("ventana-profesor-alumnos");
                    } catch (IOException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    info.setText("Profesor no existe");
                    info.setStyle("-fx-background-color:red; -fx-text-fill:white;");
                }
            }
        } else if (SessionData.getPlataforma().equals("Alumno")) {
            try (var s = HibernateUtil.getSessionFactory().openSession()) {
                Query q = s.createQuery("from Alumno where correo=:param and password=md5(:pwd)");
                q.setParameter("param", txtUser.getText());
                q.setParameter("pwd", txtPassword.getText());

                ArrayList<Alumno> resultado = (ArrayList<Alumno>) q.list();

                if (resultado.size() > 0) {
                    info.setText("Alumno existe");
                    info.setStyle("-fx-background-color:green; -fx-text-fill:white;");

                    SessionData.setAlumno(resultado.get(0));

                    try {
                        App.setRoot("ventana-alumno");
                    } catch (IOException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    info.setText("Alumno no existe");
                    info.setStyle("-fx-background-color:red; -fx-text-fill:white;");
                }
            }


        }

    }

    @FXML
    private void btnVolver(ActionEvent event) {
        try {
            App.setRoot("inicio");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

