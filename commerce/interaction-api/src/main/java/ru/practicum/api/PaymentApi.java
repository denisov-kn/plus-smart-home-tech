package ru.practicum.api;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.dto.payment.PaymentDto;

import java.util.UUID;

public interface PaymentApi {

    @PostMapping("/api/v1/payment")
    PaymentDto createPayment(@RequestBody OrderDto order);

    @PostMapping("/api/v1/payment/totalCost")
    Double totalCost(@RequestParam OrderDto orderDto);

    @PostMapping("/api/v1/payment/refund")
    void refund(@RequestParam UUID paymentId);

    @PostMapping("/api/v1/payment/productCost")
    Double productCost(@RequestBody OrderDto order);

    @PostMapping("/api/v1/payment/failed")
    void failed(@RequestParam UUID paymentId);
}
