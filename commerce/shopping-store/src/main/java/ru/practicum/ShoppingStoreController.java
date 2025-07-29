package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.client.ShoppingStoreClient;
import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.dto.shoppingStore.ProductListDto;
import ru.practicum.dto.shoppingStore.SetProductQuantityStateRequest;
import ru.practicum.service.ShoppingStoreService;

@RestController
@RequiredArgsConstructor
public class ShoppingStoreController implements ShoppingStoreClient {

    private final ShoppingStoreService shoppingStoreService;

    @Override
    @ResponseStatus
    public ProductListDto getProducts(String category, Integer page, Integer size, String sort, String direction) {
        return shoppingStoreService.getProducts(category, page, size, sort, direction);
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
        shoppingStoreService.removeProductFromStore(productId);
    }

    @Override
    public void quantityState(SetProductQuantityStateRequest setProductQuantityStateRequest) {
        shoppingStoreService.quantityState(setProductQuantityStateRequest);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        return shoppingStoreService.updateProduct(productDto);
    }
}
