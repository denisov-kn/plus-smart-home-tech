package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.api.PaymentApi;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.dto.payment.PaymentDto;
import ru.practicum.service.PaymentService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;

    @Override
    public PaymentDto createPayment(OrderDto order) {
        log.info("Create payment for order: {}", order);
        PaymentDto payment = paymentService.createPayment(order);
        log.info("Payment created: {}", payment);
        return payment;
    }

    @Override
    public Double totalCost(UUID orderId) {
        log.info("Total cost for order: {}", orderId);
        Double cost = paymentService.totalCost(orderId);
        log.info("Total cost: {}", cost);
        return cost;
    }

    @Override
    public void refund(UUID paymentId) {
        log.info("Refund paymentId: {}", paymentId);
        paymentService.refund(paymentId);
        log.info("Payment was refunded: {} ", paymentId);
    }

    @Override
    public Double productCost(OrderDto order) {
        log.info("Product cost for order: {}", order);
        Double cost = paymentService.productCost(order);
        log.info("Product cost is: {}", cost);
        return cost;
    }

    @Override
    public void failed(UUID paymentId) {
        log.info("Request for payment failed: {}", paymentId);
        paymentService.failed(paymentId);
        log.info("Payment was failed: {}", paymentId);
    }
}
