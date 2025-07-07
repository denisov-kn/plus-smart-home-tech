package ru.practicum.model.snapshot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SensorsSnapshot {
    String hubId;
    Instant timestamp;
    List<SensorState> sensorsState;
}
