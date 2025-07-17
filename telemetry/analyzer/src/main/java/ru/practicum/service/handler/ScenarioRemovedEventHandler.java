package ru.practicum.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ScenarioRemovedEventHandler implements HubEventHandler<ScenarioRemovedEventAvro> {
    private final ScenarioRepository scenarioRepository;

    @Override
    public void handle(ScenarioRemovedEventAvro payload, String hubId, Instant timestamp) {
        scenarioRepository.findByHubIdAndName(hubId, payload.getName())
                .ifPresent(
                        s-> scenarioRepository.deleteByHubIdAndName(hubId, payload.getName())
                );
    }

    @Override
    public Class<ScenarioRemovedEventAvro> payloadType() {
        return ScenarioRemovedEventAvro.class;
    }
}
