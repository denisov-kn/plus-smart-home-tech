package ru.practicum.dto.shoppingStore;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.practicum.dto.shoppingStore.enums.ProductState;
import ru.practicum.dto.shoppingStore.enums.QuantityState;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    String productId;
    String productName;
    String description;
    String imageSrc;
    QuantityState quantityState;
    ProductState productState;
    ProductCategory productCategory;
    Double price;
}
