package org.pethealth.notifications.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemplateType {

    USER_CANCELED_FOR_PATIENT(""),
    USER_CANCELED_FOR_CLINIC(""),
    USER_CREATED_FOR_PATIENT("NewAppointmentUserMailTemplate"),
    USER_CREATED_FOR_CLINIC(""),
    CLINIC_CANCELED_FOR_PATIENT(""),
    CLINIC_CANCELED_FOR_CLINIC(""),
    CLINIC_VISITED_FOR_PATIENT("");


    private final String address;

    public String getTemplateAddress() {
        return address;
    }
}
