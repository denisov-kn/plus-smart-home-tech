package ru.practicum.dto.order;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.dto.warehouse.AddressDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class CreateNewOrderRequest {
    ShoppingCartDto shoppingCart;
    AddressDto detailAddress;
}
