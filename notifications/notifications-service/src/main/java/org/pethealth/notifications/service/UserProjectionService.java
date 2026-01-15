package org.pethealth.notifications.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.pethealth.notifications.dto.person.UserEvent;
import org.pethealth.notifications.repository.UserProjectionRepository;
import org.pethealth.notifications.repository.entity.UserProjection;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProjectionService {

    private final UserProjectionRepository userProjectionRepository;

    @Transactional
    public void updatePerson(UserEvent userEvent) {
        UserProjection userProjection = userProjectionRepository.findById(userEvent.getId())
                .orElse(UserProjection.builder().id(userEvent.getId()).build());
        userProjection.setEmail(userEvent.getEmail());
        userProjection.setFirstName(userProjection.getFirstName());
        userProjection.setLastName(userProjection.getLastName());
        userProjectionRepository.save(userProjection);
    }

}
