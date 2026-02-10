package org.pethealth.notifications.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemplateType {

    USER_CANCELED_FOR_PATIENT("UserCanceledForPatientMailTemplate"),
    USER_CANCELED_FOR_CLINIC("UserCanceledForClinicMailTemplate"),
    USER_CREATED_FOR_PATIENT("NewAppointmentUserMailTemplate"),
    USER_CREATED_FOR_CLINIC("UserCreatedForClinicMailTemplate"),
    CLINIC_CANCELED_FOR_PATIENT("ClinicCanceledForPatientMailTemplate"),
    CLINIC_CANCELED_FOR_CLINIC("ClinicCanceledForClinicMailTemplate"),
    CLINIC_VISITED_FOR_PATIENT("ClinicVisitedForPatientMailTemplate");

    private final String address;

    public String getTemplateAddress() {
        return address;
    }
}
