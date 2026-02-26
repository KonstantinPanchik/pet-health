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

/**
 * Уведомление клиники о том, что пациент отменил запись.
 * Инициатор — пациент; письмо отправляем владельцу клиники.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ClinicGetUserCanceledBehavior implements EmailBehavior {

    private final EmailService emailService;
    private final UserProjectionRepository userProjectionRepository;

    @Override
    public void sendEmail(InitiatorInfo initiator, AppointmentInfo appointment) {
        if (appointment.getClinic() == null || appointment.getClinic().getClinicOwnerId() == null) {
            log.warn("Cannot notify clinic: clinic or clinicOwnerId is missing");
            return;
        }
        Optional<UserProjection> clinicOwnerOpt = userProjectionRepository.findById(appointment.getClinic().getClinicOwnerId());
        if (clinicOwnerOpt.isEmpty()) {
            log.warn("Clinic owner not found for clinicOwnerId: {}", appointment.getClinic().getClinicOwnerId());
            return;
        }
        UserProjection clinicOwner = clinicOwnerOpt.get();

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("initiator", initiator);
        templateVariables.put("appointment", appointment);

        EmailContext context = EmailContext.builder()
                .to(clinicOwner.getEmail())
                .subject("Пациент отменил запись")
                .templateType(TemplateType.USER_CANCELED_FOR_CLINIC)
                .context(templateVariables)
                .attachments(Collections.emptyList())
                .build();
        try {
            log.info("Sending user-canceled notification to clinic");
            emailService.sendHtmlEmail(context);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке email: {}", e.getMessage());
        }
    }
}
