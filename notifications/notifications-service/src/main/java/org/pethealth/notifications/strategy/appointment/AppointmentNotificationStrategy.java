package org.pethealth.notifications.strategy.appointment;

import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.pethealth.notifications.dto.appointment.InitiatorInfo;
import org.pethealth.notifications.strategy.appointment.behavior.EmailBehavior;

public interface AppointmentNotificationStrategy {

    void setPatientEmailBehavior(EmailBehavior patientEmailBehavior);

    void setClinicEmailBehavior(EmailBehavior clinicEmailBehavior);

    void sendPatientEmailNotifications(InitiatorInfo initiator, AppointmentInfo appointment);

    void sendClinicEmailNotifications(InitiatorInfo initiator, AppointmentInfo appointment);

}
