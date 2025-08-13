package ru.practicum.dto.warehouse;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class AddProductToWarehouseRequest {
    UUID productId;
    Integer quantity;
}
