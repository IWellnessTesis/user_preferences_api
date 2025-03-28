package com.iwellness.preferences.Controladores.Rabbit;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.iwellness.preferences.Servicios.Rabbit.MensajeServicePreferences;

public class MensajeControllerPreferences {
      private final MensajeServicePreferences mensajeServicePreferences;

    public MensajeControllerPreferences(MensajeServicePreferences mensajeServicePreferences) {
        this.mensajeServicePreferences = mensajeServicePreferences;
    }

    @PostMapping("/enviar")
    public String enviarMensaje(@RequestBody String mensaje) {
        mensajeServicePreferences.enviarMensaje(mensaje);
        return "Mensaje enviado: " + mensaje;
    }
}
