package ru.practicum.dto.shoppingStore;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductListDto {
    List<ProductDto> content;
    List<SortDto> sort;

    @Data
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SortDto {
        String direction;
        String property;
    }
}
