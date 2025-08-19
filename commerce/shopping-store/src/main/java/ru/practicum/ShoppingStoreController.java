package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.ShoppingStoreApi;
import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.practicum.dto.shoppingStore.enums.QuantityState;
import ru.practicum.service.ShoppingStoreService;

import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ShoppingStoreController implements ShoppingStoreApi {

    private final ShoppingStoreService shoppingStoreService;

    @Override
    public Page<ProductDto> getProducts(ProductCategory category, Pageable pageable) {
        log.info("getProducts for category {}", category);
        log.info("getProducts for Pageable {}", pageable);
        Page<ProductDto> products = shoppingStoreService.getProducts(category, pageable);
        log.info("getProducts returned {} products", products.getTotalElements());
        return products;
    }

    @Override
    public ProductDto getProduct(UUID id) {
        log.info("getProduct with id {}", id);
        ProductDto productDto = shoppingStoreService.getProduct(id);
        log.info("getProduct returned {}", productDto);
        return productDto;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("createProduct {}", productDto);
        ProductDto newProductDto = shoppingStoreService.createProduct(productDto);
        log.info("createProduct returned {}", newProductDto);
        return newProductDto;
    }

    @Override
    public void removeProductFromStore(UUID productId) {
        log.info("removeProductFromStore with id {}", productId);
        shoppingStoreService.removeProductFromStore(productId);
        log.info("removeProductFromStore was removed");
    }

    @Override
    public void quantityState(UUID productId, QuantityState quantityState) {
        log.info("quantityState for productId {} ", productId);
        log.info("quantityState {}", quantityState);
        shoppingStoreService.quantityState(productId, quantityState);
        log.info("quantityState was changed");
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        log.info("updateProduct {}", productDto);
        ProductDto updatedProductDto = shoppingStoreService.updateProduct(productDto);
        log.info("updateProduct returned {}", updatedProductDto);
        return updatedProductDto;

    }
}
