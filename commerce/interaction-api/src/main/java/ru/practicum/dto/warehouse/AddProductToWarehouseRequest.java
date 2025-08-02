package ru.practicum.dto.warehouse;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class AddProductToWarehouseRequest {
    String productId;
    Integer quantity;
}
