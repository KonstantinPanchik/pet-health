package org.pethealth.clinic.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.pethealth.clinic.dto.enums.AppointmentStatus;
import org.pethealth.clinic.dto.request.AppointmentCreationRequest;
import org.pethealth.clinic.dto.response.AppointmentResponse;
import org.pethealth.clinic.entities.Appointment;
import org.pethealth.clinic.entities.Clinic;
import org.pethealth.clinic.entities.Pet;
import org.pethealth.clinic.mapper.AppointmentMapper;
import org.pethealth.clinic.repository.AppointmentRepository;
import org.pethealth.clinic.repository.ClinicRepository;
import org.pethealth.clinic.repository.PetRepository;
import org.pethealth.security.converter.JwtUserConverter;
import org.pethealth.security.model.JwtUserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final ClinicRepository clinicRepository;

    private final AppointmentMapper appointmentMapper;

    private final JwtUserConverter jwtUserConverter;

    public AppointmentResponse createAppointment(AppointmentCreationRequest request, Jwt jwt) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);

        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        if (!pet.getOwnerId().equals(principal.getSub())) {
            throw new IllegalArgumentException("Pet is not owner of this owner");
        }

        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));

        Appointment appointment = new Appointment();
        appointment.setPet(pet);
        appointment.setClinic(clinic);
        appointment.setReason(request.getReason());
        appointment.setDateTime(request.getDateTime());
        appointment.setStatus(AppointmentStatus.NEW);
        appointmentRepository.save(appointment);

        return appointmentMapper.toAppointmentResponse(appointment);
    }

    public AppointmentResponse getAppointment(Long id, Jwt jwt) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        if (!(appointment.getPet().getOwnerId().equals(principal.getSub()) &&
                appointment.getClinic().getOwnerId().equals(principal.getSub()))) {
            throw new IllegalArgumentException("Pet is not owner of this owner");
        }

        return appointmentMapper.toAppointmentResponse(appointment);
    }

    public Page<AppointmentResponse> getAppointmentsByPet(
            LocalDateTime from,
            LocalDateTime to,
            AppointmentStatus status,
            Long petId,
            Pageable pageable,
            Jwt jwt) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
        if (!principal.getSub().equals(pet.getOwnerId())) {
            throw new IllegalArgumentException("Pet is not owner of this owner");
        }

        Page<Appointment> pageAllByPet = appointmentRepository.findPageAllByPet(petId, status, from, to, pageable);
        return pageAllByPet.map(appointmentMapper::toAppointmentResponse);
    }

    public Page<AppointmentResponse> getAppointmentsByClinic(
            LocalDateTime from,
            LocalDateTime to,
            AppointmentStatus status,
            Long clinicId,
            Pageable pageable,
            Jwt jwt) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);

        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));

        if (!principal.getSub().equals(clinic.getOwnerId())) {
            throw new IllegalArgumentException("Clinic is not owner of this owner");
        }

        Page<Appointment> pageAllByPet = appointmentRepository.findAllByClinic(clinicId, status, from, to, pageable);
        return pageAllByPet.map(appointmentMapper::toAppointmentResponse);
    }


}
