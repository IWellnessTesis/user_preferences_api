package com.iwellness.preferences.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin(origins = "http://localhost:4200")
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
        try {
            return ResponseEntity.ok(preferenciasServicio.BuscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la preferencia con ID: " + id);
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarPreferencia(@RequestBody Preferencias preferencia) {
        try {
            return ResponseEntity.ok(preferenciasServicio.guardar(preferencia));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar la preferencia: " + e.getMessage());
        } 
    }

    @PutMapping("/editar")
    public ResponseEntity<?> actualizarPreferencia(@RequestBody Preferencias preferencia) {
        try {
            return ResponseEntity.ok(preferenciasServicio.actualizar(preferencia));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar la preferencia: " + e.getMessage());
        }
    }

     @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPreferencia(@PathVariable Long id) {
        try {
            preferenciasServicio.eliminar(id);
            return ResponseEntity.ok("Preferencia eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la Preferencia con ID: " + id);
        }
    }
}
