package com.iwellness.preferences.Servicios.ServicioXPreferenciaServicio;

import java.util.List;

import com.iwellness.preferences.Entidades.ServicioXPreferencia;

public interface IServicioXPreferenciaServicio {

    List<ServicioXPreferencia> obtenerPorIdServicio(Long idServicio);

    ServicioXPreferencia guardar(ServicioXPreferencia servicioXPreferencia);

    void eliminar(Long id);

}
