package org.pethealth.clinic.rabbitmqproducer;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Bean
    public Queue appointmentsQueue() {
        return new Queue(queueName, false);
    }

}
