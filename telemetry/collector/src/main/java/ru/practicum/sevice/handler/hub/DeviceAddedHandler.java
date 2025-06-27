package ru.practicum.sevice.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.model.hub.event.HubEventType;
import ru.practicum.model.hub.event.device.DeviceAddedEvent;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
public class DeviceAddedHandler implements HubEventHandler<DeviceAddedEvent> {

    @Override
    public HubEventAvro handle(DeviceAddedEvent e) {
        DeviceAddedEventAvro payload = DeviceAddedEventAvro.newBuilder()
                .setId(e.getId())
                .setType(MappingUtils.mapDeviceType(e.getDeviceType()))
                .build();

        return HubEventAvro.newBuilder()
                .setHubId(e.getHubId())
                .setTimestamp(e.getTimestamp())
                .setPayload(payload)
                .build();
    }

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}
