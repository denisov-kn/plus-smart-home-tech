package ru.practicum.utils;

import ru.practicum.dto.delivery.DeliveryDto;
import ru.practicum.dto.warehouse.AddressDto;
import ru.practicum.model.Address;
import ru.practicum.model.Delivery;

public class Mapper {
    public static Address fromAddressDto(AddressDto addressDto) {
        return Address.builder()
                .country(addressDto.getCountry())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .house(addressDto.getHouse())
                .flat(addressDto.getFlat())
                .build();
    }

    public static DeliveryDto toDeliveryDto(Delivery delivery) {
        return DeliveryDto.builder()
                .deliveryId(delivery.getDeliveryId())
                .orderId(delivery.getDeliveryId())
                .toAddress(Mapper.toAddressDto(delivery.getToAddress()))
                .fromAddress(Mapper.toAddressDto(delivery.getFromAddress()))
                .deliveryState(delivery.getDeliveryState())
                .build();
    }

    public static AddressDto toAddressDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .flat(address.getFlat())
                .build();
    }
}
