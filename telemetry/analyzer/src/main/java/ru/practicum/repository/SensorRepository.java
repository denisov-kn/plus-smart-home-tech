package ru.practicum.repository;


import ru.practicum.model.entity.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<SensorEntity, String> {
    boolean existsByIdInAndHubId(Collection<String> ids, String hubId);
    Optional<SensorEntity> findByIdAndHubId(String id, String hubId);
}
