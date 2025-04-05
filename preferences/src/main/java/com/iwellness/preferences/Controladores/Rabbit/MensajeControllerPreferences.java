package com.iwellness.preferences.Controladores.Rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Servicios.Rabbit.MensajeServicePreferences;
import com.iwellness.preferences.Servicios.Rabbit.MensajeServicePreferencesConfig;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/preferencias/publish")
@RequiredArgsConstructor
public class MensajeControllerPreferences {
    private final RabbitTemplate template;

    @PostMapping("/mensaje/enviar")
    public String enviarMensaje(@RequestBody Preferencias preferencias) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPreferencias = objectMapper.writeValueAsString(preferencias);
            this.template.convertAndSend(MensajeServicePreferencesConfig.EXCHANGE_NAME, MensajeServicePreferencesConfig.ROUTING_KEY_PREFERENCE, jsonPreferencias);
            return "Preferencias publicadas: " + preferencias.getNombre() + " con id: " + preferencias.get_idPreferencias();
        } catch (JsonProcessingException e) {
            return "Error al serializar la preferencia: " + e.getMessage();
        }
    }
}
