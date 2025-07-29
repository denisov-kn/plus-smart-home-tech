package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ShoppingStore;
import ru.practicum.ShoppingStoreRepository;
import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.dto.shoppingStore.ProductListDto;
import ru.practicum.dto.shoppingStore.SetProductQuantityStateRequest;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Product;
import ru.practicum.utils.Mapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingStoreServiceImpl implements ShoppingStoreService {

    private final ShoppingStoreRepository shoppingStoreRepository;

    @Override
    public ProductListDto getProducts(String category, Integer page, Integer size, String sort, String direction) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 10,
                Sort.by(Sort.Direction.fromString(direction != null ? direction : "ASC"),
                        sort != null ? sort : "name")
        );

        Page<Product> productPage;

        if (category != null) {
            ProductCategory categoryEnum = ProductCategory.valueOf(category.toUpperCase());
            productPage = shoppingStoreRepository.findByProductCategory(categoryEnum, pageable);
        } else {
            productPage = shoppingStoreRepository.findAll(pageable);
        }

        List<ProductDto> content = productPage.getContent().stream()
                .map(Mapper::toDto)
                .toList();

        ProductListDto.SortDto sortDto = new ProductListDto.SortDto(direction, sort);

        return new ProductListDto(content, List.of(sortDto));

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
        findProductById(UUID.fromString(productId));
        shoppingStoreRepository.deleteById(UUID.fromString(productId));
    }

    @Override
    public void quantityState(SetProductQuantityStateRequest request) {
        UUID uuid = UUID.fromString(request.getProductId());
        Product product = findProductById(uuid);
        product.setQuantityState(request.getQuantityState());
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
