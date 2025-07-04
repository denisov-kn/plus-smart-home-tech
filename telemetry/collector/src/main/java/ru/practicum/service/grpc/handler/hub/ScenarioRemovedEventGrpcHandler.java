package ru.practicum.service.grpc.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.model.hub.event.scenario.ScenarioRemovedEvent;
import ru.practicum.service.kafka.KafkaService;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ScenarioRemovedEventGrpcHandler implements HubEventGrpcHandler{

    private final KafkaService kafkaService;


    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_REMOVED;
    }

    @Override
    public void handle(HubEventProto event) {
        ScenarioRemovedEvent scenarioRemovedEvent = new ScenarioRemovedEvent();
        scenarioRemovedEvent.setHubId(event.getHubId());
        scenarioRemovedEvent.setTimestamp(
                Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(),
                        event.getTimestamp().getNanos())
        );
        scenarioRemovedEvent.setName(event.getScenarioRemoved().getName());

    }
}
