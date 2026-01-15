package org.pethealth.notifications.handler.appointment;

import lombok.RequiredArgsConstructor;
import org.pethealth.notifications.dto.appointment.AppointmentEvent;
import org.pethealth.notifications.strategy.appointment.AppointmentNotificationStrategy;
import org.pethealth.notifications.strategy.appointment.AppointmentNotificationStrategyFactory;

@RequiredArgsConstructor
public class AppointmentEventHandler {

    private final AppointmentNotificationStrategyFactory appointmentNotificationStrategyFactory;

    public void handle(AppointmentEvent event) {
        AppointmentNotificationStrategy appointmentNotificationStrategy
                = appointmentNotificationStrategyFactory.getAppointmentNotificationStrategy(event.getAppointmentEventType());
        appointmentNotificationStrategy.sendEmailNotifications(event.getInitiatorInfo(), event.getAppointmentInfo());
    }
}
