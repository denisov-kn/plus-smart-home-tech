package ru.practicum.dto.warehouse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class AddressDto {
    String country;
    String city;
    String street;
    String house;
    String flat;
}
