package com.mycompany.gestorpracticasgrupal;

import java.util.List;
import models.Actividad;
import org.hibernate.Transaction;

public class ActividadDAOHib implements ActividadDAO {

    @Override
    public Boolean crearActividad(Actividad actividad) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.save(actividad);
            t.commit();

            return true;
        }
    }

    @Override
    public Boolean modificarActividad(Actividad actividad) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.update(actividad);
            t.commit();

            return true;
        }
    }

    @Override
    public Boolean eliminarActividad(Actividad actividad) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.delete(actividad);
            t.commit();

            return true;
        }
    }

    @Override
    public Actividad obtenerActividadId(Integer id) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Actividad.class, id);
        }
    }

    @Override
    public List<Actividad> obtenerListadoActividades() {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("from Actividad");
            return q.list();
        }
    }

}
