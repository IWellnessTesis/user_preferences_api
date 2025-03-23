package com.iwellness.preferences.Servicios.TuristaXPreferenciaServicio;

import java.util.List;

import com.iwellness.preferences.Entidades.TuristaXPreferencia;

public interface ITuristaXPreferenciaServicio {

    List<TuristaXPreferencia> buscarTodos();

    List<TuristaXPreferencia> obtenerPorIdUsuario(Long idUsuario);

    TuristaXPreferencia guardar(TuristaXPreferencia turistaXPreferencia);

    void eliminar(Long id);

    TuristaXPreferencia actualizar(TuristaXPreferencia turistaXPreferencia);

    Object findByPreferencia_IdPreferencias(Long idPreferencia);

}
