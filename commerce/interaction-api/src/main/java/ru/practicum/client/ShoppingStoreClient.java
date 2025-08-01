package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.dto.shoppingStore.ProductListDto;
import ru.practicum.dto.shoppingStore.SetProductQuantityStateRequest;
import ru.practicum.dto.shoppingStore.enums.QuantityState;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShoppingStoreClient {
    @GetMapping
    ProductListDto getProducts(@RequestParam String category,
                               @RequestParam (required = false) Integer page,
                               @RequestParam (required = false) Integer size,
                               @RequestParam (required = false) String sort,
                               @RequestParam (required = false) String direction
    );

    @GetMapping("/{id}")
    ProductDto getProduct(@PathVariable String id);

    @PutMapping
    ProductDto createProduct(@RequestBody ProductDto productDto);

    @PostMapping("/removeProductFromStore")
    void removeProductFromStore(@RequestBody String productId);

    @PostMapping("/quantityState")
    void quantityState(@RequestParam (required = true) String productId,
                       @RequestParam (required = true) QuantityState quantityState
                       );

    @PostMapping
    ProductDto updateProduct(@RequestBody ProductDto productDto);
}