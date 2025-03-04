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

import com.iwellness.preferences.Entidades.TuristaXPreferencia;
import com.iwellness.preferences.Servicios.TuristaXPreferenciaServicio.ITuristaXPreferenciaServicio;

@RestController
@RequestMapping("/api/turistaXPreferencia")
public class TuristaXPreferenciaControlador {

    @Autowired
    private ITuristaXPreferenciaServicio turistaXPreferenciaServicio;

    @GetMapping("/usuario/{idUsuario}")
    public List<TuristaXPreferencia> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return turistaXPreferenciaServicio.obtenerPorIdUsuario(idUsuario);
    }

    @PostMapping
    public TuristaXPreferencia asociarPreferencia(@RequestBody TuristaXPreferencia turistaXPreferencia) {
        return turistaXPreferenciaServicio.guardar(turistaXPreferencia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsociacion(@PathVariable Long id) {
        turistaXPreferenciaServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
