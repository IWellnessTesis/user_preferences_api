package com.iwellness.preferences.Controladores.Rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Entidades.ServicioXPreferencia;
import com.iwellness.preferences.Entidades.TuristaXPreferencia;
import com.iwellness.preferences.Servicios.Rabbit.MensajeServicePreferencesConfig;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/preferencias/publish")
@RequiredArgsConstructor
public class MensajeControllerPreferences {
    private final RabbitTemplate template;

    @PostMapping("/mensaje/enviar/preferencias")
    public String enviarMensajePreferencias(@RequestBody Preferencias preferencias) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonPreferencias = objectMapper.writeValueAsString(preferencias);
            this.template.convertAndSend(MensajeServicePreferencesConfig.EXCHANGE_NAME, MensajeServicePreferencesConfig.ROUTING_KEY_PREFERENCE, jsonPreferencias);
            return "Preferencias publicadas: " + preferencias.getNombre() + " con id: " + preferencias.get_idPreferencias();
        } catch (JsonProcessingException e) {
            return "Error al serializar la preferencia: " + e.getMessage();
        }
    }

    @PostMapping("/mensaje/enviar/servicioxpreferencia")
public String enviarMensajeServicioxProferencia(@RequestBody ServicioXPreferencia servicioxpreferencias) {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // 👈 Esto limpia el JSON
        String jsonServicioXPreferencias = objectMapper.writeValueAsString(servicioxpreferencias);

        this.template.convertAndSend(
            MensajeServicePreferencesConfig.EXCHANGE_NAME,
            MensajeServicePreferencesConfig.ROUTING_KEY_PREFERENCE,
            jsonServicioXPreferencias
        );

        return "Servicios por Preferencias publicadas: " + 
               servicioxpreferencias.get_idServicioXPreferencia() + 
               " con id_servicio: " + servicioxpreferencias.getIdServicio() + 
               " y id_preferencia: " + 
               (servicioxpreferencias.getPreferencia() != null ? servicioxpreferencias.getPreferencia().get_idPreferencias() : "N/A");
    } catch (JsonProcessingException e) {
        return "Error al serializar la preferencia: " + e.getMessage();
    }
}


    @PostMapping("/mensaje/enviar/turistaxpreferencia")
    public String enviarMensajeTuristaxPreferencia(@RequestBody TuristaXPreferencia turistaxpreferencia) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // 👈 Esto limpia el JSON
            String jsonTuristaXPreferencias = objectMapper.writeValueAsString(turistaxpreferencia);
            this.template.convertAndSend(MensajeServicePreferencesConfig.EXCHANGE_NAME, MensajeServicePreferencesConfig.ROUTING_KEY_PREFERENCE, jsonTuristaXPreferencias);

            return "Turista x Preferencia publicada con ID: " + turistaxpreferencia.get_idTuristaXPreferencia()
            + ", Usuario: " + turistaxpreferencia.getIdUsuario()
            + ", Preferencia ID: " + turistaxpreferencia.getPreferencia().get_idPreferencias();

        } catch (JsonProcessingException e) {
            return "Error al serializar la preferencia: " + e.getMessage();
        }
    }

}
