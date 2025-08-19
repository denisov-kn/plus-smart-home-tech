package ru.practicum.service;

import ru.practicum.dto.order.OrderDto;
import ru.practicum.dto.payment.PaymentDto;
import java.util.UUID;

public interface PaymentService {

    PaymentDto createPayment(OrderDto order);

    Double totalCost(OrderDto order);

    void refund(UUID paymentId);

    Double productCost(OrderDto order);

    void failed(UUID paymentId);
}
