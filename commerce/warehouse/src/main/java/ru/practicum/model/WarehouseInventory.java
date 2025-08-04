package ru.practicum.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "warehouse_inventory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseInventory {

    @Id
    @Column(name = "product_id")
    UUID productId;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "product_id")
    WarehouseProduct product;

    @NotNull
    Integer quantity;
}
