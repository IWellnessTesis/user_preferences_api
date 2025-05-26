package com.iwellness.preferences.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iwellness.preferences.Entidades.TuristaXPreferencia;

@Repository
public interface ITuristaXPreferenciaRepositorio extends CrudRepository<TuristaXPreferencia, Long>{

    List<TuristaXPreferencia> findByIdUsuario(Long idUsuario);

    @Query("SELECT sxp FROM TuristaXPreferencia sxp WHERE sxp.preferencia._idPreferencias = :idPreferencia")
    List<TuristaXPreferencia> findByPreferencia_IdPreferencias (@Param("idPreferencia")Long idPreferencia);

    void deleteByidUsuario(Long idUsuario);

}
