package com.iwellness.preferences.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwellness.preferences.Entidades.ServicioXPreferencia;
import com.iwellness.preferences.Servicios.ServicioXPreferenciaServicio.IServicioXPreferenciaServicio;

@RestController
@RequestMapping("/api/servicioXPreferencia")
public class ServicioXPreferenciaControlador {

    @Autowired
    private IServicioXPreferenciaServicio servicioXPreferenciaServicio;

    @GetMapping("/servicio/{idServicio}")
    public List<ServicioXPreferencia> obtenerPorServicio(@PathVariable Long idServicio) {
        return servicioXPreferenciaServicio.obtenerPorIdServicio(idServicio);
    }

    @PostMapping
    public ServicioXPreferencia asociarPreferencia(@RequestBody ServicioXPreferencia servicioXPreferencia) {
        return servicioXPreferenciaServicio.guardar(servicioXPreferencia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsociacion(@PathVariable Long id) {
        servicioXPreferenciaServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
