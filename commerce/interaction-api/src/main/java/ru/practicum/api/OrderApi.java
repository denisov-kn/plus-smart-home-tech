package ru.practicum.api;

import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.order.CreateNewOrderRequest;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.dto.order.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderApi {
    @Description("Получить заказы пользователя.")
    @GetMapping("/api/v1/order")
    List<OrderDto> getOrders(@RequestParam String username);

    @Description("Создать новый заказ в системе.")
    @PutMapping("/api/v1/order")
    OrderDto createOrder(@RequestParam String username, @RequestBody CreateNewOrderRequest request);

    @Description("Возврат заказа.")
    @PostMapping("/api/v1/order/return")
    OrderDto returnOrder(@RequestBody ProductReturnRequest request);

    @Description("Оплата заказа.")
    @PostMapping("/api/v1/order/payment")
    OrderDto payOrder(@RequestParam UUID orderId);

    @Description("Оплата заказа произошла с ошибкой.")
    @PostMapping("/api/v1/order/payment/failed")
    OrderDto payOrderFailed(@RequestParam UUID orderId);

    @Description("Доставка заказа.")
    @PostMapping("/api/v1/order/delivery")
    OrderDto deliveryOrder(@RequestParam UUID orderId);

    @Description("Доставка заказа произошла с ошибкой.")
    @PostMapping("/api/v1/order/delivery/failed")
    OrderDto deliveryOrderFailed(@RequestParam UUID orderId);

    @Description("Завершение заказа.")
    @PostMapping("/api/v1/order/completed")
    OrderDto completedOrder(@RequestParam UUID orderId);

    @Description("Расчёт стоимости заказа.")
    @PostMapping("/api/v1/order/calculate/total")
    OrderDto calculateTotal(@RequestParam UUID orderId);

    @Description("Расчёт стоимости доставки заказа.")
    @PostMapping("/api/v1/order/calculate/delivery")
    OrderDto calculateDelivery(@RequestParam UUID orderId);

    @Description("Сборка заказа.")
    @PostMapping("/api/v1/order/assembly")
    OrderDto assemblyOrder(@RequestParam UUID orderId);

    @Description("Сборка заказа произошла с ошибкой.")
    @PostMapping("/api/v1/order/assembly/failed")
    OrderDto assemblyOrderFailed(@RequestParam UUID orderId);

}
