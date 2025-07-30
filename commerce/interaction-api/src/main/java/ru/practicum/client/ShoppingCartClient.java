package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.dto.shoppingStore.SetProductQuantityStateRequest;

import java.util.List;
import java.util.Map;

@FeignClient(name = "shopping-cart-service", path = "/api/v1/shopping-cart")
public interface ShoppingCartClient {
    @GetMapping
    ShoppingCartDto getShoppingCart(@RequestParam String username);

    @PutMapping
    ShoppingCartDto updateShoppingCart(@RequestParam String username,
                                       @RequestBody Map<String, Integer> products
    );

    @DeleteMapping
    void deleteShoppingCart(@RequestParam String username);

    @PostMapping("/remove")
    ShoppingCartDto removeProductsFromShoppingCart(@RequestParam String username,
                                                   @RequestBody List<String> products
    );

    @PostMapping ("/change-quantity")
    ShoppingCartDto changeQuantityShoppingCart(@RequestParam String username,
                                               @RequestBody ChangeProductQuantityRequest request
    );




}
