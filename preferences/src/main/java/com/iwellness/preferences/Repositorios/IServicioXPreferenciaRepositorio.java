package com.iwellness.preferences.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iwellness.preferences.Entidades.ServicioXPreferencia;

@Repository
public interface IServicioXPreferenciaRepositorio extends CrudRepository<ServicioXPreferencia, Long>{

    List<ServicioXPreferencia> findByIdServicio(Long idServicio);

    @Query("SELECT sxp FROM ServicioXPreferencia sxp WHERE sxp.preferencia._idPreferencias = :idPreferencia")
    List<ServicioXPreferencia> findByPreferencia_IdPreferencias (@Param("idPreferencia")Long idPreferencia);
}
