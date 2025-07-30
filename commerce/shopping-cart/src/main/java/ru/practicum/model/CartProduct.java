package ru.practicum.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "cart_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartProduct {
    @EmbeddedId
    CartProductId id;

    @ManyToOne
    @MapsId("cartId")
    Cart cart;

    UUID productId;
    Integer quantity;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CartProductId implements Serializable {
        UUID cartId;
        UUID productId;
    }
}
