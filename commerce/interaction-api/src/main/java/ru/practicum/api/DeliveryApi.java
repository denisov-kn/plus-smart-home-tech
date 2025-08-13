package ru.practicum.api;

import jdk.jfr.Description;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.delivery.DeliveryDto;
import ru.practicum.dto.order.OrderDto;

import java.util.UUID;

public interface DeliveryApi {

    @Description("Создать новую доставку в БД")
    @PutMapping("/api/v1/delivery")
    DeliveryDto addDelivery(@RequestBody DeliveryDto deliveryDto);

    @Description("Эмуляция успешной доставки товара")
    @PostMapping("/api/v1/delivery/successful")
    void successfulDelivery(@RequestParam UUID deliveryId);

    @Description("Эмуляция получения товара в доставку")
    @PostMapping("/api/v1/delivery/picked")
    void pickedDelivery(@RequestParam UUID deliveryId);

    @Description("Эмуляция неудачного вручения товара.")
    @PostMapping("/api/v1/delivery/failed")
    void failedDelivery(@RequestParam UUID deliveryId);

    @Description("Расчёт полной стоимости доставки заказа.")
    @PostMapping("/api/v1/delivery/cost")
    Double deliveryCost(@RequestBody OrderDto orderDto);
}
