package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.client.ShoppingStoreClient;
import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.practicum.dto.shoppingStore.enums.QuantityState;
import ru.practicum.service.ShoppingStoreService;



@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
public class ShoppingStoreController implements ShoppingStoreClient {

    private final ShoppingStoreService shoppingStoreService;

    @Override
    public Page<ProductDto> getProducts(ProductCategory category, Pageable pageable) {
        return shoppingStoreService.getProducts(category, pageable);
    }

    @Override
    public ProductDto getProduct(String id) {
        return shoppingStoreService.getProduct(id);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return shoppingStoreService.createProduct(productDto);
    }

    @Override
    public void removeProductFromStore(String productId) {
        productId = productId.replaceAll("\"", "").trim();
        shoppingStoreService.removeProductFromStore(productId);
    }

    @Override
    public void quantityState(String productId, QuantityState quantityState) {
        shoppingStoreService.quantityState(productId, quantityState);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        return shoppingStoreService.updateProduct(productDto);
    }
}
