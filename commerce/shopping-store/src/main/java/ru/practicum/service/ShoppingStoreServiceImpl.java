package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.practicum.dto.shoppingStore.enums.QuantityState;
import ru.practicum.repository.ShoppingStoreRepository;
import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.dto.shoppingStore.enums.ProductState;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Product;
import ru.practicum.utils.Mapper;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingStoreServiceImpl implements ShoppingStoreService {

    private final ShoppingStoreRepository shoppingStoreRepository;

    @Override
    public Page<ProductDto> getProducts(ProductCategory category, Pageable pageable) {
        return shoppingStoreRepository.findByProductCategory(category, pageable).map(Mapper::toDto);
    }

    @Override
    public ProductDto getProduct(String id) {
        return Mapper.toDto(shoppingStoreRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Продукт с id " + id + " не найден"))
        );
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = Mapper.toEntity(productDto);
        return Mapper.toDto(shoppingStoreRepository.save(product));
    }

    @Override
    public void removeProductFromStore(String productId) {
        UUID uuid = UUID.fromString(productId);
        Product product = findProductById(uuid);
        product.setProductState(ProductState.DEACTIVATE);
        shoppingStoreRepository.save(product);
    }

    @Override
    public void quantityState(String productId, QuantityState quantityState) {
        UUID uuid = UUID.fromString(productId);
        Product product = findProductById(uuid);
        product.setQuantityState(quantityState);
        shoppingStoreRepository.save(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        UUID uuid = UUID.fromString(productDto.getProductId());
        Product product = findProductById(uuid);
        Mapper.updateProductFromDto(productDto, product);
        return Mapper.toDto(shoppingStoreRepository.save(product));
    }

    private Product findProductById(UUID id) {
        return shoppingStoreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Продукт с id " + id + " не найден"));
    }
}
