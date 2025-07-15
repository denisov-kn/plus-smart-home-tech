package ru.practicum.repository;

import ru.practicum.model.entity.ScenarioActionLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioActionLinkRepository extends JpaRepository<ScenarioActionLink, Long> {
    List<ScenarioActionLink> findAllByScenarioId(Long scenarioId);
}
