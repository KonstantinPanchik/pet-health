package org.pethealth.notifications.handler.appointment;

import lombok.RequiredArgsConstructor;
import org.pethealth.notifications.dto.appointment.AppointmentEvent;
import org.pethealth.notifications.strategy.appointment.AppointmentNotificationStrategy;
import org.pethealth.notifications.strategy.appointment.AppointmentNotificationStrategyFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppointmentEventHandler {

    private final AppointmentNotificationStrategyFactory appointmentNotificationStrategyFactory;

    @RabbitListener(queues = "${rabbitmq.queue.appointment.name}")
    public void handle(AppointmentEvent event) {
        AppointmentNotificationStrategy appointmentNotificationStrategy
                = appointmentNotificationStrategyFactory.getAppointmentNotificationStrategy(event.getAppointmentEventType());

        appointmentNotificationStrategy.sendPatientEmailNotifications(event.getInitiatorInfo(), event.getAppointmentInfo());
    }
}
