package org.pethealth.notifications.strategy.appointment.imp;

import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.pethealth.notifications.dto.appointment.InitiatorInfo;
import org.pethealth.notifications.strategy.appointment.AppointmentNotificationStrategy;
import org.springframework.stereotype.Component;

@Component
public class UserCancelAppointmentNotificationStrategy implements AppointmentNotificationStrategy {

    @Override
    public void sendEmailNotifications(InitiatorInfo initiator, AppointmentInfo appointment) {

    }
}
