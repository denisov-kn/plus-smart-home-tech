package ru.practicum.service;

import ru.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.dto.shoppingStore.SetProductQuantityStateRequest;

import java.util.List;
import java.util.Map;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCart(String username);

    ShoppingCartDto updateShoppingCart(String username, Map<String, Integer> products);

    void deleteShoppingCart(String username);

    ShoppingCartDto removeProductsFromShoppingCart(String username, List<String> products);

    ShoppingCartDto changeQuantityShoppingCart(String username, ChangeProductQuantityRequest request);
}
