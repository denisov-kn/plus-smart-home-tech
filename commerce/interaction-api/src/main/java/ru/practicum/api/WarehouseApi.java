package ru.practicum.api;

import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.practicum.dto.warehouse.AddressDto;
import ru.practicum.dto.warehouse.BookedProductsDto;
import ru.practicum.dto.warehouse.NewProductInWarehouseRequest;

public interface WarehouseApi {
    @PutMapping("/api/v1/warehouse")
    void addNewProduct(@RequestBody NewProductInWarehouseRequest request);

    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto checkCart(@RequestBody ShoppingCartDto shoppingCartDto);

    @PostMapping("/api/v1/warehouse/add")
    void addProduct(@RequestBody AddProductToWarehouseRequest request);

    @GetMapping("/api/v1/warehouse/address")
    AddressDto getAddress();

}
