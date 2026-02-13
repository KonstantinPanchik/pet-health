package org.pethealth.notifications.dto.service;

import lombok.AllArgsConstructor;
import org.pethealth.notifications.dto.base.DomainEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@AllArgsConstructor
public class NotificationQueueMessageService {

    private final RabbitTemplate template;
    private final String messageQueue;

    public void sendMessage(DomainEvent<?> message) {
        template.convertAndSend(messageQueue, message);
    }

}
