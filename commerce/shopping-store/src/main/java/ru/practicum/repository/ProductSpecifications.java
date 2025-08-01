package ru.practicum.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.model.Product;

import java.util.ArrayList;
import java.util.List;


public class ProductSpecifications {
    public static Specification<Product> withFilter(ProductFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            filter.getCategoryEnum().ifPresent(categoryEnum ->
                    predicates.add(cb.equal(root.get("productCategory"), categoryEnum))
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
