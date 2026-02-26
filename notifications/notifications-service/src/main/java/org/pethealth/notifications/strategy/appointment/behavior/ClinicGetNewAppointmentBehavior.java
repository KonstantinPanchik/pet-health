package org.pethealth.notifications.strategy.appointment.behavior;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.pethealth.notifications.dto.appointment.InitiatorInfo;
import org.pethealth.notifications.model.EmailContext;
import org.pethealth.notifications.model.enums.TemplateType;
import org.pethealth.notifications.repository.UserProjectionRepository;
import org.pethealth.notifications.repository.entity.UserProjection;
import org.pethealth.notifications.service.EmailService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClinicGetNewAppointmentBehavior implements EmailBehavior{

    private final UserProjectionRepository projectionRepository;
    private final EmailService emailService;

    @Override
    public void sendEmail(InitiatorInfo initiator, AppointmentInfo appointment) {

        Optional<UserProjection> byId = projectionRepository.findById(appointment.getClinic().getClinicOwnerId());
        if (byId.isEmpty()) {
            return;
        }
        UserProjection clinicOwner = byId.get();

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("clinicOwner", clinicOwner);
        templateVariables.put("initiator", initiator);
        templateVariables.put("appointment", appointment);

        EmailContext context = EmailContext.builder()
                .to(clinicOwner.getEmail())
                .subject("Новая запись на приём")
                .templateType(TemplateType.USER_CREATED_FOR_CLINIC)
                .context(templateVariables)
                .attachments(Collections.emptyList())
                .build();
        try {
            log.info("Sending user-created appointment notification to clinic owner");
            emailService.sendHtmlEmail(context);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке email: {}", e.getMessage());
        }
    }
}
