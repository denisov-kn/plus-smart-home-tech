package ru.practicum.dto.shoppingStore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SortDto {
        String direction;
        String property;
    }
}
