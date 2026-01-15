package org.pethealth.notifications.dto.appointment;

import lombok.Getter;
import lombok.Setter;
import org.pethealth.notifications.dto.base.DomainEvent;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentEvent implements DomainEvent<AppointmentEventType> {

    private InitiatorInfo initiatorInfo;

    private AppointmentInfo appointmentInfo;

    private AppointmentEventType appointmentEventType;

    private LocalDateTime eventTime;

    @Override
    public AppointmentEventType getEventType() {
        return appointmentEventType;
    }

    @Override
    public LocalDateTime occurredAt() {
        return eventTime;
    }
}
