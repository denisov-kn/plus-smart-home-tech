package ru.practicum.sevice.kafka.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.model.hub.event.HubEventType;
import ru.practicum.model.hub.event.device.DeviceRemovedEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
public class DeviceRemovedEventHandler implements HubEventHandler<DeviceRemovedEvent> {

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }

    @Override
    public HubEventAvro handle(DeviceRemovedEvent event) {
        DeviceRemovedEventAvro payload = DeviceRemovedEventAvro.newBuilder()
                .setId(event.getId())
                .build();

        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();
    }
}
