package ru.practicum.dto.order;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ProductReturnRequest {
    UUID orderId;
    Map<UUID, Integer> products;
}
