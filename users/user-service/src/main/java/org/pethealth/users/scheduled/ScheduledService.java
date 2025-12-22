package org.pethealth.users.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.pethealth.users.utils.RealmUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledService {

    private final RealmUtil realmUtil;

    @Scheduled(cron = "0 */10 * * * *")
    @Async
    public void scheduled() {

        int first = 0;
        int max = 100;

        log.debug("Start scheduled deleted users thread");

        while (true) {

            List<UserRepresentation> userRepresentations = realmUtil.getRealm()
                    .users()
                    .search(null, null, null, null, false, first, max, null, null);

            if (userRepresentations.isEmpty()) {
                break;
            }

            userRepresentations.forEach(user -> {
                if (Boolean.FALSE.equals(user.isEmailVerified()) &&
                        System.currentTimeMillis() - user.getCreatedTimestamp() > Duration.ofHours(24).toMillis()) {

                    realmUtil.getRealm()
                            .users()
                            .delete(user.getId());
                }
                log.trace("Stop deleting users");

            });
            first+=max;

        }
        log.debug("Stop deleting users");
    }
}
