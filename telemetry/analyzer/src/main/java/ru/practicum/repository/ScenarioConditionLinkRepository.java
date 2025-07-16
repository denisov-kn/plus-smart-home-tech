package ru.practicum.repository;

import ru.practicum.entity.ScenarioConditionLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioConditionLinkRepository extends JpaRepository<ScenarioConditionLink, Long> {
    List<ScenarioConditionLink> findByScenarioId(Long scenarioId);
}
