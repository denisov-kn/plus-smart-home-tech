package ru.practicum.dto.shoppingCart;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCartDto {
    String shoppingCartId;
    Map<String, Integer> products;
}
