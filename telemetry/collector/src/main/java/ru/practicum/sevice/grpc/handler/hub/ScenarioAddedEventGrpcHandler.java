package ru.practicum.sevice.grpc.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.model.hub.event.scenario.ScenarioAddedEvent;
import ru.practicum.sevice.kafka.KafkaService;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ScenarioAddedEventGrpcHandler implements HubEventGrpcHandler{

    private final KafkaService kafkaService;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    public void handle(HubEventProto event) {
        ScenarioAddedEvent scenarioAddedEvent = new ScenarioAddedEvent();
        scenarioAddedEvent.setHubId(event.getHubId());
        scenarioAddedEvent.setTimestamp(
                Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(),
                        event.getTimestamp().getNanos())
        );
        scenarioAddedEvent.setName(event.getScenarioAdded().getName());
        scenarioAddedEvent.setActions(event.getScenarioAdded().getActionsList().stream()
                .map(MappingUtils::mapDeviceAction)
                .toList()
        );
        scenarioAddedEvent.setConditions(event.getScenarioAdded().getConditionsList().stream()
                .map(MappingUtils::mapScenarioCondition)
                .toList()
        );

        kafkaService.kafkaHubEvent(scenarioAddedEvent);
    }
}
