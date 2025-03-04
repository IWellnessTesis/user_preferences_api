package com.iwellness.preferences.Servicios.TuristaXPreferenciaServicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwellness.preferences.Entidades.TuristaXPreferencia;
import com.iwellness.preferences.Repositorios.ITuristaXPreferenciaRepositorio;

@Service
public class ITuristaXPreferenciaServicioImpl implements ITuristaXPreferenciaServicio{

    @Autowired
    private ITuristaXPreferenciaRepositorio turistaXPreferenciaRepositorio;

    @Override
    public List<TuristaXPreferencia> obtenerPorIdUsuario(Long idUsuario) {
        return turistaXPreferenciaRepositorio.findBy_idUsuario(idUsuario);
    }

    @Override
    public TuristaXPreferencia guardar(TuristaXPreferencia turistaXPreferencia) {
        return turistaXPreferenciaRepositorio.save(turistaXPreferencia);
    }

    @Override
    public void eliminar(Long id) {
        turistaXPreferenciaRepositorio.deleteById(id);
    }

}
