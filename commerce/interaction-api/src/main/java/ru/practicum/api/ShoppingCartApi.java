package ru.practicum.api;

import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface ShoppingCartApi {
    @GetMapping("/api/v1/shopping-cart")
    ShoppingCartDto getShoppingCart(@RequestParam String username);

    @PutMapping("/api/v1/shopping-cart")
    ShoppingCartDto updateShoppingCart(@RequestParam String username,
                                       @RequestBody Map<UUID, Integer> products
    );

    @DeleteMapping("/api/v1/shopping-cart")
    void deleteShoppingCart(@RequestParam String username);

    @PostMapping("/api/v1/shopping-cart/remove")
    ShoppingCartDto removeProductsFromShoppingCart(@RequestParam String username,
                                                   @RequestBody List<String> products
    );

    @PostMapping ("/api/v1/shopping-cart/change-quantity")
    ShoppingCartDto changeQuantityShoppingCart(@RequestParam String username,
                                               @RequestBody ChangeProductQuantityRequest request
    );




}
