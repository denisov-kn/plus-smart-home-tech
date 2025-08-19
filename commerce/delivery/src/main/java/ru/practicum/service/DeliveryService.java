package ru.practicum.service;


import ru.practicum.dto.delivery.DeliveryDto;
import ru.practicum.dto.order.OrderDto;

import java.util.UUID;

public interface DeliveryService {

    DeliveryDto addDelivery(DeliveryDto deliveryDto);

    void successfulDelivery(UUID deliveryId);

    void pickedDelivery(UUID deliveryId);

    void failedDelivery(UUID deliveryId);

    Double deliveryCost(OrderDto orderDto);
}
