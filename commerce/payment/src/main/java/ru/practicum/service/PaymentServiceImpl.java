package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.dto.payment.PaymentDto;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentDto createPayment(OrderDto order) {
        return null;
    }

    @Override
    public Double totalCost(UUID orderId) {
        return 0.0;
    }

    @Override
    public Double refund(UUID paymentId) {
        return 0.0;
    }

    @Override
    public Double productCost(OrderDto order) {
        return 0.0;
    }

    @Override
    public void failed(UUID paymentId) {

    }
}
