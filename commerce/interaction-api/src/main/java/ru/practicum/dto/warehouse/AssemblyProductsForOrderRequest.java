package ru.practicum.dto.warehouse;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@ToString
public class AssemblyProductsForOrderRequest {
    Map<UUID, Integer> products;
    UUID orderId;
}
