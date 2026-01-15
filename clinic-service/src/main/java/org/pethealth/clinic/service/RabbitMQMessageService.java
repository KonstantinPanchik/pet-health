package org.pethealth.clinic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQMessageService {

    private final RabbitTemplate template;

    @Value("${rabbitmq.queue.name}")
    private String messageQueue;


    public void sendMessage(Object message) {
        template.convertAndSend(messageQueue, message);
    }

}