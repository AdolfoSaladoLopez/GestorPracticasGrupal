
package com.mycompany.gestorpracticasgrupal;

import java.util.ArrayList;
import models.Alumno;


public class NewMain {


    public static void main(String[] args) {
        AlumnoDAO gestorAlumnos = new AlumnoDAOHib();
        EmpresaDAO gestorEmpresas = new EmpresaDAOHib();
        ActividadDAO gestorActividades = new ActividadDAOHib();
        ProfesorDAO gestorProfesores = new ProfesorDAOHib();
        
        var listadoAlumnos = new ArrayList<Alumno>(gestorAlumnos.obtenerListadoAlumnos());
        
        for (Alumno alumno : listadoAlumnos) {
            System.out.println(alumno.getActividades());
            System.out.println(alumno.getProfesor());
            System.out.println(alumno.getEmpresa());
            System.out.println(gestorAlumnos.calcularHorasFct(alumno));
        }
       
        
        System.out.println("");
        
        System.out.println(gestorProfesores.obtenerListadoProfesores());
        System.out.println("");
        System.out.println(gestorEmpresas.obtenerListadoEmpresas());
        System.out.println(gestorActividades.obtenerListadoActividades());
        
    }
    
}
