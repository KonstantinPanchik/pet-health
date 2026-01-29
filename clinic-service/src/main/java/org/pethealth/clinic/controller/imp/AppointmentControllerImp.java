package org.pethealth.clinic.controller.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.clinic.controller.AppointmentController;
import org.pethealth.clinic.dto.request.AppointmentCreationRequest;
import org.pethealth.clinic.dto.response.AppointmentResponse;
import org.pethealth.clinic.service.appointment.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('EMAIL_VERIFIED')")
    public AppointmentResponse createAppointment(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Validated AppointmentCreationRequest request
    ) {
        return appointmentService.createAppointment(request, jwt);
    }

    @Override
    @PatchMapping("/{id}/cancel")
    public AppointmentResponse updateAppointmentStatus(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {
        return appointmentService.cancelAppointmentByUser(jwt, id);

    }

}

