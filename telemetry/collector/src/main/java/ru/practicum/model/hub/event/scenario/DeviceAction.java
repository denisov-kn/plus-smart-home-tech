package ru.practicum.model.hub.event.scenario;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceAction {
    String sensorId;
    ActionType type;
    Integer value;
}
