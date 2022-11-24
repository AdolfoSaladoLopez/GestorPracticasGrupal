package com.mycompany.gestorpracticasgrupal;

import java.util.List;
import models.Empresa;

public interface EmpresaDAO {

    Boolean crearEmpresa(Empresa empresa);

    Boolean modificarEmpresa(Empresa empresa);

    Boolean eliminarEmpresa(Empresa empresa);

    Empresa obtenerEmpresaId(Integer id);

    List<Empresa> obtenerListadoEmpresas();
}
