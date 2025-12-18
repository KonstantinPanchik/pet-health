package org.pethealth.security.converter;

import org.pethealth.security.model.JwtUserPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtUserUserConverterKeycloak implements JwtUserConverter {

    public JwtUserPrincipal convert(Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        String firstName = jwt.getClaimAsString("given_name");
        String lastName = jwt.getClaimAsString("family_name");
        String email = jwt.getClaimAsString("email");
        Boolean emailVerified = jwt.getClaimAsBoolean("email_verified");
        return new JwtUserPrincipal(username, firstName, lastName, email, emailVerified);
    }

}
