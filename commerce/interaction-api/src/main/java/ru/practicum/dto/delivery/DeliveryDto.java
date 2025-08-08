package ru.practicum.dto.delivery;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.delivery.enums.DeliveryState;
import ru.practicum.dto.warehouse.AddressDto;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class DeliveryDto {
    UUID deliveryId;
    AddressDto fromAddress;
    AddressDto toAddress;
    UUID orderId;
    DeliveryState deliveryState;
}
