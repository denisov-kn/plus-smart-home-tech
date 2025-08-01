package ru.practicum.service;


import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.dto.shoppingStore.ProductListDto;
import ru.practicum.dto.shoppingStore.SetProductQuantityStateRequest;
import ru.practicum.dto.shoppingStore.enums.QuantityState;

public interface ShoppingStoreService {

    ProductListDto getProducts(String category, Integer page, Integer size, String sort, String direction);

    ProductDto getProduct(String id);

    ProductDto createProduct(ProductDto productDto);

    void removeProductFromStore(String productId);

    void quantityState(String productId, QuantityState quantityState);

    ProductDto updateProduct(ProductDto productDto);
}
