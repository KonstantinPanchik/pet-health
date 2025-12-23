package org.pethealth.clinic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.pethealth.clinic.dto.enums.PetType;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "breed")
    private String breed;

    @Column(name="description")
    private String description;

    @Column(name = "year_of_birth")
    private Integer yearOfBirth;

    @Column(name = "ownerId")
    private String ownerId;

    @Column(name = "pet_type")
    @Enumerated(EnumType.STRING)
    private PetType petType;

}
