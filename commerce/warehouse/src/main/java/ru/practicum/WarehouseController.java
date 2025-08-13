package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.WarehouseApi;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.dto.warehouse.*;
import ru.practicum.service.WarehouseService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WarehouseController implements WarehouseApi {

    private final WarehouseService warehouseService;

    @Override
    public void addNewProduct(NewProductInWarehouseRequest request) {
        log.info("Add new product to warehouse request: {}", request);
        warehouseService.addNewProduct(request);
        log.info("Add new product to warehouse successful");

    }

    @Override
    public BookedProductsDto checkCart(ShoppingCartDto shoppingCartDto) {
        log.info("Check cart for shopping cart request: {}", shoppingCartDto);
        BookedProductsDto bookedProductsDto = warehouseService.checkCart(shoppingCartDto);
        log.info("Check cart for shopping cart successful BookedProductsDto: {}", bookedProductsDto);
        return bookedProductsDto;
    }

    @Override
    public void addProduct(AddProductToWarehouseRequest request) {
        log.info("Add product to warehouse request: {}", request);
        warehouseService.addProduct(request);
        log.info("Add product to warehouse successful");

    }

    @Override
    public AddressDto getAddress() {
        AddressDto addressDto = warehouseService.getAddress();
        log.info("Get address: {}", addressDto);
        return addressDto;
    }

    @Override
    public void shippedProducts(ShippedToDeliveryRequest request) {
        log.info("Shipped to delivery request: {}", request);
        warehouseService.shippedProducts(request);
        log.info("Shipped to delivery request successful");
    }

    @Override
    public void returnProducts(Map<UUID, Integer> products) {
        log.info("Returned products map: {}", products);
        warehouseService.returnProducts(products);
        log.info("Returned products map successful");

    }

    @Override
    public BookedProductsDto assemblyProducts(AssemblyProductsForOrderRequest request) {
        log.info("Assembly product for order request: {}", request);
        BookedProductsDto bookedProductsDto = warehouseService.assemblyProducts(request);
        log.info("BookedProductsDto: {}", bookedProductsDto);
        return bookedProductsDto;
    }
}
