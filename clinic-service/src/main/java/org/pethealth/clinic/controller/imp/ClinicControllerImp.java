package org.pethealth.clinic.controller.imp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.clinic.controller.ClinicController;
import org.pethealth.clinic.dto.aliases.AppointmentPageResponse;
import org.pethealth.clinic.dto.aliases.ClinicPageResponse;
import org.pethealth.clinic.dto.enums.AppointmentStatus;
import org.pethealth.clinic.dto.request.ClinicRequest;
import org.pethealth.clinic.dto.response.AppointmentResponse;
import org.pethealth.clinic.dto.response.ClinicResponse;
import org.pethealth.clinic.service.appointment.AppointmentService;
import org.pethealth.clinic.service.clinic.ClinicService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/clinics")
@RequiredArgsConstructor
public class ClinicControllerImp implements ClinicController {

    private final ClinicService clinicService;
    private final AppointmentService appointmentService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('DOCTOR')")
    public ClinicResponse createClinic(@AuthenticationPrincipal Jwt jwt,
                                       @RequestBody @Validated ClinicRequest clinicRequest) {
        log.debug("REST POST request to create clinic");
        return clinicService.createClinic(jwt, clinicRequest);
    }

    @Override
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/my")
    public List<ClinicResponse> getMyClinics(@AuthenticationPrincipal Jwt jwt) {
        log.debug("REST GET request to get my clinics");
        return clinicService.getMyClinics(jwt);
    }

    @Override
    @GetMapping("/{clinicId}")
    public ClinicResponse getClinicById(@PathVariable Long clinicId) {
        log.debug("REST GET request to get clinic by id: {}", clinicId);
        return clinicService.getClinicById(clinicId);
    }

    @Override
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/{clinicId}")
    public ClinicResponse updateClinic(@AuthenticationPrincipal Jwt jwt,
                                       @PathVariable Long clinicId,
                                       @RequestBody ClinicRequest clinicRequest) {
        log.debug("REST PUT request to update clinic with id: {}", clinicId);
        return clinicService.updateClinic(jwt, clinicId, clinicRequest);
    }

    @Override
    @GetMapping
    public ClinicPageResponse searchClinics(@RequestParam(required = false, defaultValue = "") String name, Pageable pageable) {
        log.debug("REST GET request to search clinics by name: {}", name);
        return new ClinicPageResponse(clinicService.searchClinics(name, pageable));
    }

    @GetMapping("/{clinicId}/appointments")
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

    @PatchMapping("/{clinicId}/appointments/{appointmentId}/cancel")
    public AppointmentResponse cancelAppointment(
            @PathVariable Long clinicId,
            @PathVariable Long appointmentId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return appointmentService.cancelAppointmentByClinicOwner(jwt, appointmentId, clinicId);
    }

    @PatchMapping("/{clinicId}/appointments/{appointmentId}/visit")
    public AppointmentResponse markAsVisitAppointment(
            @PathVariable Long clinicId,
            @PathVariable Long appointmentId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return appointmentService.visitAppointment(jwt, appointmentId, clinicId);
    }
}

