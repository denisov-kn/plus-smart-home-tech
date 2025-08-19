package ru.practicum.dto.shoppingCart;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ShoppingCartDto {
    UUID shoppingCartId;
    Map<UUID, Integer> products;
}
