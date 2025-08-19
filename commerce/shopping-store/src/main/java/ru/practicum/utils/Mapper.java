package ru.practicum.utils;

import ru.practicum.dto.shoppingStore.ProductDto;
import ru.practicum.model.Product;

public class Mapper {
    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .imageSrc(product.getImageSrc())
                .quantityState(product.getQuantityState())
                .productState(product.getProductState())
                .productCategory(product.getProductCategory())
                .price(product.getPrice())
                .build();
    }

    public static Product toEntity(ProductDto productDto) {
        return Product.builder()
                .productName(productDto.getProductName())
                .description(productDto.getDescription())
                .imageSrc(productDto.getImageSrc())
                .quantityState(productDto.getQuantityState())
                .productState(productDto.getProductState())
                .productCategory(productDto.getProductCategory())
                .price(productDto.getPrice())
                .build();
    }

    public static void updateProductFromDto(ProductDto dto, Product product) {

        if (dto.getProductName() != null) {
            product.setProductName(dto.getProductName());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getImageSrc() != null) {
            product.setImageSrc(dto.getImageSrc());
        }
        if (dto.getQuantityState() != null) {
            product.setQuantityState(dto.getQuantityState());
        }
        if (dto.getProductState() != null) {
            product.setProductState(dto.getProductState());
        }
        if (dto.getProductCategory() != null) {
            product.setProductCategory(dto.getProductCategory());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
    }


}
