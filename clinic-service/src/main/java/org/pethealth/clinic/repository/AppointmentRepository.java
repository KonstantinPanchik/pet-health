package org.pethealth.clinic.repository;


import org.pethealth.clinic.dto.enums.AppointmentStatus;
import org.pethealth.clinic.entities.Appointment;
import org.pethealth.clinic.entities.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
            SELECT a
            FROM Appointment a
            JOIN fetch a.clinic c
            JOIN fetch a.pet p
            WHERE (:status is NULL
            OR a.status=:status)
            AND c.id=:clinicId
            AND a.dateTime between :from AND :to
            ORDER BY a.dateTime
            """)
    Page<Appointment> findAllByClinic(@Param("clinicId") Long clinicId,
                                      @Param("status") AppointmentStatus status,
                                      @Param("from") LocalDateTime from,
                                      @Param("to") LocalDateTime to,
                                      Pageable pageable);

    @Query("""
            SELECT a
            FROM Appointment a
            JOIN fetch a.clinic c
            JOIN fetch a.pet p
            WHERE (:status is NULL
            OR a.status=:status)
            AND p.id=:petId
            AND a.dateTime between :from AND :to
            ORDER BY a.dateTime
            """)
    Page<Appointment> findPageAllByPet(@Param("petId") Long petId,
                                       @Param("status") AppointmentStatus status,
                                       @Param("from") LocalDateTime from,
                                       @Param("to") LocalDateTime to,
                                       Pageable pageable);


      List<Appointment> findAllByPet(Pet pet);
}

