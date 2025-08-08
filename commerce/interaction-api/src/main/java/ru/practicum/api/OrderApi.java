package ru.practicum.api;

import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.order.CreateNewOrderRequest;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.dto.order.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderApi {
    @GetMapping("/api/v1/order")
    List<OrderDto> getOrders(@RequestParam String username);

    @PutMapping("/api/v1/order")
    OrderDto createOrder(@RequestParam String username, @RequestBody CreateNewOrderRequest request);

    @PostMapping("/api/v1/order/return")
    OrderDto returnOrder(@RequestBody ProductReturnRequest request);

    @PostMapping("/api/v1/order/payment")
    OrderDto payOrder(@RequestParam UUID orderId);

    @PostMapping("/api/v1/order/payment/failed")
    OrderDto payOrderFailed(@RequestParam UUID orderId);

    @PostMapping("/api/v1/order/delivery")
    OrderDto deliveryOrder(@RequestParam UUID orderId);

    @PostMapping("/api/v1/order/delivery/failed")
    OrderDto deliveryOrderFailed(@RequestParam UUID orderId);

    @PostMapping("/api/v1/order/completed")
    OrderDto completedOrder(@RequestParam UUID orderId);

    @PostMapping("/api/v1/order/calculate/total")
    OrderDto calculateTotal(@RequestParam UUID orderId);

    @PostMapping("/api/v1/order/calculate/delivery")
    OrderDto calculateDelivery(@RequestParam UUID orderId);

    @PostMapping("/api/v1/order/assembly")
    OrderDto assemblyOrder(@RequestParam UUID orderId);

    @PostMapping("/api/v1/order/assembly/failed")
    OrderDto assemblyOrderFailed(@RequestParam UUID orderId);

}
