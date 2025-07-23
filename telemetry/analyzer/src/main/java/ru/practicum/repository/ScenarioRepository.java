package ru.practicum.repository;

import ru.practicum.entity.ScenarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScenarioRepository extends JpaRepository<ScenarioEntity, Long> {
    List<ScenarioEntity> findByHubId(String hubId);
    Optional<ScenarioEntity> findByHubIdAndName(String hubId, String name);
    void deleteByHubIdAndName(String hubId, String name);
}
