package org.pethealth.clinic.mapper;

import lombok.RequiredArgsConstructor;
import org.pethealth.clinic.dto.response.AppointmentResponse;
import org.pethealth.clinic.entities.Appointment;
import org.pethealth.notifications.dto.appointment.AppointmentInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {

    private final PetMapper petMapper;
    private final ClinicMapper clinicMapper;

    public AppointmentResponse toAppointmentResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .reason(appointment.getReason())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .pet(petMapper.toPetShortResponse(appointment.getPet()))
                .clinic(clinicMapper.toClinicResponse(appointment.getClinic()))
                .build();
    }

    public AppointmentInfo toAppointmentInfo(Appointment appointment) {
        AppointmentInfo appointmentInfo = new AppointmentInfo();
        appointmentInfo.setPet(petMapper.toPetInfo(appointment.getPet()));
        appointmentInfo.setClinic(clinicMapper.toClinicInfo(appointment.getClinic()));
        appointmentInfo.setDateTime(appointment.getDateTime());
        appointmentInfo.setReason(appointment.getReason());
        return appointmentInfo;
    }

}
