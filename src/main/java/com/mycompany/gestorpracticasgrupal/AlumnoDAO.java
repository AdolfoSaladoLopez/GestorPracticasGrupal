package com.mycompany.gestorpracticasgrupal;

import java.util.ArrayList;
import java.util.List;
import models.Alumno;

public interface AlumnoDAO {

    Boolean crearAlumno(Alumno alumno);

    Boolean modificarAlumno(Alumno alumno);

    Boolean eliminarAlumno(Alumno alumno);

    List<Alumno> obtenerListadoAlumnos();

    Alumno obtenerAlumnoId(Integer id);
    
    ArrayList<Integer> calcularHorasFct(Alumno alumno);

    List<Integer> calcularHorasDual(Alumno alumno);
}
