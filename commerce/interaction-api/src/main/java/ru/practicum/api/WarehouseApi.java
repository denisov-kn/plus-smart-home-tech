package ru.practicum.api;

import jdk.jfr.Description;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.dto.warehouse.*;

import java.util.Map;
import java.util.UUID;

public interface WarehouseApi {

    @Description("Добавить новый товар на склад")
    @PutMapping("/api/v1/warehouse")
    void addNewProduct(@RequestBody NewProductInWarehouseRequest request);

    @Description("Предварительно проверить что количество товаров на складе достаточно для данной корзины продуктов")
    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto checkCart(@RequestBody ShoppingCartDto shoppingCartDto);

    @Description("Принять товар на склад")
    @PostMapping("/api/v1/warehouse/add")
    void addProduct(@RequestBody AddProductToWarehouseRequest request);

    @Description("Предоставить адрес склада для расчёта доставки")
    @GetMapping("/api/v1/warehouse/address")
    AddressDto getAddress();

    @Description("Передать товары в доставку.")
    @PostMapping("/api/v1/warehouse/shipped")
    void shippedProducts(@RequestBody ShippedToDeliveryRequest request);

    @Description("Принять возврат товаров на склад.")
    @PostMapping("/api/v1/warehouse/return")
    void returnProducts(@RequestBody Map<UUID, Integer> products);

    @Description("Собрать товары к заказу для подготовки к отправке")
    @PostMapping("/api/v1/warehouse/assembly")
    BookedProductsDto assemblyProducts(@RequestBody AssemblyProductsForOrderRequest request);


}
