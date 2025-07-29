package ru.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.practicum.dto.shoppingStore.enums.ProductState;
import ru.practicum.dto.shoppingStore.enums.QuantityState;

import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @NotNull
    String name;

    @NotNull
    String description;

    @NotNull
    String imageSrc;

    @NotNull
    @Enumerated(EnumType.STRING)
    QuantityState quantityState;

    @NotNull
    @Enumerated(EnumType.STRING)
    ProductState productState;

    @NotNull
    @Enumerated(EnumType.STRING)
    ProductCategory productCategory;

    @NotNull
    Double price;

}
