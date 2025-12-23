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
    private Integer id;

    @Column
    private String reason;

    @ManyToOne
    private Pet pet;

    @ManyToOne
    private Clinic clinic;

    @Column
    public LocalDateTime dateTime;

    @Column
    public AppointmentStatus status;
}
