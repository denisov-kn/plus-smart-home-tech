package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.OrderApi;
import ru.practicum.dto.order.CreateNewOrderRequest;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.dto.order.ProductReturnRequest;
import ru.practicum.service.OrderService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;


    @Override
    public List<OrderDto> getOrders(String username) {
        log.info("getOrders called for {}", username);
        List<OrderDto> orders =  orderService.getOrders(username);
        log.info("Orders: {}", orders);
        return orders;

    }

    @Override
    public OrderDto createOrder(String username, CreateNewOrderRequest request) {
        log.info("createOrder called for {}", username);
        log.info("CreateNewOrderRequest: {}", request);
        OrderDto orderDto = orderService.createOrder(username, request);
        log.info("Create OrderDto: {}", orderDto);
        return orderDto;
    }

    @Override
    public OrderDto returnOrder(ProductReturnRequest request) {
        log.info("returnOrder called for request {}", request);
        OrderDto orderDto = orderService.returnOrder(request);
        log.info("Return order: {}", orderDto);
        return orderDto;
    }

    @Override
    public OrderDto payOrder(UUID orderId) {
        log.info("payOrder called for order {}", orderId);
        OrderDto orderDto = orderService.payOrder(orderId);
        log.info("Pay order: {}", orderDto);
        return orderDto;
    }

    @Override
    public OrderDto payOrderFailed(UUID orderId) {
        log.info("payOrderFailed called for order {}", orderId);
        OrderDto orderDto = orderService.payOrderFailed(orderId);
        log.info("Pay order failed: {}", orderDto);
        return orderDto;
    }

    @Override
    public OrderDto deliveryOrder(UUID orderId) {
        log.info("deliveryOrder called for order {}", orderId);
        OrderDto orderDto = orderService.deliveryOrder(orderId);
        log.info("Delivery order: {}", orderDto);
        return orderDto;
    }

    @Override
    public OrderDto deliveryOrderFailed(UUID orderId) {
        log.info("deliveryOrderFailed called for order {}", orderId);
        OrderDto orderDto = orderService.deliveryOrderFailed(orderId);
        log.info("Delivery order failed: {}", orderDto);
        return orderDto;
    }

    @Override
    public OrderDto completedOrder(UUID orderId) {
        log.info("completedOrder called for order {}", orderId);
        OrderDto orderDto = orderService.completedOrder(orderId);
        log.info("Completed order: {}", orderDto);
        return orderDto;
    }

    @Override
    public OrderDto calculateTotal(UUID orderId) {
        log.info("calculateTotal called for order {}", orderId);
        OrderDto orderDto = orderService.calculateTotal(orderId);
        log.info("Calculate total order: {}", orderDto);
        return orderDto;
    }

    @Override
    public OrderDto calculateDelivery(UUID orderId) {
        log.info("calculateDelivery called for order {}", orderId);
        OrderDto orderDto = orderService.calculateDelivery(orderId);
        log.info("Calculate delivery order: {}", orderDto);
        return orderDto;
    }

    @Override
    public OrderDto assemblyOrder(UUID orderId) {
        log.info("assemblyOrder called for order {}", orderId);
        OrderDto orderDto = orderService.assemblyOrder(orderId);
        log.info("Assembly order: {}", orderDto);
        return orderDto;
    }

    @Override
    public OrderDto assemblyOrderFailed(UUID orderId) {
        log.info("assemblyOrderFailed called for order {}", orderId);
        OrderDto orderDto = orderService.assemblyOrderFailed(orderId);
        log.info("Assembly order failed: {}", orderDto);
        return orderDto;
    }
}
