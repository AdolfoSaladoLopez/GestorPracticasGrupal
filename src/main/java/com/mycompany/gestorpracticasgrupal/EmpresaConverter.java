package com.mycompany.gestorpracticasgrupal;

import javafx.util.StringConverter;
import models.Empresa;

public class EmpresaConverter extends StringConverter<Empresa> {
    @Override
    public String toString(Empresa empresa) {
        return empresa == null ? null : empresa.getNombre();
    }

    @Override
    public Empresa fromString(String s) {
        return null;
    }
}
