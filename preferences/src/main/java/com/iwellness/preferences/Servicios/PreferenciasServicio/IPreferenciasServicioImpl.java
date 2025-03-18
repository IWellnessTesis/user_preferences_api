package com.iwellness.preferences.Servicios.PreferenciasServicio;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Repositorios.IPreferenciasRepositorio;

@Service
public class IPreferenciasServicioImpl implements IPreferenciasServicio{

    @Autowired
    private IPreferenciasRepositorio preferenciasRepositorio;

    @Override
    //retorna una lista de preferencias
    public List<Preferencias> buscarTodos() {
        return (List<Preferencias>) preferenciasRepositorio.findAll();
    }

    @Override
    //retorna una preferencia por id, si no lo encuentra, lanza una excepción NoSuchElementException
    public Preferencias BuscarPorId(Long id) {
        return preferenciasRepositorio.findById(id).orElseThrow(() -> 
            new NoSuchElementException("Preferencia no encontrado con ID: " + id));
    }

    @Override
    //guarda una preferencia
    public Preferencias guardar(Preferencias preferencia) {
        return preferenciasRepositorio.save(preferencia);
    }

    @Override
    //actualiza una preferencia
    public Preferencias actualizar(Preferencias preferencia){
        return preferenciasRepositorio.save(preferencia);
    }

    @Override
    //elimina un servicio por id
    public void eliminar(Long id) {
        preferenciasRepositorio.deleteById(id);
    }
}
