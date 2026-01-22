package org.pethealth.clinic.service.appointment;

import lombok.RequiredArgsConstructor;
import org.pethealth.clinic.entities.Appointment;
import org.pethealth.clinic.mapper.AppointmentMapper;
import org.pethealth.clinic.mapper.InitiatorMapper;
import org.pethealth.notifications.dto.appointment.AppointmentEvent;
import org.pethealth.notifications.dto.appointment.AppointmentEventType;
import org.pethealth.security.model.JwtUserPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentEventService {

    private final AppointmentMapper appointmentMapper;
    private final InitiatorMapper initiatorMapper;

    public AppointmentEvent createEvent(JwtUserPrincipal principal, Appointment appointment, AppointmentEventType eventType) {
        AppointmentEvent event = new AppointmentEvent();
        event.setInitiatorInfo(initiatorMapper.toInitiatorInfo(principal));
        event.setAppointmentInfo(appointmentMapper.toAppointmentInfo(appointment));
        event.setAppointmentEventType(eventType);
        event.setEventTime(LocalDateTime.now());
        
        return event;
    }
}

