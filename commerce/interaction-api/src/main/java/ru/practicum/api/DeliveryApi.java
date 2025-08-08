package ru.practicum.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.delivery.DeliveryDto;

import java.util.UUID;

public interface DeliveryApi {
    @PutMapping("/api/v1/delivery")
    DeliveryDto addDelivery(@RequestBody DeliveryDto deliveryDto);

    @PostMapping("/api/v1/delivery/successful")
    void successfulDelivery(@RequestParam UUID deliveryId);

    @PostMapping("/api/v1/delivery/picked")
    void pickedDelivery(@RequestParam UUID deliveryId);

    @PostMapping("/api/v1/delivery/failed")
    void failedDelivery(@RequestParam UUID deliveryId);
    @PostMapping("/api/v1/delivery/cost")
    Double deliveryCost(@RequestBody DeliveryDto deliveryDto);
}
