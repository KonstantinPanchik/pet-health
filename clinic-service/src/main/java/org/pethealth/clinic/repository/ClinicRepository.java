package org.pethealth.clinic.repository;

import org.pethealth.clinic.entities.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    List<Clinic> findByOwnerId(String ownerId);

    @Query(value = """
            SELECT c
            FROM Clinic c
            WHERE UPPER(c.name) LIKE concat('%',UPPER(:name),'%')
            """)
    Page<Clinic> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}

