package org.pethealth.utils;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealmUtil {

    @Value("${application.keycloak-admin.realm}")
    private String realm;
    private final Keycloak keycloak;


    public RealmResource getRealm() {
        return keycloak.realm(realm);
    }


}
