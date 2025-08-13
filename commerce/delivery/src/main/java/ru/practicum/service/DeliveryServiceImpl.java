package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.delivery.DeliveryDto;
import ru.practicum.dto.delivery.enums.DeliveryState;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Delivery;
import ru.practicum.repositiry.DeliveryRepository;
import ru.practicum.utils.Mapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{

    private static final Double  BASE_COST = 5.0;

    private final DeliveryRepository deliveryRepository;


    @Override
    public DeliveryDto addDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = Delivery.builder()
                .orderId(deliveryDto.getOrderId())
                .toAddress(Mapper.fromAddressDto(deliveryDto.getToAddress()))
                .fromAddress(Mapper.fromAddressDto(deliveryDto.getFromAddress()))
                .deliveryState(DeliveryState.CREATED)
                .build();
        return Mapper.toDeliveryDto(deliveryRepository.save(delivery));

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
    public Double deliveryCost(OrderDto orderDto) {

        Double totalCost = BASE_COST;
        Delivery delivery = findDeliveryById(orderDto.getDeliveryId());

        /*
        Умножаем базовую стоимость на число, зависящее от адреса склада:
            Если адрес склада содержит название ADDRESS_1, то умножаем на 1.
            Если адрес склада содержит название ADDRESS_2, то умножаем на 2. Складываем получившийся результат с базовой стоимостью.
         */

        String country = delivery.getFromAddress().getCountry();
        if (country.contains("ADDRESS_1"))
            totalCost = BASE_COST * 1;
        else if (country.contains("ADDRESS_2"))
                totalCost += BASE_COST*2;

        // Если в заказе есть признак хрупкости, умножаем сумму на 0.2 и складываем с полученным на предыдущем шаге итогом.
        if (orderDto.getFragile())
            totalCost *= 1.2;

        // Добавляем к сумме, полученной на предыдущих шагах, вес заказа, умноженный на 0.3.
        totalCost += orderDto.getDeliveryWeight() * 0.3;

        // Складываем с полученным на прошлом шаге итогом объём, умноженный на 0.2.

        totalCost += orderDto.getDeliveryVolume() * 0.2;

        /*
        Если нужно доставить на ту же улицу, где находится склад (улица доставки совпадает с адресом склада),
        то стоимость доставки не увеличивается. Иначе её нужно умножить на 0.2 и сложить с полученным
        на предыдущем шаге итогом.
         */

        String toDeliveryStreet = delivery.getToAddress().getStreet();
        String fromDeliveryStreet = delivery.getFromAddress().getStreet();

        if(!toDeliveryStreet.equals(fromDeliveryStreet))
            totalCost *= 1.2;

        return totalCost;

    }

    private Delivery findDeliveryById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(
                () -> new NotFoundException("Delivery with id " + deliveryId + " not found")
        );
    }
}
