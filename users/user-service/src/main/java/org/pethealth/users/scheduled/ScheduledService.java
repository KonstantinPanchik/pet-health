package org.pethealth.users.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledService {

    private final RealmResource realmKeycloak;

    @Scheduled(cron = "0 * * */30 * *")
    @Async
    public void scheduled() {

        int first = 0;
        int max = 100;

        log.debug("Start scheduled deleted users thread");

        while (true) {

            List<UserRepresentation> userRepresentations = realmKeycloak.users()
                    .search(null, null, null, null, false, first, max, null, null);

            if (userRepresentations.isEmpty()) {
                break;
            }

            userRepresentations.forEach(user -> {
                boolean roleEmailVerified = user.getRealmRoles().contains("ROLE_EMAIL_VERIFIED");
                if (!roleEmailVerified &&
                        System.currentTimeMillis() - user.getCreatedTimestamp() > Duration.ofDays(365).toMillis()) {
                    realmKeycloak.users().delete(user.getId());
                }
                log.trace("Stop deleting users");

            });
            first += max;

        }
        log.debug("Stop deleting users");
    }
}
