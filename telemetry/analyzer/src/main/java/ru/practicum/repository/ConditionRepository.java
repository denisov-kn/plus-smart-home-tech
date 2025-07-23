package ru.practicum.repository;

import ru.practicum.entity.ConditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<ConditionEntity, Long> {
}
