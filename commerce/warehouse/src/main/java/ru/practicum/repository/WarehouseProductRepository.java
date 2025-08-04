package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.WarehouseProduct;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, UUID> {

    List<WarehouseProduct> findByProductIdIn(Set<UUID> productIds);
}
