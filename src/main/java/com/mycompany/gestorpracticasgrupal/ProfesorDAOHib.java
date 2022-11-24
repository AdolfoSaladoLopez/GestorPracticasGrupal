package com.mycompany.gestorpracticasgrupal;

import java.util.List;
import models.Profesor;

public class ProfesorDAOHib implements ProfesorDAO {

    @Override
    public Profesor obtenerProfesorId(Integer id) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Profesor.class, id);
        }
    }

    @Override
    public List<Profesor> obtenerListadoProfesores() {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("from Profesor");
            return q.list();
        }
    }

}
