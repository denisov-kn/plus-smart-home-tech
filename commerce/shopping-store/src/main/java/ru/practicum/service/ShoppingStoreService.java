package ru.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.practicum.dto.shoppingStore.enums.QuantityState;

public interface ShoppingStoreService {

    Page<ProductDto> getProducts(ProductCategory category, Pageable pageable);

    ProductDto getProduct(String id);

    ProductDto createProduct(ProductDto productDto);

    void removeProductFromStore(String productId);

    void quantityState(String productId, QuantityState quantityState);

    ProductDto updateProduct(ProductDto productDto);
}
