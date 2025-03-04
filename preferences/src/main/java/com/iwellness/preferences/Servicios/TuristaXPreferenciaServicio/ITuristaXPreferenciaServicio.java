package com.iwellness.preferences.Servicios.TuristaXPreferenciaServicio;

import java.util.List;

import com.iwellness.preferences.Entidades.TuristaXPreferencia;

public interface ITuristaXPreferenciaServicio {

    List<TuristaXPreferencia> obtenerPorIdUsuario(Long idUsuario);

    TuristaXPreferencia guardar(TuristaXPreferencia turistaXPreferencia);

    void eliminar(Long id);

}
