package ru.practicum.utils;

import ru.practicum.dto.warehouse.DimensionDto;
import ru.practicum.model.Dimension;
import ru.practicum.model.WarehouseProduct;

public class WarehouseMapper {
    public static Dimension fromDimensionDto(DimensionDto dimensionDto) {
        return Dimension.builder()
                .depth(dimensionDto.getDepth())
                .width(dimensionDto.getWidth())
                .height(dimensionDto.getHeight())
                .build();
    }
}
