package com.iwellness.preferences.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Servicios.PreferenciasServicio.IPreferenciasServicio;

@RestController
@RequestMapping("/api/preferencias")
public class PreferenciasControlador {

    @Autowired
    private IPreferenciasServicio preferenciasServicio;

    @GetMapping("/all")
    public ResponseEntity<?> buscarTodos(){
        return ResponseEntity.ok(preferenciasServicio.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(preferenciasServicio.BuscarPorId(id));
    }

    @PostMapping
    public Preferencias crearPreferencia(@RequestBody Preferencias preferencia) {
        return preferenciasServicio.guardar(preferencia);
    }

     @PutMapping("/editar/{id}")
    public ResponseEntity<Preferencias> actualizarPreferencia(@PathVariable Long id, @RequestBody Preferencias nuevaPreferencia) {
        if (!preferenciasServicio.BuscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        nuevaPreferencia.set_idPreferencias(id);
        return ResponseEntity.ok(preferenciasServicio.guardar(nuevaPreferencia));
    }

     @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPreferencia(@PathVariable Long id) {
        if (!preferenciasServicio.BuscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        preferenciasServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
