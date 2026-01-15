package org.pethealth.notifications.dto.appointment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentInfo {

    private ClinicInfo clinic;

    private PetInfo petInfo;

    private LocalDateTime dateTime;

    private String reason;

}
