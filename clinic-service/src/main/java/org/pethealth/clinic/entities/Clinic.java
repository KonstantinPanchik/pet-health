package org.pethealth.clinic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clinics")
@Getter
@Setter
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column (name = "address")
    private String address;

    @Column(name = "ownerId",nullable = false)
    private String ownerId;

    @Column(name = "phone")
    private String phone;

}
