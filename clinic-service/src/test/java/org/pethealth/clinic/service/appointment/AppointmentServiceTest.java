package org.pethealth.clinic.service.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.pethealth.clinic.dto.enums.PetType;
import org.pethealth.clinic.dto.request.AppointmentCreationRequest;
import org.pethealth.clinic.entities.Clinic;
import org.pethealth.clinic.entities.Pet;
import org.pethealth.clinic.repository.AppointmentRepository;
import org.pethealth.clinic.repository.ClinicRepository;
import org.pethealth.clinic.repository.PetRepository;
import org.pethealth.security.converter.JwtUserConverter;
import org.pethealth.security.model.JwtUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppointmentServiceTest {

    @MockBean
    AppointmentRepository appointmentRepository;

    @MockBean
    PetRepository petRepository;

    @MockBean
    ClinicRepository clinicRepository;

    @MockBean
    JwtUserConverter jwtUserConverter;

    @Autowired
    AppointmentService appointmentService;

    @Test
    public void shouldSendAppointmentToRabbitMQ() {
        when(jwtUserConverter.convert(any()))
                .thenAnswer(invocationOnMock -> {
                    JwtUserPrincipal principal = new JwtUserPrincipal();
                    principal.setSub("0000-0000-0000-0000");
                    principal.setUsername("kostya-init");
                    principal.setEmail("konstantin.panisov@gmail.com");
                    principal.setFirstName("Kostya");
                    principal.setLastName("Tuder");
                    principal.setEmailVerified(true);
                    return principal;
                });

        when(petRepository.findById(anyLong()))
                .thenAnswer(invocationOnMock -> {
                    Pet pet = new Pet();
                    pet.setId(1L);
                    pet.setName("Olivka");
                    pet.setPetType(PetType.DOG);
                    pet.setYearOfBirth(2023);
                    pet.setBreed("Alabay");
                    pet.setDescription("Собака пудель");
                    pet.setOwnerId("0000-0000-0000-0000");
                    return Optional.of(pet);
                });
        when(clinicRepository.findById(anyLong()))
                .thenAnswer(invocationOnMock -> {
                    Clinic clinic = new Clinic();
                    clinic.setId(1L);
                    clinic.setName("Клиника доктора Трампа");
                    clinic.setOwnerId("0000-0000-0000-0001");
                    clinic.setCity("London");
                    clinic.setAddress("улица Фейков, возле дуба");
                    clinic.setPhone("+79000990098");
                    return Optional.of(clinic);
                });

        when(appointmentRepository.save(any()))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        AppointmentCreationRequest request = new AppointmentCreationRequest();
        request.setPetId(1L);
        request.setClinicId(1L);
        request.setDateTime(LocalDateTime.now().plusDays(1));
        request.setReason("Собака устала работать за копейки");
        appointmentService.createAppointment(request, null);
    }
}
