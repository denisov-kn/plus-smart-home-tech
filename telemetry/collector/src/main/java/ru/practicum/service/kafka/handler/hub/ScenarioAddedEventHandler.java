package ru.practicum.service.kafka.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.model.hub.event.HubEventType;
import ru.practicum.model.hub.event.scenario.ScenarioAddedEvent;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.practicum.utils.MappingUtils;

import java.util.List;

@Component
public class ScenarioAddedEventHandler implements HubEventHandler<ScenarioAddedEvent> {

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }

    @Override
    public HubEventAvro handle(ScenarioAddedEvent event) {
        List<ScenarioConditionAvro> conditions = event.getConditions().stream()
                .map(MappingUtils::mapAvroScenarioCondition)
                .toList();

        List<DeviceActionAvro> actions = event.getActions().stream()
                .map(MappingUtils::mapAvroDeviceAction)
                .toList();

        ScenarioAddedEventAvro payload = ScenarioAddedEventAvro.newBuilder()
                .setName(event.getName())
                .setConditions(conditions)
                .setActions(actions)
                .build();

        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();
    }
}
