package org.pethealth.notifications.strategy.appointment.imp;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.pethealth.notifications.dto.appointment.InitiatorInfo;
import org.pethealth.notifications.model.EmailContext;
import org.pethealth.notifications.model.enums.TemplateType;
import org.pethealth.notifications.repository.UserProjectionRepository;
import org.pethealth.notifications.service.EmailService;
import org.pethealth.notifications.strategy.appointment.AppointmentNotificationStrategy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewAppointmentNotificationStrategy implements AppointmentNotificationStrategy {

    private final EmailService emailService;
    private final UserProjectionRepository projectionRepository;

    @Override
    public void sendEmailNotifications(InitiatorInfo initiator, AppointmentInfo appointment) {
        System.out.println("New Appointment Notification Strategy started");

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("initiator", initiator);
        templateVariables.put("appointment", appointment);

        EmailContext context = EmailContext.builder()
                .to(initiator.getInitiatorEmail())
                .subject("Запись на приём")
                .templateType(TemplateType.USER_CREATED)
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
