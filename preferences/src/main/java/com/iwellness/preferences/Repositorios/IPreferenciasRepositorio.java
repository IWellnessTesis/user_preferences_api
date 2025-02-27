package com.iwellness.preferences.Repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iwellness.preferences.Entidades.Preferencias;

@Repository
public interface IPreferenciasRepositorio  extends CrudRepository<Preferencias, Long>{

    
}
