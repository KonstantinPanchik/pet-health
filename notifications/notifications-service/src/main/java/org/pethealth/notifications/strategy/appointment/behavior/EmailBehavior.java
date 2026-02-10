package org.pethealth.notifications.strategy.appointment.behavior;

import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.pethealth.notifications.dto.appointment.InitiatorInfo;

public interface EmailBehavior {

    void sendEmail(InitiatorInfo initiator, AppointmentInfo appointment);
}
