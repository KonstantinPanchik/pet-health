package org.pethealth.notifications.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemplateType {

    USER_CANCELED(""),
    USER_CREATED("NewAppointmentUserMailTemplate"),
    CLINIC_CANCELED(""),
    CLINIC_VISITED("");

    private final String address;

    public String getTemplateAddress(){
        return address;
    }
}
