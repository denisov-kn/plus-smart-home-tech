package ru.practicum.sevice.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.model.hub.event.HubEventType;
import ru.practicum.model.hub.event.scenario.ScenarioRemovedEvent;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Component
public class ScenarioRemovedEventHandler implements HubEventHandler<ScenarioRemovedEvent> {

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVED;
    }

    @Override
    public HubEventAvro handle(ScenarioRemovedEvent event) {
        ScenarioRemovedEventAvro payload = ScenarioRemovedEventAvro.newBuilder()
                .setName(event.getName())
                .build();

        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();
    }

}