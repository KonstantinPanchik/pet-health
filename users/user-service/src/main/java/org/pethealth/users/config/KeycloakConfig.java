package org.pethealth.users.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class KeycloakConfig {

    @Value("${application.keycloak-admin.keycloak-url}")
    private String keycloakUrl;

    @Value("${application.keycloak-admin.realm}")
    private String realm;

    @Value("${application.keycloak-admin.client-id}")
    private String clientId;

    @Value("${application.keycloak-admin.client-secret}")
    private String clientSecret;

    @Bean
    public Keycloak keycloak() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
        log.info(keycloak.serverInfo().getInfo().toString());
        return keycloak;
    }

    @Bean
    public RealmResource realmKeycloak(Keycloak keycloak) {
        return keycloak.realm(realm);
    }
}
