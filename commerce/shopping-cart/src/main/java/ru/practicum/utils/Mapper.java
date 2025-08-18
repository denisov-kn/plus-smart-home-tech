package ru.practicum.utils;

import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.model.Cart;
import ru.practicum.model.CartProduct;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Mapper {
    public static ShoppingCartDto toShoppingCartDto(Cart cart) {

        Map<UUID, Integer> products = cart.getProducts().stream()
                .collect(Collectors.toMap(
                        cartProduct -> cartProduct.getProductId(),
                        CartProduct::getQuantity
                ));

        return ShoppingCartDto.builder()
                .shoppingCartId(cart.getId())
                .products(products)
                .build();
    }

    public static List<CartProduct> toCartProduct(Cart cart, Map<UUID, Integer> products) {
        return products.entrySet().stream()
                .map(entry -> CartProduct.builder()
                        .cart(cart)
                        .productId(entry.getKey())
                        .quantity(entry.getValue())
                        .build())
                .toList();
    }
}
