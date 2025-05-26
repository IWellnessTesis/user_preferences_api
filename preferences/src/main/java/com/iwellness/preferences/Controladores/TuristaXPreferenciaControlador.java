package com.iwellness.preferences.Controladores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

import com.iwellness.preferences.Clientes.TuristaFeignClient;
import com.iwellness.preferences.DTO.TuristaDTO;
import com.iwellness.preferences.DTO.UsuarioInteresesDTO;
import com.iwellness.preferences.DTO.UsuarioInteresesEstadoDTO;
import com.iwellness.preferences.Entidades.TuristaXPreferencia;
import com.iwellness.preferences.Servicios.TuristaXPreferenciaServicio.ITuristaXPreferenciaServicio;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/turistaXPreferencia")
public class TuristaXPreferenciaControlador {

    @Autowired
    private ITuristaXPreferenciaServicio turistaXPreferenciaServicio;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TuristaFeignClient turistaFeignClient;

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
        try {

            // Obtener las preferencias del turista
            List<TuristaXPreferencia> preferencias = turistaXPreferenciaServicio.obtenerPorIdUsuario(idUsuario);

            if (preferencias.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron preferencias para el usuario con ID: " + idUsuario);
            }

            // Obtener el país desde el microservicio de usuarios
            TuristaDTO turista = turistaFeignClient.obtenerTurista(idUsuario);

            //Acceder al pais del turista (anidado)
            String pais = turista.getTuristaInfo().getPais();


            System.out.println("Pais del turista: " + pais);

            // Crear el DTO con la información del turista -> Caso 2
            UsuarioInteresesDTO usuarioInteresesDTO = new UsuarioInteresesDTO();
            usuarioInteresesDTO.setUserId(idUsuario.toString());
            usuarioInteresesDTO.setPais(pais);


            //Acceder al estado civil del turista (anidado)
            String estadoCivil = turista.getTuristaInfo().getEstadoCivil();
            String genero = turista.getTuristaInfo().getGenero();

            // Crear el DTO con la información del turista -> Caso 4
            UsuarioInteresesEstadoDTO usuarioInteresesEstadoDTO = new UsuarioInteresesEstadoDTO();
            usuarioInteresesEstadoDTO.setUserId(idUsuario.toString());
            usuarioInteresesEstadoDTO.setEstadoCivil(estadoCivil);
            usuarioInteresesEstadoDTO.setGenero(genero);

            // Obtener los nombres de las preferencias
            List<String> intereses = preferencias.stream()
                    .map(pref -> pref.getPreferencia() != null ? pref.getPreferencia().getNombre() : "Desconocido")
                    .collect(Collectors.toList());

            usuarioInteresesDTO.setIntereses(intereses);
            usuarioInteresesEstadoDTO.setIntereses(intereses);

            System.out.println("Usuario intereses: " + usuarioInteresesDTO);

            // Enviar a la cola -> Caso 2
            rabbitTemplate.convertAndSend("message_exchange", "my_routing_key_turistxpreferences", usuarioInteresesDTO);
            //Enviar a la cola -> Caso 4
            rabbitTemplate.convertAndSend("message_exchange", "my_routing_key_turistxpreferences_estadocivil", usuarioInteresesEstadoDTO);


            return ResponseEntity.ok(preferencias);

        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener información del usuario: " + e.getMessage());
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

    @DeleteMapping("/eliminarPorTurista/{idTurista}")
    public ResponseEntity<String> eliminarPreferenciasPorTurista(@PathVariable Long idTurista) {
        turistaXPreferenciaServicio.eliminarPreferenciasPorTurista(idTurista);
        return ResponseEntity.ok("Preferencias del servicio eliminadas correctamente");
    }

}
