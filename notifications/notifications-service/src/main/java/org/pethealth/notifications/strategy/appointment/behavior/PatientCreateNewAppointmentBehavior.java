package org.pethealth.notifications.strategy.appointment.behavior;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Slf4j
public class PatientCreateNewAppointmentBehavior implements EmailBehavior{

    private final EmailService emailService;

    @Override
    public void sendEmail(InitiatorInfo initiator, AppointmentInfo appointment)  {
        System.out.println("New Appointment Notification Strategy started");

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("initiator", initiator);
        templateVariables.put("appointment", appointment);

        EmailContext context = EmailContext.builder()
                .to(initiator.getInitiatorEmail())
                .subject("Запись на приём")
                .templateType(TemplateType.USER_CREATED_FOR_PATIENT)
                .context(templateVariables)
                .attachments(Collections.emptyList())
                .build();
        try {
            log.info("Sending new appointment notification");
            emailService.sendHtmlEmail(context);
            log.info("New Appointment Notification Strategy completed");
        } catch (MessagingException e) {
            System.err.println("Ошибка при отправке email: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
