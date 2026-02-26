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

@Component
@RequiredArgsConstructor
@Slf4j
public class PatientCanceledAppointmentBehavior implements EmailBehavior {

    private final EmailService emailService;

    @Override
    public void sendEmail(InitiatorInfo initiator, AppointmentInfo appointment) {
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("initiator", initiator);
        templateVariables.put("appointment", appointment);

        EmailContext context = EmailContext.builder()
                .to(initiator.getInitiatorEmail())
                .subject("Отмена записи на приём")
                .templateType(TemplateType.USER_CANCELED_FOR_PATIENT)
                .context(templateVariables)
                .attachments(Collections.emptyList())
                .build();
        try {
            log.info("Sending user-canceled appointment notification to patient");
            emailService.sendHtmlEmail(context);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке email: {}", e.getMessage());
        }
    }
}
