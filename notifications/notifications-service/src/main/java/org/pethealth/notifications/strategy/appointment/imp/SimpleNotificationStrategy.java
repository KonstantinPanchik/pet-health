package org.pethealth.notifications.strategy.appointment.imp;

import lombok.AllArgsConstructor;
import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.pethealth.notifications.dto.appointment.InitiatorInfo;
import org.pethealth.notifications.strategy.appointment.AppointmentNotificationStrategy;
import org.pethealth.notifications.strategy.appointment.behavior.EmailBehavior;

@AllArgsConstructor
public class SimpleNotificationStrategy implements AppointmentNotificationStrategy {

    private EmailBehavior patientEmailBehavior;
    private EmailBehavior clinicEmailBehavior;

    @Override
    public void setPatientEmailBehavior(EmailBehavior patientEmailBehavior) {
        this.patientEmailBehavior = patientEmailBehavior;
    }

    @Override
    public void setClinicEmailBehavior(EmailBehavior clinicEmailBehavior) {
        this.clinicEmailBehavior = clinicEmailBehavior;
    }

    @Override
    public void sendPatientEmailNotifications(InitiatorInfo initiator, AppointmentInfo appointment) {
        patientEmailBehavior.sendEmail(initiator, appointment);
    }

    @Override
    public void sendClinicEmailNotifications(InitiatorInfo initiator, AppointmentInfo appointment) {
        clinicEmailBehavior.sendEmail(initiator, appointment);
    }
}
