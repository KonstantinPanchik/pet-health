package org.pethealth.notifications.dto.base;

import java.time.LocalDateTime;

public interface DomainEvent<T> {
    T getEventType();
    LocalDateTime occurredAt();
}
