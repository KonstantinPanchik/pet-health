package org.pethealth.notifications.strategy.appointment.behavior;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.pethealth.notifications.dto.appointment.InitiatorInfo;
import org.pethealth.notifications.model.EmailContext;
import org.pethealth.notifications.model.enums.TemplateType;
import org.pethealth.notifications.service.EmailService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Уведомление клиники о том, что приём отменён (подтверждение для владельца клиники).
 * При CLINIC_CANCELED инициатор — владелец клиники, письмо ему же.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ClinicGetClinicCanceledBehavior implements EmailBehavior {

    private final EmailService emailService;

    @Override
    public void sendEmail(InitiatorInfo initiator, AppointmentInfo appointment) {
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("initiator", initiator);
        templateVariables.put("appointment", appointment);

        EmailContext context = EmailContext.builder()
                .to(initiator.getInitiatorEmail())
                .subject("Подтверждение отмены приёма")
                .templateType(TemplateType.CLINIC_CANCELED_FOR_CLINIC)
                .context(templateVariables)
                .attachments(Collections.emptyList())
                .build();
        try {
            log.info("Sending clinic-canceled confirmation to clinic owner");
            emailService.sendHtmlEmail(context);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке email: {}", e.getMessage());
        }
    }
}
