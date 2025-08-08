package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.delivery.DeliveryDto;

import java.util.UUID;

@Service
public class DeliveryServiceImpl implements DeliveryService{
    @Override
    public DeliveryDto addDelivery(DeliveryDto deliveryDto) {
        return null;
    }

    @Override
    public void successfulDelivery(UUID deliveryId) {

    }

    @Override
    public void pickedDelivery(UUID deliveryId) {

    }

    @Override
    public void failedDelivery(UUID deliveryId) {

    }

    @Override
    public Double deliveryCost(DeliveryDto deliveryDto) {
        return 0.0;
    }
}
