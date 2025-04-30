package com.iwellness.preferences.Servicios.TuristaXPreferenciaServicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwellness.preferences.Clientes.TuristaFeignClient;
import com.iwellness.preferences.DTO.TuristaDTO;
import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Entidades.TuristaXPreferencia;
import com.iwellness.preferences.Repositorios.IPreferenciasRepositorio;
import com.iwellness.preferences.Repositorios.ITuristaXPreferenciaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ITuristaXPreferenciaServicioImpl implements ITuristaXPreferenciaServicio{

    @Autowired
    private ITuristaXPreferenciaRepositorio turistaXPreferenciaRepositorio;

    @Autowired
    private IPreferenciasRepositorio preferenciasRepositorio;

    @Autowired
    private TuristaFeignClient turistaFeignClient;

    @Override
    public List<TuristaXPreferencia> buscarTodos() {
        return (List<TuristaXPreferencia>) turistaXPreferenciaRepositorio.findAll();
    }

    @Override
    public List<TuristaXPreferencia> obtenerPorIdUsuario(Long idUsuario) {
        return turistaXPreferenciaRepositorio.findByIdUsuario(idUsuario);
    }

    @Override
    public List<TuristaXPreferencia> findByPreferencia_IdPreferencias (Long idPreferencia){
        List<TuristaXPreferencia> resultados = turistaXPreferenciaRepositorio.findByPreferencia_IdPreferencias(idPreferencia);
    if (resultados.isEmpty()) {
        throw new IllegalArgumentException("No se encontraron relaciones con la Preferencia ID: " + idPreferencia);
    }
    return resultados;
    }

    @Override
    public TuristaXPreferencia guardar(TuristaXPreferencia turistaXPreferencia) {
        // Validar que la preferencia no sea nula
        if (turistaXPreferencia.getPreferencia() == null || 
        turistaXPreferencia.getPreferencia().get_idPreferencias() == null) {
            throw new IllegalArgumentException("Debe especificar un idPreferencias válido.");
        }

        Preferencias preferenciaExistente = preferenciasRepositorio
            .findById(turistaXPreferencia.getPreferencia().get_idPreferencias())
            .orElseThrow(() -> new IllegalArgumentException("La preferencia no existe."));

        TuristaDTO turistaDTO;
        try {
            turistaDTO = turistaFeignClient.obtenerTurista(turistaXPreferencia.getIdUsuario());
        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con el microservicio de usuarios: " + e.getMessage());
        }

        if (turistaDTO == null) {
            throw new IllegalArgumentException("El turista con ID " + turistaXPreferencia.getIdUsuario() + " no existe.");
        }

        turistaXPreferencia.setPreferencia(preferenciaExistente);
        
        return turistaXPreferenciaRepositorio.save(turistaXPreferencia);
    }

    public boolean existePorId(Long id) {
        return turistaXPreferenciaRepositorio.existsById(id);
    }

    @Override
    public void eliminar(Long id) {
        if (!existePorId(id)) {
            throw new IllegalArgumentException("No se encontró la relación con ID: " + id);
        }
        turistaXPreferenciaRepositorio.deleteById(id);    
    }

    @Override
    public TuristaXPreferencia actualizar(TuristaXPreferencia turistaXPreferencia){
        if (!existePorId(turistaXPreferencia.get_idTuristaXPreferencia())) {
            throw new IllegalArgumentException("No se encontró la relación con ID: " + turistaXPreferencia.get_idTuristaXPreferencia());
        }
        return turistaXPreferenciaRepositorio.save(turistaXPreferencia);
    }

    @Transactional
    public void eliminarPreferenciasPorTurista(Long idTurista) {
        turistaXPreferenciaRepositorio.deleteByidUsuario(idTurista);
    }
}
