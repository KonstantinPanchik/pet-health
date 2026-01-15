package org.pethealth.notifications.strategy.appointment;

import lombok.RequiredArgsConstructor;
import org.pethealth.notifications.dto.appointment.AppointmentEventType;
import org.pethealth.notifications.strategy.appointment.imp.ClinicCancelAppointmentNotificationStrategy;
import org.pethealth.notifications.strategy.appointment.imp.ClinicVisitAppointmentNotificationStrategy;
import org.pethealth.notifications.strategy.appointment.imp.NewAppointmentNotificationStrategy;
import org.pethealth.notifications.strategy.appointment.imp.UserCancelAppointmentNotificationStrategy;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppointmentNotificationStrategyFactory {

    private final NewAppointmentNotificationStrategy newAppointmentNotificationStrategy;
    private final ClinicCancelAppointmentNotificationStrategy clinicCancelAppointmentNotificationStrategy;
    private final ClinicVisitAppointmentNotificationStrategy clinicVisitAppointmentNotificationStrategy;
    private final UserCancelAppointmentNotificationStrategy userCancelAppointmentNotificationStrategy;

    public AppointmentNotificationStrategy getAppointmentNotificationStrategy(AppointmentEventType appointmentEventType) {
        return switch (appointmentEventType) {
            case USER_CREATED -> newAppointmentNotificationStrategy;
            case USER_CANCELED -> userCancelAppointmentNotificationStrategy;
            case CLINIC_VISITED -> clinicVisitAppointmentNotificationStrategy;
            case CLINIC_CANCELED -> clinicCancelAppointmentNotificationStrategy;
        };
    }
}
