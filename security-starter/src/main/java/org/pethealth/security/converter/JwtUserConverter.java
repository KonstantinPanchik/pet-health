package org.pethealth.security.converter;

import org.pethealth.security.model.JwtUserPrincipal;

import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtUserConverter {

    JwtUserPrincipal convert(Jwt jwt);
}
