package org.pethealth.clinic.mapper;

import org.pethealth.clinic.dto.response.ClinicResponse;
import org.pethealth.clinic.entities.Clinic;
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
}

