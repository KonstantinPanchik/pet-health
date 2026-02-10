package org.pethealth.notifications.strategy.appointment.behavior;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.pethealth.notifications.dto.appointment.InitiatorInfo;
import org.pethealth.notifications.repository.UserProjectionRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClinicGetNewAppointmentBehavior implements EmailBehavior{

    private final UserProjectionRepository projectionRepository;

    @Override
    public void sendEmail(InitiatorInfo initiator, AppointmentInfo appointment) {

    }
}
