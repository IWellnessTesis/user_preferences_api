package com.iwellness.preferences.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwellness.preferences.Servicios.IPreferenciasServicio;

@RestController
@RequestMapping("/api/preferencias")
public class PreferenciasControlador {

    @Autowired
    private IPreferenciasServicio preferenciasServicio;

    @GetMapping("/all")
    public ResponseEntity<?> buscarTodos(){
        return ResponseEntity.ok(preferenciasServicio.buscarTodos());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(preferenciasServicio.BuscarPorId(id));
    }
}
