package ru.practicum.model.snapshot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.sensor.SensorEvent;

import java.time.Instant;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SensorState {
    Instant timestamp;
    SensorEvent sensorEvent;
}
