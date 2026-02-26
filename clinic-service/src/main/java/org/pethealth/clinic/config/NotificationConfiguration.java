package org.pethealth.clinic.config;

import lombok.RequiredArgsConstructor;
import org.pethealth.notifications.dto.service.NotificationQueueMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NotificationConfiguration {

private final RabbitTemplate rabbitTemplate;

@Bean
    public NotificationQueueMessageService notificationQueueMessageService() {
    return new NotificationQueueMessageService(rabbitTemplate, org.pethealth.notifications.dto.NotificationConfiguration.appointmentsQueueName);
}
}
