package com.iwellness.preferences.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/all")
    public ResponseEntity<?> obtenerTodas() {
        return ResponseEntity.ok(turistaXPreferenciaServicio.buscarTodos());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody TuristaXPreferencia nuevaRelacion) {
        try {
            return ResponseEntity.ok(turistaXPreferenciaServicio.guardar(nuevaRelacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar el turistaXpreferencia: " + e.getMessage());
        } 
    }

    @PutMapping("/editar")
    public ResponseEntity<?> actualizarPreferencia(@RequestBody TuristaXPreferencia turistaXPreferencia) {
        
        try {
            return ResponseEntity.ok(turistaXPreferenciaServicio.actualizar(turistaXPreferencia));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el turistaXPreferencia: " + e.getMessage());
        }
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            turistaXPreferenciaServicio.eliminar(id);
            return ResponseEntity.ok("turistaXpreferencia eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el turistaXpreferencia con ID: " + id);
        }    
    }

    @GetMapping("/turista/{idUsuario}")
    public ResponseEntity<?> obtenerPorTurista(@PathVariable Long idUsuario) {
        try{
            return ResponseEntity.ok(turistaXPreferenciaServicio.obtenerPorIdUsuario(idUsuario));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningun usuario con ID: " + idUsuario);
        }
    }

    @GetMapping("/preferencia/{idPreferencia}")
    public ResponseEntity<?> obtenerPorPreferencia(@PathVariable Long idPreferencia) {
        try{
            return ResponseEntity.ok(turistaXPreferenciaServicio.findByPreferencia_IdPreferencias (idPreferencia));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna preferencia con ID: " + idPreferencia);
        }
    }

}
