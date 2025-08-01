package ru.practicum.repository;

import lombok.Setter;
import org.springframework.data.domain.Sort;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;

import java.util.Optional;

@Setter
public class ProductFilter {
    private String category;
    private Integer page = 0;
    private Integer size = 10;
    private String sort = "productName";
    private String direction = "ASC";

    public ProductFilter() {
    }
    // геттеры, сеттеры и нормализация
    public int getPage() {
        return page != null ? page : 0;
    }

    public int getSize() {
        return size != null ? size : 10;
    }

    public String getSort() {
        return sort != null ? sort : "productName";
    }

    public String getDirection() {
        return direction != null ? direction : "ASC";
    }

    public Optional<ProductCategory> getCategoryEnum() {
        try {
            return category != null ? Optional.of(ProductCategory.valueOf(category.toUpperCase())) : Optional.empty();
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
