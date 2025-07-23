package ru.practicum.service.kafka.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
public class DeviceRemovedEventHandler implements HubEventHandler {


    @Override
    public boolean supports(HubEventProto proto) {
        return proto.hasDeviceRemoved();
    }

    @Override
    public HubEventAvro handle(HubEventProto proto) {
        return HubEventAvro.newBuilder()
                .setHubId(proto.getHubId())
                .setTimestamp(MappingUtils.toInstant(proto.getTimestamp()))
                .setPayload(DeviceRemovedEventAvro.newBuilder()
                        .setId(proto.getDeviceRemoved().getId())
                        .build())
                .build();
    }
}
