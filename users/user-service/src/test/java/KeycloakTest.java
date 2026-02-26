import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@Slf4j
public class KeycloakTest {


    @Test
    public void test() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080/auth")
                .realm("pethealth")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId("admin-service")
                .clientSecret("EYEKRs6m4RNhkenFz4pIgMlflkdBbeTL")
                .build();
        RealmResource pethealth = keycloak.realm("pethealth");
        List<UserRepresentation> userRepresentations = pethealth
                .users()
                .search(null, null, null, null, null, null);

        UserResource userResource = pethealth.users().get("d72e2901-156e-4f27-a2f6-0cd6059acb46");

        String email = userResource.toRepresentation().getEmail();
        log.info(email);
        userRepresentations.forEach((user) -> {
            log.info(user.getUsername());
            log.info(user.getEmail());
        });
    }
}
