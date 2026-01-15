package org.pethealth.notifications.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "user_projection")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserProjection {

    @Id
    private String id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;


}
