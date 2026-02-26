package org.pethealth.notifications.strategy.appointment.behavior;

import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.pethealth.notifications.dto.appointment.InitiatorInfo;
import org.springframework.stereotype.Component;

/**
 * Поведение «ничего не делать» — не отправляет email.
 * Используется когда уведомление данному получателю не требуется.
 */
@Component
public class NoOpBehavior implements EmailBehavior {

    @Override
    public void sendEmail(InitiatorInfo initiator, AppointmentInfo appointment) {
        // intentionally do nothing
    }
}
