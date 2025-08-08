package ru.practicum.service;


import ru.practicum.dto.delivery.DeliveryDto;

import java.util.UUID;

public interface DeliveryService {

    DeliveryDto addDelivery(DeliveryDto deliveryDto);

    void successfulDelivery(UUID deliveryId);

    void pickedDelivery(UUID deliveryId);

    void failedDelivery(UUID deliveryId);

    Double deliveryCost(DeliveryDto deliveryDto);
}
