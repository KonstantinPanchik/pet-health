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
public class PatientGetClinicCanceledBehavior implements EmailBehavior {

    private final EmailService emailService;
    private final UserProjectionRepository userProjectionRepository;

    @Override
    public void sendEmail(InitiatorInfo initiator, AppointmentInfo appointment) {

        if (appointment.getPet() == null || appointment.getPet().getPetOwnerId() == null) {
            log.warn("Cannot notify patient: pet or petOwnerId is missing");
            return;
        }
        Optional<UserProjection> patientOpt = userProjectionRepository.findById(appointment.getPet().getPetOwnerId());
        if (patientOpt.isEmpty()) {
            log.warn("Patient not found for petOwnerId: {}", appointment.getPet().getPetOwnerId());
            return;
        }
        UserProjection patient = patientOpt.get();

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("patient", patient);
        templateVariables.put("initiator", initiator);
        templateVariables.put("appointment", appointment);

        EmailContext context = EmailContext.builder()
                .to(patient.getEmail())
                .subject("Клиника отменила приём")
                .templateType(TemplateType.CLINIC_CANCELED_FOR_PATIENT)
                .context(templateVariables)
                .attachments(Collections.emptyList())
                .build();
        try {
            log.info("Sending clinic-canceled appointment notification to patient");
            emailService.sendHtmlEmail(context);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке email: {}", e.getMessage());
        }
    }
}
