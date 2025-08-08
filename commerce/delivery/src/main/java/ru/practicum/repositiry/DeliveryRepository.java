package ru.practicum.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.Delivery;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
