package org.pethealth.clinic.controller.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.clinic.controller.ClinicController;
import org.pethealth.clinic.dto.aliases.ClinicPageResponse;
import org.pethealth.clinic.dto.request.ClinicRequest;
import org.pethealth.clinic.dto.response.ClinicResponse;
import org.pethealth.clinic.service.ClinicService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clinics")
@RequiredArgsConstructor
public class ClinicControllerImp implements ClinicController {

    private final ClinicService clinicService;

    @Override
    @PostMapping
    public ClinicResponse createClinic(@AuthenticationPrincipal Jwt jwt,
                                       @RequestBody ClinicRequest clinicRequest) {
        log.debug("REST POST request to create clinic");
        return clinicService.createClinic(jwt, clinicRequest);
    }

    @Override
    @GetMapping
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
    @PutMapping("/{clinicId}")
    public ClinicResponse updateClinic(@AuthenticationPrincipal Jwt jwt,
                                       @PathVariable Long clinicId,
                                       @RequestBody ClinicRequest clinicRequest) {
        log.debug("REST PUT request to update clinic with id: {}", clinicId);
        return clinicService.updateClinic(jwt, clinicId, clinicRequest);
    }

    @Override
    @GetMapping("/search")
    public ClinicPageResponse searchClinics(@RequestParam String name, Pageable pageable) {
        log.debug("REST GET request to search clinics by name: {}", name);
        return new ClinicPageResponse(clinicService.searchClinics(name, pageable));
    }
}

