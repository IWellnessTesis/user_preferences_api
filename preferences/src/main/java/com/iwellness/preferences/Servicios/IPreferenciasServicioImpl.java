package com.iwellness.preferences.Servicios;

import java.util.List;

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
    public Preferencias BuscarPorId(Long id) {
        return preferenciasRepositorio.findById(id).orElseThrow();
    }

}
