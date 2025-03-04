package com.iwellness.preferences.Repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iwellness.preferences.Entidades.ServicioXPreferencia;

@Repository
public interface IServicioXPreferenciaRepositorio extends CrudRepository<ServicioXPreferencia, Long>{

    List<ServicioXPreferencia> findBy_idServicio(Long _idServicio);

}
