package com.iwellness.preferences.Servicios.Rabbit;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MensajeServicePreferences {
    private final RabbitTemplate rabbitTemplate;

    public MensajeServicePreferences(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensaje(String mensaje) {
        rabbitTemplate.convertAndSend(MensajeServicePreferencesConfig.EXCHANGE_NAME, MensajeServicePreferencesConfig.ROUTING_KEY_PREFERENCE, mensaje);
        System.out.println("Mensaje enviado: " + mensaje);
    }
}
