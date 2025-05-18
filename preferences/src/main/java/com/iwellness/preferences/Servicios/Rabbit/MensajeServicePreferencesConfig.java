package com.iwellness.preferences.Servicios.Rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class MensajeServicePreferencesConfig {

    public static final String EXCHANGE_NAME = "message_exchange";
    //CASO 2
    public static final String QUEUE_NAME_TURISTAXPREFERENCIA = "my_queue_turistxpreferences";
    public static final String ROUTING_KEY_TURISTAXPREFERENCIA = "my_routing_key_turistxpreferences";
    //CASO 4
    public static final String QUEUE_NAME_TURISTAXPREFERENCIA_ESTADOCIVIL = "my_queue_turistxpreferences_estadocivil";
    public static final String ROUTING_KEY_TURISTAXPREFERENCIA_ESTADOCIVIL = "my_routing_key_turistxpreferences_estadocivil";

    @Bean
    public TopicExchange topicexchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connection) {
        final var template = new RabbitTemplate(connection);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public Queue queueTuristxPreferencesEstadoCivil() {
        return new Queue(QUEUE_NAME_TURISTAXPREFERENCIA_ESTADOCIVIL, true);
    }

   

    @Bean
    public Queue queueTuristxPreferences() {
        return new Queue(QUEUE_NAME_TURISTAXPREFERENCIA, true);
    }


    @Bean
    public Binding bindingTuristxPreferences(
        @Qualifier("queueTuristxPreferences") Queue queue,
        TopicExchange exchange
    ) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_TURISTAXPREFERENCIA);
    }

    @Bean
    public Binding bindingTuristxPreferencesEstadoCivil(
        @Qualifier("queueTuristxPreferencesEstadoCivil") Queue queue,
        TopicExchange exchange
    ) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_TURISTAXPREFERENCIA_ESTADOCIVIL);
    }
}
