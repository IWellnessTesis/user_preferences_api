package com.iwellness.preferences.Servicios.PreferenciasServicio;

import java.util.List;
import java.util.Optional;

import com.iwellness.preferences.Entidades.Preferencias;

public interface IPreferenciasServicio {

    List<Preferencias> buscarTodos();

    Optional<Preferencias> BuscarPorId(Long id);

    Preferencias guardar(Preferencias preferencia);

    void eliminar(Long id);

}
