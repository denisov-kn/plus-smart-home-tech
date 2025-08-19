package ru.practicum.api;



import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.dto.payment.PaymentDto;

import java.util.UUID;

public interface PaymentApi {

    @Description("Формирование оплаты для заказа (переход в платежный шлюз).")
    @PostMapping("/api/v1/payment")
    PaymentDto createPayment(@RequestBody OrderDto order);

    @Description("Расчёт полной стоимости заказа.")
    @PostMapping("/api/v1/payment/totalCost")
    Double totalCost(@RequestBody OrderDto orderDto);

    @Description("Метод для эмуляции успешной оплаты в платежного шлюза.")
    @PostMapping("/api/v1/payment/refund")
    void refund(@RequestParam UUID paymentId);

    @Description("Расчёт стоимости товаров в заказе.")
    @PostMapping("/api/v1/payment/productCost")
    Double productCost(@RequestBody OrderDto order);

    @Description("Метод для эмуляции отказа в оплате платежного шлюза.")
    @PostMapping("/api/v1/payment/failed")
    void failed(@RequestParam UUID paymentId);
}
