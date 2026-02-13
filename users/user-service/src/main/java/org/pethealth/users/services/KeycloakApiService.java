package org.pethealth.users.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.pethealth.security.converter.JwtUserConverter;
import org.pethealth.security.model.JwtUserPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakApiService {

    private final RealmResource realmKeycloak;
    private final JwtUserConverter jwtUserConverter;

    public void confirmEmail(Jwt jwt) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);
        if (principal.getEmailVerified()) {
            throw new RuntimeException("Email already verified");//todo ошибка переделать
        }
        realmKeycloak.users().get(principal.getSub()).sendVerifyEmail();
        log.debug("Email for verification was sent");
    }

    public void changePassword(Jwt jwt) {
        JwtUserPrincipal principal = jwtUserConverter.convert(jwt);
        if (!principal.getEmailVerified()) {
            throw new RuntimeException("Email not verified");//todo ошибка переделать
        }

        realmKeycloak.users()
                .get(principal.getSub())
                .executeActionsEmail(List.of("UPDATE_PASSWORD"));

        log.info("Password changed for user {}", principal.getUsername());
    }


}
