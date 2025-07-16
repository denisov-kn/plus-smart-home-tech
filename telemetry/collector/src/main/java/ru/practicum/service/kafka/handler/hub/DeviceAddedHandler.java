package ru.practicum.service.kafka.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
public class DeviceAddedHandler implements HubEventHandler {

    @Override
    public boolean supports(HubEventProto proto) {
        return proto.hasDeviceAdded();
    }

    @Override
    public HubEventAvro handle(HubEventProto proto) {
        return HubEventAvro.newBuilder()
                .setHubId(proto.getHubId())
                .setTimestamp(MappingUtils.toInstant(proto.getTimestamp()))
                .setPayload(DeviceAddedEventAvro.newBuilder()
                        .setId(proto.getDeviceAdded().getId())
                        .setType(MappingUtils.map(proto.getDeviceAdded().getType()))
                        .build())
                .build();
    }
}
