package ru.practicum.model.hub.event.device;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.hub.event.HubEvent;
import ru.practicum.model.hub.event.HubEventType;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceAddedEvent extends HubEvent {

    @NotNull
    String id;
    @NotNull
    DeviceType deviceType;


    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}
