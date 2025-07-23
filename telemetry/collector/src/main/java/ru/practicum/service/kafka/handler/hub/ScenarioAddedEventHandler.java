package ru.practicum.service.kafka.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;

@Component
public class ScenarioAddedEventHandler implements HubEventHandler {

    @Override
    public boolean supports(HubEventProto proto) {
        return proto.hasScenarioAdded();
    }

    @Override
    public HubEventAvro handle(HubEventProto proto) {
        ScenarioAddedEventProto event = proto.getScenarioAdded();

        return HubEventAvro.newBuilder()
                .setHubId(proto.getHubId())
                .setTimestamp(MappingUtils.toInstant(proto.getTimestamp()))
                .setPayload(ScenarioAddedEventAvro.newBuilder()
                        .setName(event.getName())
                        .setConditions(MappingUtils.mapConditions(event.getConditionsList()))
                        .setActions(MappingUtils.mapActions(event.getActionsList()))
                        .build())
                .build();
    }

}
