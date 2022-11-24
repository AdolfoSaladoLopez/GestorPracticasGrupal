package com.mycompany.gestorpracticasgrupal;

import java.util.List;
import models.Empresa;
import org.hibernate.Transaction;

public class EmpresaDAOHib implements EmpresaDAO {

    @Override
    public Boolean crearEmpresa(Empresa empresa) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.save(empresa);
            t.commit();

            return true;
        }
    }

    @Override
    public Boolean modificarEmpresa(Empresa empresa) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.update(empresa);
            t.commit();

            return true;
        }
    }

    @Override
    public Boolean eliminarEmpresa(Empresa empresa) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.delete(empresa);
            t.commit();

            return true;
        }
    }

    @Override
    public Empresa obtenerEmpresaId(Integer id) {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Empresa.class, id);
        }
    }

    @Override
    public List<Empresa> obtenerListadoEmpresas() {
        try ( var s = HibernateUtil.getSessionFactory().openSession()) {
            var q = s.createQuery("from Empresa");
            return q.list();
        }
    }

}
