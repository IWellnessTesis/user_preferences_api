package com.iwellness.preferences.Servicios.Rabbit;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeServicePreferences {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MensajeServicePreferences(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensaje(String mensaje) {
        String exchange = "my_exchange";       // Nombre del exchange
        String routingKey = "my_routing_key";  // Clave de enrutamiento

        rabbitTemplate.convertAndSend(exchange, routingKey, mensaje);
        System.out.println("Mensaje enviado: " + mensaje);
    }
}
