package ru.practicum.service;

import ru.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCart(String username);

    ShoppingCartDto updateShoppingCart(String username, Map<UUID, Integer> products);

    void deleteShoppingCart(String username);

    ShoppingCartDto removeProductsFromShoppingCart(String username, List<String> products);

    ShoppingCartDto changeQuantityShoppingCart(String username, ChangeProductQuantityRequest request);
}
