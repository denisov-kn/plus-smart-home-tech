package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.DeliveryApi;
import ru.practicum.dto.delivery.DeliveryDto;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.service.DeliveryService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DeliveryController implements DeliveryApi {

    private final DeliveryService deliveryService;

    @Override
    public DeliveryDto addDelivery(DeliveryDto deliveryDto) {
        log.info("Add delivery request: {}", deliveryDto);
        DeliveryDto delivery = deliveryService.addDelivery(deliveryDto);
        log.info("Delivery added: {}", delivery);
        return delivery;
    }

    @Override
    public void successfulDelivery(UUID deliveryId) {
        log.info("Successful delivery request: {}", deliveryId);
        deliveryService.successfulDelivery(deliveryId);
        log.info("Delivery successful");
    }

    @Override
    public void pickedDelivery(UUID deliveryId) {
        log.info("Picked delivery request: {}", deliveryId);
        deliveryService.pickedDelivery(deliveryId);
        log.info("Picked delivery");
    }

    @Override
    public void failedDelivery(UUID deliveryId) {
        log.info("Failed delivery request: {}", deliveryId);
        deliveryService.failedDelivery(deliveryId);
        log.info("Failed delivery");
    }

    @Override
    public Double deliveryCost(OrderDto orderDto) {
        log.info("Delivery cost OrderDto: {}", orderDto);
        Double deliveryCost = deliveryService.deliveryCost(orderDto);
        log.info("Delivery cost: {}", deliveryCost);
        return deliveryCost;
    }
}
