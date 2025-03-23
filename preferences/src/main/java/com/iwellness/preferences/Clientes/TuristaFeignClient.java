package com.iwellness.preferences.Clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.iwellness.preferences.DTO.TuristaDTO;

@FeignClient(name = "turista-ms", url = "http://localhost:8080/auth")
public interface TuristaFeignClient {

    @GetMapping("/buscar/{idTurista}")
    TuristaDTO  obtenerTurista(@PathVariable("idTurista") Long idTurista);

}
