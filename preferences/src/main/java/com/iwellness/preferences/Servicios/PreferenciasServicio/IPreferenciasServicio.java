package com.iwellness.preferences.Servicios.PreferenciasServicio;

import java.util.List;

import com.iwellness.preferences.Entidades.Preferencias;

public interface IPreferenciasServicio {

    List<Preferencias> buscarTodos();

    Preferencias BuscarPorId(Long id);

    Preferencias guardar(Preferencias preferencia);

    Preferencias actualizar(Preferencias preferencia);

    void eliminar(Long id);

}
