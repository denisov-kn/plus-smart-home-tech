package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.client.ShoppingCartClient;
import ru.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.service.ShoppingCartService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController implements ShoppingCartClient {

    private final ShoppingCartService shoppingCartService;

    @Override
    public ShoppingCartDto getShoppingCart(String username) {
        return shoppingCartService.getShoppingCart(username);
    }

    @Override
    public ShoppingCartDto updateShoppingCart(String username, Map<String, Integer> products) {
        return shoppingCartService.updateShoppingCart(username, products);
    }

    @Override
    public void deleteShoppingCart(String username) {
        shoppingCartService.deleteShoppingCart(username);
    }

    @Override
    public ShoppingCartDto removeProductsFromShoppingCart(String username, List<String> products) {
        return shoppingCartService.removeProductsFromShoppingCart(username, products);
    }

    @Override
    public ShoppingCartDto changeQuantityShoppingCart(String username, ChangeProductQuantityRequest request) {
        return shoppingCartService.changeQuantityShoppingCart(username, request);
    }
}
