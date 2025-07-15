package ru.practicum.model.hub.event.scenario;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class DeviceAction {
    String sensorId;
    ActionType type;
    Integer value;
}
