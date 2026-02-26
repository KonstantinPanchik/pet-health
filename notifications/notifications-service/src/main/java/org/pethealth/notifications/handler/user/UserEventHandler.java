package org.pethealth.notifications.handler.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.notifications.dto.person.UserEvent;
import org.pethealth.notifications.service.UserProjectionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@Slf4j
public class UserEventHandler {

    private final UserProjectionService userService;

    @RabbitListener(queues = "${rabbitmq.queue.user.name}")
    public void handle(UserEvent event) {
        log.info("Received user event: {}", event);
        userService.updatePerson(event);
    }
}
