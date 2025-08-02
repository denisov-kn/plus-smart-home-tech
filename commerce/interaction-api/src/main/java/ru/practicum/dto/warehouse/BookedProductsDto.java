package ru.practicum.dto.warehouse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class BookedProductsDto {
    Double deliveryWeight;
    Double deliveryVolume;
    Boolean fragile;
}
