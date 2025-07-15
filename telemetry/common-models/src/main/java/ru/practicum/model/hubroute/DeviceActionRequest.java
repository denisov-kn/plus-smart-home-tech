package ru.practicum.model.hubroute;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotNull;
import ru.practicum.model.hub.event.scenario.DeviceAction;

import java.time.Instant;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceActionRequest {
    @NotNull
    String hubId;

    @NotNull
    String scenarioName;

    @NotNull
    DeviceAction action;

    @NotNull
    Instant timestamp;
}
