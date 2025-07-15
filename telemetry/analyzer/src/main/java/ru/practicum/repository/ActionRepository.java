package ru.practicum.repository;

import ru.practicum.model.entity.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<ActionEntity, Long> {
}
