package org.pethealth.clinic.mapper;

import org.pethealth.clinic.dto.response.ClinicResponse;
import org.pethealth.clinic.entities.Clinic;
import org.pethealth.notifications.dto.appointment.ClinicInfo;
import org.springframework.stereotype.Component;

@Component
public class ClinicMapper {

    public ClinicResponse toClinicResponse(Clinic clinic) {
        return ClinicResponse.builder()
                .id(clinic.getId())
                .name(clinic.getName())
                .city(clinic.getCity())
                .address(clinic.getAddress())
                .phone(clinic.getPhone())
                .ownerId(clinic.getOwnerId())
                .build();
    }

    public ClinicInfo toClinicInfo(Clinic clinic) {
        ClinicInfo clinicInfo = new ClinicInfo();
        clinicInfo.setClinicAddress(clinic.getCity() + " " + clinic.getAddress());
        clinicInfo.setClinicName(clinic.getName());
        clinicInfo.setClinicOwnerId(clinic.getOwnerId());
        return clinicInfo;
    }
}

