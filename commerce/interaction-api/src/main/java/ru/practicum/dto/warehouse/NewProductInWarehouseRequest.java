package ru.practicum.dto.warehouse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class NewProductInWarehouseRequest {
    String productId;
    Boolean fragile;
    DimensionDto dimension;
    Double weight;
}
