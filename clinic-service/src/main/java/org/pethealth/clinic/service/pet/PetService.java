package org.pethealth.clinic.service.pet;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.clinic.dto.request.PetRequest;
import org.pethealth.clinic.dto.response.PetResponse;
import org.pethealth.clinic.dto.response.PetShortResponse;
import org.pethealth.clinic.entities.Pet;
import org.pethealth.clinic.mapper.PetMapper;
import org.pethealth.clinic.repository.PetRepository;
import org.pethealth.security.converter.JwtUserConverter;
import org.pethealth.security.model.JwtUserPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service

public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final JwtUserConverter jwtUserConverter;

    @Transactional
    public PetResponse createPet(Jwt jwt, PetRequest petRequest) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);
        Pet pet = new Pet();
        pet.setName(petRequest.getName());
        pet.setBreed(petRequest.getBreed());
        pet.setDescription(petRequest.getDescription());
        pet.setYearOfBirth(petRequest.getYearOfBirth());
        pet.setOwnerId(principal.getSub());
        pet.setPetType(petRequest.getPetType());
        return petMapper.toPetResponse(petRepository.save(pet));
    }

    @Transactional
    public PetResponse updatePet(Jwt jwt, Long petId, PetRequest petRequest) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);

        Pet petById = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found")); //TODO переделать ошибку

        if (!petById.getOwnerId().equals(principal.getSub())) {
            throw new RuntimeException("Pet owner is not the owner of the pet"); //TODO переделать ошибку
        }

        if (petRequest.getName() != null) {
            log.debug("Updating pet with name {}", petRequest.getName());
            petById.setName(petRequest.getName());
        }

        if (petRequest.getBreed() != null) {
            log.debug("Updating pet with breed {}", petRequest.getBreed());
            petById.setBreed(petRequest.getBreed());
        }

        if (petRequest.getDescription() != null) {
            log.debug("Updating pet with description {}", petRequest.getDescription());
            petById.setDescription(petRequest.getDescription());
        }

        if (petRequest.getYearOfBirth() != null) {
            log.debug("Updating pet with year of birth {}", petRequest.getYearOfBirth());
            petById.setYearOfBirth(petRequest.getYearOfBirth());
        }

        if (petRequest.getPetType() != null) {
            log.debug("Updating pet with pet type {}", petRequest.getPetType());
            petById.setPetType(petRequest.getPetType());
        }

        return petMapper.toPetResponse(petRepository.save(petById));
    }

    @Transactional
    public List<PetShortResponse> getMyPets(Jwt jwt) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);
        return petRepository.findByOwnerId(principal.getSub())
                .stream()
                .map(petMapper::toPetShortResponse)
                .toList();
    }

    @Transactional
    public PetResponse getPetById(Jwt jwt, Long petId) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found")); //TODO переделать ошибку

        if (!pet.getOwnerId().equals(principal.getSub())) {
            throw new RuntimeException("Pet owner is not the owner of the pet"); //TODO переделать ошибку
        }

        log.debug("Found pet with id {}", petId);
        return petMapper.toPetResponse(pet);
    }

}
