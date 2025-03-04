package com.iwellness.preferences.Servicios.PreferenciasServicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Repositorios.IPreferenciasRepositorio;

@Service
public class IPreferenciasServicioImpl implements IPreferenciasServicio{

    @Autowired
    private IPreferenciasRepositorio preferenciasRepositorio;

    @Override
    public List<Preferencias> buscarTodos() {
        return (List<Preferencias>) preferenciasRepositorio.findAll();
    }

    @Override
    public Optional<Preferencias> BuscarPorId(Long id) {
        return preferenciasRepositorio.findById(id);
    }

    @Override
    public Preferencias guardar(Preferencias preferencia) {
        return preferenciasRepositorio.save(preferencia);
    }

    @Override
    public void eliminar(Long id) {
        preferenciasRepositorio.deleteById(id);
    }

}
