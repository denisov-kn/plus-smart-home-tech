package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.clients.OrderClient;
import ru.practicum.clients.ShoppingStoreClient;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.dto.payment.PaymentDto;
import ru.practicum.dto.payment.enums.PaymentState;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Payment;
import ru.practicum.repository.PaymentRepository;
import ru.practicum.utils.Mapper;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ShoppingStoreClient shoppingStoreClient;
    private final OrderClient orderClient;


    @Override
    public PaymentDto createPayment(OrderDto orderDto) {
       Payment payment = Payment.builder()
               .state(PaymentState.PENDING)
               .deliveryTotal(orderDto.getDeliveryPrice())
               .feeTotal(getFee(orderDto.getTotalPrice()))
               .totalPayment(orderDto.getTotalPrice())
               .orderId(orderDto.getOrderId())
               .build();

       paymentRepository.save(payment);
       return Mapper.toPaymentDto(payment);
    }

    @Override
    public Double totalCost(OrderDto orderDto) {

        /*
        a. От суммы стоимости всех товаров нужно взять 10% — это будет НДС.
        Например, если товар стоит 100 рублей, то НДС составит 10 рублей.
        b. Налог прибавляем к стоимости товара, получим 110 рублей.
        c. Добавляем стоимость доставки — 50 рублей. И в итоге пользователь видит сумму: 160 рублей.
         */
        Double productPrice = orderDto.getProductPrice();
        Double deliveryPrice = orderDto.getDeliveryPrice();
        Double feeTotal = getFee(productPrice);
        return productPrice + deliveryPrice + feeTotal;
    }

    private static Double getFee(Double productPrice) {
        return productPrice * 0.1;
    }

    @Override
    public void refund(UUID paymentId) {
        Payment payment = getPayment(paymentId);
        payment.setState(PaymentState.SUCCESS);
        paymentRepository.save(payment);
        orderClient.payOrder(payment.getOrderId());
    }

    private Payment getPayment(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(
                () -> new NotFoundException("Payment with id " + paymentId + " not found")
        );
    }

    @Override
    public Double productCost(OrderDto order) {
        Set<UUID> productIds = order.getProducts().keySet();
        double productCost = 0.0;
        for (UUID productId : productIds) {
            productCost = Double.sum(productCost, shoppingStoreClient.getProduct(productId).getPrice());
        }

        return productCost;
    }

    @Override
    public void failed(UUID paymentId) {
        Payment payment = getPayment(paymentId);
        payment.setState(PaymentState.FAILED);
        paymentRepository.save(payment);
        orderClient.payOrderFailed(payment.getOrderId());
    }
}
