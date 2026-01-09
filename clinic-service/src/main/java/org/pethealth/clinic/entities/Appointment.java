package org.pethealth.clinic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.pethealth.clinic.dto.enums.AppointmentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String reason;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @Column
    private LocalDateTime dateTime;

    @Column
    private AppointmentStatus status;
}
