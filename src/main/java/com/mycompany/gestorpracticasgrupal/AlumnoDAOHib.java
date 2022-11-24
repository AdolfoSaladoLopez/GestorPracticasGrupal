package com.mycompany.gestorpracticasgrupal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;
import models.Alumno;

public class AlumnoDAOHib implements AlumnoDAO {

    @Override
    public Boolean crearAlumno(Alumno alumno) {
        try (var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.save(alumno);
            t.commit();

            return true;
        }
    }

    @Override
    public Boolean modificarAlumno(Alumno alumno) {
        try (var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.update(alumno);
            t.commit();

            return true;
        }
    }

    @Override
    public Boolean eliminarAlumno(Alumno alumno) {
        try (var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.delete(alumno);
            t.commit();

            return true;
        }
    }

    @Override
    public List<Alumno> obtenerListadoAlumnos() {
        try (var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("from Alumno");
            return q.list();
        }
    }

    @Override
    public Alumno obtenerAlumnoId(Integer id) {
        try (var s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Alumno.class, id);
        }
    }

    @Override
    public List<Integer> calcularHorasFct(Alumno alumno) {
        Integer id = alumno.getId();
        try (var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("Select sum(ac.horas) FROM Alumno a, Actividad ac WHERE a.id = ac.alumno.id AND a.id = :id and ac.tipo = 'FCT'");
            q.setInteger("id", id);

            return q.list();
        }
    }

    @Override
    public List<Integer> calcularHorasDual(Alumno alumno) {
        Integer id = alumno.getId();
        try (var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("Select sum(ac.horas) FROM Alumno a, Actividad ac WHERE a.id = ac.alumno.id AND a.id = :id and ac.tipo = 'DUAL'");
            q.setInteger("id", id);

            return q.list();
        }
    }
}
