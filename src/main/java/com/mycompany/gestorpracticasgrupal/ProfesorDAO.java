package com.mycompany.gestorpracticasgrupal;

import java.util.List;
import models.Profesor;

public interface ProfesorDAO {

    Profesor obtenerProfesorId(Integer id);

    List<Profesor> obtenerListadoProfesores();
}
