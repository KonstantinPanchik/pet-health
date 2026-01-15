package org.pethealth.notifications.strategy.appointment;

import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.pethealth.notifications.dto.appointment.InitiatorInfo;

public interface AppointmentNotificationStrategy {

    void sendEmailNotifications(InitiatorInfo initiator, AppointmentInfo appointment);
}
