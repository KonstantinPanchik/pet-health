package org.pethealth.notifications.dto;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {

    public static final String appointmentsQueueName = "appointment_queue";
    public static final String userQueueName = "user_queue";

    @Bean
    public Queue appointmentsQueue() {
        return new Queue(appointmentsQueueName, false);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(userQueueName, false);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
