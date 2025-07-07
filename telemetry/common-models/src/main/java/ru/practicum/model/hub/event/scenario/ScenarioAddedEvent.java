package ru.practicum.model.hub.event.scenario;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.hub.event.HubEvent;
import ru.practicum.model.hub.event.HubEventType;

import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioAddedEvent extends HubEvent {

    @NotNull
    @Size(min = 3, max = 2147483647)
    String name;
    @NotNull
    @NotEmpty
    List<ScenarioCondition> conditions;
    @NotNull
    @NotEmpty
    List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
