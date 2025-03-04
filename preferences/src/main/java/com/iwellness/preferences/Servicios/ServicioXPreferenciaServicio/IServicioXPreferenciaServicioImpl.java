package com.iwellness.preferences.Servicios.ServicioXPreferenciaServicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwellness.preferences.Entidades.ServicioXPreferencia;
import com.iwellness.preferences.Repositorios.IServicioXPreferenciaRepositorio;

@Service
public class IServicioXPreferenciaServicioImpl implements IServicioXPreferenciaServicio{

    @Autowired
    private IServicioXPreferenciaRepositorio servicioXPreferenciaRepositorio;

    @Override
    public List<ServicioXPreferencia> obtenerPorIdServicio(Long idServicio) {
        return servicioXPreferenciaRepositorio.findBy_idServicio(idServicio);
    }

    @Override
    public ServicioXPreferencia guardar(ServicioXPreferencia servicioXPreferencia) {
        return servicioXPreferenciaRepositorio.save(servicioXPreferencia);
    }

    @Override
    public void eliminar(Long id) {
        servicioXPreferenciaRepositorio.deleteById(id);
    }

}
