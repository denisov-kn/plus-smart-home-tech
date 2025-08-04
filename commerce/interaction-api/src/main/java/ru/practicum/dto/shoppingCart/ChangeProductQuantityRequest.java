package ru.practicum.dto.shoppingCart;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ChangeProductQuantityRequest {
    String productId;
    Integer newQuantity;
}
