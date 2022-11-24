package com.mycompany.gestorpracticasgrupal;

import java.util.List;
import models.Actividad;

public interface ActividadDAO {

    Boolean crearActividad(Actividad actividad);

    Boolean modificarActividad(Actividad actividad);

    Boolean eliminarActividad(Actividad actividad);

    Actividad obtenerActividadId(Integer id);

    List<Actividad> obtenerListadoActividades();
}
