package org.pethealth.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserPrincipal {
    private String sub;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean emailVerified;
}
