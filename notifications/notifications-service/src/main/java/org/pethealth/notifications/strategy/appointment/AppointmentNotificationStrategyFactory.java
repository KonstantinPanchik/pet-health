package org.pethealth.notifications.strategy.appointment;

import lombok.RequiredArgsConstructor;
import org.pethealth.notifications.dto.appointment.AppointmentEventType;
import org.pethealth.notifications.strategy.appointment.behavior.*;
import org.pethealth.notifications.strategy.appointment.imp.SimpleNotificationStrategy;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppointmentNotificationStrategyFactory {

    private final PatientCreateNewAppointmentBehavior patientCreateNewAppointmentBehavior;
    private final ClinicGetNewAppointmentBehavior clinicGetNewAppointmentBehavior;
    private final NoOpBehavior noOpBehavior;
    private final PatientCanceledAppointmentBehavior patientCanceledAppointmentBehavior;
    private final ClinicGetUserCanceledBehavior clinicGetUserCanceledBehavior;
    private final PatientGetClinicCanceledBehavior patientGetClinicCanceledBehavior;
    private final ClinicGetClinicCanceledBehavior clinicGetClinicCanceledBehavior;
    private final PatientVisitedBehavior patientVisitedBehavior;

    public AppointmentNotificationStrategy getAppointmentNotificationStrategy(AppointmentEventType appointmentEventType) {
        return switch (appointmentEventType) {
            case USER_CREATED ->
                    new SimpleNotificationStrategy(patientCreateNewAppointmentBehavior, clinicGetNewAppointmentBehavior);
            case USER_CANCELED ->
                    new SimpleNotificationStrategy(patientCanceledAppointmentBehavior, clinicGetUserCanceledBehavior);
            case CLINIC_CANCELED ->
                    new SimpleNotificationStrategy(patientGetClinicCanceledBehavior, clinicGetClinicCanceledBehavior);
            case CLINIC_VISITED ->
                    new SimpleNotificationStrategy(patientVisitedBehavior, noOpBehavior);
        };
    }
}
