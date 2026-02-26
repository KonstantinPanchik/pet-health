package org.pethealth.clinic.mapper;

import org.pethealth.clinic.dto.response.PetResponse;
import org.pethealth.clinic.dto.response.PetShortResponse;
import org.pethealth.clinic.entities.Pet;
import org.pethealth.notifications.dto.appointment.PetInfo;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public PetResponse toPetResponse(Pet pet) {
        return PetResponse.builder()
                .id(pet.getId())
                .name(pet.getName())
                .breed(pet.getBreed())
                .description(pet.getDescription())
                .yearOfBirth(pet.getYearOfBirth())
                .ownerId(pet.getOwnerId())
                .petType(pet.getPetType())
                .build();
    }

    public PetShortResponse toPetShortResponse(Pet pet) {
        return PetShortResponse.builder()
                .id(pet.getId())
                .name(pet.getName())
                .petType(pet.getPetType())
                .build();
    }

    public PetInfo toPetInfo(Pet pet) {
        PetInfo petInfo = new PetInfo();
        petInfo.setPetOwnerId(pet.getOwnerId());
        petInfo.setName(pet.getName());
        petInfo.setType(petInfo.getType());
        petInfo.setFullYearsByYearOfBirth(pet.getYearOfBirth());
        return petInfo;
    }
}
