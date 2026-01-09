package org.pethealth.clinic.controller.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.clinic.controller.AppointmentController;
import org.pethealth.clinic.dto.aliases.AppointmentPageResponse;
import org.pethealth.clinic.dto.enums.AppointmentStatus;
import org.pethealth.clinic.dto.request.AppointmentCreationRequest;
import org.pethealth.clinic.dto.response.AppointmentResponse;
import org.pethealth.clinic.service.AppointmentService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@Validated
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentControllerImp implements AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public AppointmentResponse getAppointment(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id
    ) {
        return appointmentService.getAppointment(id, jwt);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMAIL_VERIFIED')")
    public AppointmentResponse createAppointment(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Validated AppointmentCreationRequest request
    ) {
        return appointmentService.createAppointment(request, jwt);
    }

    @GetMapping("/{petId}/byPetId")
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

    @GetMapping("/{clinicId}/byClinicId")
    public AppointmentPageResponse getAllAppointmentsByClinicId(
            @PathVariable Long clinicId,
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) AppointmentStatus status,
            Pageable pageable
    ) {
        from = from == null ? LocalDateTime.now() : from;
        to = to == null ? LocalDateTime.now().plusDays(365) : to;

        return new AppointmentPageResponse(
                appointmentService.getAppointmentsByClinic(from, to, status, clinicId, pageable, jwt)
        );
    }


}
