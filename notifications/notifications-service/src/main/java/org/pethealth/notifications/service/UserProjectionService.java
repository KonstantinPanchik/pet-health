package org.pethealth.notifications.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pethealth.notifications.dto.person.UserEvent;
import org.pethealth.notifications.repository.UserProjectionRepository;
import org.pethealth.notifications.repository.entity.UserProjection;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProjectionService {

    private final UserProjectionRepository userProjectionRepository;

    @Transactional
    public void updatePerson(UserEvent userEvent) {
        log.info("Updating user projection with event id : {}", userEvent.getId());
        UserProjection userProjection = userProjectionRepository.findById(userEvent.getId())
                .orElse(UserProjection.builder().id(userEvent.getId()).build());
        userProjection.setEmail(userEvent.getEmail());
        userProjection.setFirstName(userProjection.getFirstName());
        userProjection.setLastName(userProjection.getLastName());
        userProjectionRepository.save(userProjection);
        log.info("Updated user projection with event id : {}", userEvent.getId());
    }
}
