package org.pethealth.users.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.pethealth.common.dto.enums.SEX;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    private String id;

    @Column(unique = true)
    private String username;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String city;

    @Column
    private String address;

    @Column
    private String phone;

    @Enumerated(EnumType.STRING)
    private SEX sex;

    @Column
    private LocalDate birthday;
}
