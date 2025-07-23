package ru.practicum.service.kafka.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Component
public class ScenarioRemovedEventHandler implements HubEventHandler {

    @Override
    public boolean supports(HubEventProto proto) {
        return proto.hasScenarioRemoved();
    }

    @Override
    public HubEventAvro handle(HubEventProto proto) {
        return HubEventAvro.newBuilder()
                .setHubId(proto.getHubId())
                .setTimestamp(MappingUtils.toInstant(proto.getTimestamp()))
                .setPayload(ScenarioRemovedEventAvro.newBuilder()
                        .setName(proto.getScenarioRemoved().getName())
                        .build())
                .build();
    }

}