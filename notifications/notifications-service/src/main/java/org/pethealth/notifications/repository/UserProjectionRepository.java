package org.pethealth.notifications.repository;

import org.pethealth.notifications.repository.entity.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectionRepository extends JpaRepository<UserProjection, String> {
}
