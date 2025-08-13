package ru.practicum.utils;

import ru.practicum.model.Payment;
import ru.practicum.dto.payment.PaymentDto;

public class Mapper {

    public static PaymentDto toPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .feeTotal(payment.getFeeTotal())
                .deliveryTotal(payment.getDeliveryTotal())
                .paymentId(payment.getPaymentId())
                .totalPayment(payment.getTotalPayment())
                .build();
    }
}
