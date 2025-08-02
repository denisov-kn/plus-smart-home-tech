package ru.practicum.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.practicum.dto.shoppingStore.enums.QuantityState;


public interface ShoppingStoreApi {
    @GetMapping("/api/v1/shopping-store")
    Page<ProductDto> getProducts(@RequestParam ProductCategory category, @PageableDefault(sort = {"productName"}) Pageable pageable);

    @GetMapping("/api/v1/shopping-store/{id}")
    ProductDto getProduct(@PathVariable String id);

    @PutMapping("/api/v1/shopping-store")
    ProductDto createProduct(@RequestBody ProductDto productDto);

    @PostMapping("/api/v1/shopping-store/removeProductFromStore")
    void removeProductFromStore(@RequestBody String productId);

    @PostMapping("/api/v1/shopping-store/quantityState")
    void quantityState(@RequestParam (required = true) String productId,
                       @RequestParam (required = true) QuantityState quantityState
                       );

    @PostMapping("/api/v1/shopping-store")
    ProductDto updateProduct(@RequestBody ProductDto productDto);
}