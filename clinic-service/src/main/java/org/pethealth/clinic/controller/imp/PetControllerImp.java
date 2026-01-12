package org.pethealth.clinic.controller.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.clinic.controller.PetController;
import org.pethealth.clinic.dto.aliases.AppointmentPageResponse;
import org.pethealth.clinic.dto.enums.AppointmentStatus;
import org.pethealth.clinic.dto.request.PetRequest;
import org.pethealth.clinic.dto.response.PetResponse;
import org.pethealth.clinic.dto.response.PetShortResponse;
import org.pethealth.clinic.mapper.PetMapper;
import org.pethealth.clinic.service.AppointmentService;
import org.pethealth.clinic.service.PetService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pets")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class PetControllerImp implements PetController {

    private final PetService petService;
    private final AppointmentService appointmentService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetResponse createPet(@AuthenticationPrincipal Jwt jwt,
                                 @RequestBody PetRequest petRequest) {
        log.debug("REST POST request to create pet");
        return petService.createPet(jwt, petRequest);
    }

    @Override
    @GetMapping
    public List<PetShortResponse> getMyPets(@AuthenticationPrincipal Jwt jwt) {
        log.debug("REST GET request to get my pets");
        return petService.getMyPets(jwt);
    }

    @Override
    @GetMapping("/{petId}")
    public PetResponse getPetById(@AuthenticationPrincipal Jwt jwt,
                                  @PathVariable Long petId) {
        log.debug("REST GET request to get pet by id: {}", petId);
        return petService.getPetById(jwt, petId);
    }

    @Override
    @PutMapping("/{petId}")
    public PetResponse updatePet(@AuthenticationPrincipal Jwt jwt,
                                 @PathVariable Long petId,
                                 @RequestBody PetRequest petRequest) {
        log.debug("REST PUT request to update pet with id: {}", petId);
        return petService.updatePet(jwt, petId, petRequest);
    }

    @GetMapping("/{petId}/appointments")
    public AppointmentPageResponse getAllAppointmentsByPet(
            @PathVariable Long petId,
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) AppointmentStatus status,
            Pageable pageable
    ) {
        from = from == null ? LocalDateTime.now() : from;
        to = to == null ? LocalDateTime.now().plusDays(365) : to;

        return new AppointmentPageResponse(
                appointmentService.getAppointmentsByPet(from, to, status, petId, pageable, jwt)
        );
    }
}

