package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.client.WarehouseClient;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.practicum.dto.warehouse.AddressDto;
import ru.practicum.dto.warehouse.BookedProductsDto;
import ru.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.practicum.service.WarehouseService;

@RestController
@RequiredArgsConstructor
public class WarehouseController implements WarehouseClient {

    private final WarehouseService warehouseService;

    @Override
    public void addNewProduct(NewProductInWarehouseRequest request) {
        warehouseService.addNewProduct(request);
    }

    @Override
    public BookedProductsDto checkCart(ShoppingCartDto shoppingCartDto) {
        return warehouseService.checkCart(shoppingCartDto);
    }

    @Override
    public void addProduct(AddProductToWarehouseRequest request) {
        warehouseService.addProduct(request);

    }

    @Override
    public AddressDto getAddress() {
        return warehouseService.getAddress();
    }
}
