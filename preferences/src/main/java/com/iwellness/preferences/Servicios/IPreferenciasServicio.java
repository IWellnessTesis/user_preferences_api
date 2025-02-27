package com.iwellness.preferences.Servicios;

import java.util.List;

import com.iwellness.preferences.Entidades.Preferencias;

public interface IPreferenciasServicio {

    List<Preferencias> buscarTodos();

    Preferencias BuscarPorId(Long id);

}
