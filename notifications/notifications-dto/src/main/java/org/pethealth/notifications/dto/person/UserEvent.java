package org.pethealth.notifications.dto.person;

import lombok.Getter;
import lombok.Setter;
import org.pethealth.notifications.dto.base.DomainEvent;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserEvent implements DomainEvent<UserEventType> {

    private String id;
    private String name;
    private String lastName;
    private String email;

    private UserEventType type;
    private LocalDateTime eventTime;

    @Override
    public UserEventType getEventType() {
        return type;
    }

    @Override
    public LocalDateTime occurredAt() {
        return eventTime;
    }
}
