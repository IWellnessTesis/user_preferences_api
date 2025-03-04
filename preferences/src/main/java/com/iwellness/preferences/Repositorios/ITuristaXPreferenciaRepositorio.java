package com.iwellness.preferences.Repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iwellness.preferences.Entidades.TuristaXPreferencia;

@Repository
public interface ITuristaXPreferenciaRepositorio extends CrudRepository<TuristaXPreferencia, Long>{

    List<TuristaXPreferencia> findBy_idUsuario(Long _idUsuario);

}
