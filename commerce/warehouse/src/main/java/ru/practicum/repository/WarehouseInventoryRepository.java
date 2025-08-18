package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.WarehouseInventory;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventory, UUID> {
    List<WarehouseInventory> findByProductIdIn(Set<UUID> productIds);
}
