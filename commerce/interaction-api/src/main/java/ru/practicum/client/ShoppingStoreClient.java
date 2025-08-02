package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.practicum.dto.shoppingStore.enums.QuantityState;



@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShoppingStoreClient {
    @GetMapping
    Page<ProductDto> getProducts(@RequestParam ProductCategory category, @PageableDefault(sort = {"productName"}) Pageable pageable);

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