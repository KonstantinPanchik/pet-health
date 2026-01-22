package org.pethealth.clinic.service.clinic;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.clinic.dto.request.ClinicRequest;
import org.pethealth.clinic.dto.response.ClinicResponse;
import org.pethealth.clinic.entities.Clinic;
import org.pethealth.clinic.mapper.ClinicMapper;
import org.pethealth.clinic.repository.ClinicRepository;
import org.pethealth.security.converter.JwtUserConverter;
import org.pethealth.security.model.JwtUserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;
    private final JwtUserConverter jwtUserConverter;

    @Transactional
    public ClinicResponse getClinicById(Long clinicId) {

        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Clinic not found")); //TODO переделать ошибку

        log.debug("Found clinic with id {}", clinicId);
        return clinicMapper.toClinicResponse(clinic);
    }

    @Transactional
    public List<ClinicResponse> getMyClinics(Jwt jwt) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);

        log.debug("Finding clinics for owner {}", principal.getSub());
        return clinicRepository.findByOwnerId(principal.getSub())
                .stream()
                .map(clinicMapper::toClinicResponse)
                .toList();
    }

    @Transactional
    public ClinicResponse updateClinic(Jwt jwt, Long clinicId, ClinicRequest clinicRequest) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);

        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Clinic not found")); //TODO переделать ошибку

        if (!clinic.getOwnerId().equals(principal.getSub())) {
            throw new RuntimeException("Clinic owner is not the owner of the clinic"); //TODO переделать ошибку
        }

        if (clinicRequest.getName() != null) {
            log.debug("Updating clinic with name {}", clinicRequest.getName());
            clinic.setName(clinicRequest.getName());
        }

        if (clinicRequest.getCity() != null) {
            log.debug("Updating clinic with city {}", clinicRequest.getCity());
            clinic.setCity(clinicRequest.getCity());
        }

        if (clinicRequest.getAddress() != null) {
            log.debug("Updating clinic with address {}", clinicRequest.getAddress());
            clinic.setAddress(clinicRequest.getAddress());
        }

        if (clinicRequest.getPhone() != null) {
            log.debug("Updating clinic with phone {}", clinicRequest.getPhone());
            clinic.setPhone(clinicRequest.getPhone());
        }

        return clinicMapper.toClinicResponse(clinicRepository.save(clinic));
    }

    @Transactional
    public ClinicResponse createClinic(Jwt jwt, ClinicRequest clinicRequest) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);
        Clinic clinic = new Clinic();
        clinic.setName(clinicRequest.getName());
        clinic.setCity(clinicRequest.getCity());
        clinic.setAddress(clinicRequest.getAddress());
        clinic.setPhone(clinicRequest.getPhone());
        clinic.setOwnerId(principal.getSub());

        return clinicMapper.toClinicResponse(clinicRepository.save(clinic));
    }

    @Transactional
    public Page<ClinicResponse> searchClinics(String name, Pageable pageable) {
        log.debug("Searching clinics by name: {}", name);
        return clinicRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(clinicMapper::toClinicResponse);
    }

}

