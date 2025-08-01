package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.practicum.model.Product;
import java.util.UUID;

public interface ShoppingStoreRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Page<Product> findByProductCategory(ProductCategory category, Pageable pageable);
}
