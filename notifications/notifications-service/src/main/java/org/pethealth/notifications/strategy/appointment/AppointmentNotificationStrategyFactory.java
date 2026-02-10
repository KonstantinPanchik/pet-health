package org.pethealth.notifications.strategy.appointment;

import lombok.RequiredArgsConstructor;
import org.pethealth.notifications.dto.appointment.AppointmentEventType;
import org.pethealth.notifications.strategy.appointment.behavior.ClinicGetNewAppointmentBehavior;
import org.pethealth.notifications.strategy.appointment.behavior.PatientCreateNewAppointmentBehavior;
import org.pethealth.notifications.strategy.appointment.imp.SimpleNotificationStrategy;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppointmentNotificationStrategyFactory {

    private final PatientCreateNewAppointmentBehavior patientCreateNewAppointmentBehavior;
    private final ClinicGetNewAppointmentBehavior clinicGetNewAppointmentBehavior;


    public AppointmentNotificationStrategy getAppointmentNotificationStrategy(AppointmentEventType appointmentEventType) {
        return switch (appointmentEventType) {
            case USER_CREATED ->
                    new SimpleNotificationStrategy(patientCreateNewAppointmentBehavior, clinicGetNewAppointmentBehavior);
            case USER_CANCELED -> userCancelAppointmentNotificationStrategy;
            case CLINIC_VISITED -> clinicVisitAppointmentNotificationStrategy;
            case CLINIC_CANCELED -> clinicCancelAppointmentNotificationStrategy;
        };
    }
}
