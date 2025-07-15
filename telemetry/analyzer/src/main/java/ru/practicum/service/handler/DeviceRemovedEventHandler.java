package ru.practicum.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DeviceRemovedEventHandler implements HubEventHandler<DeviceRemovedEventAvro>{

    private final SensorRepository sensorRepository;

    @Override
    public void handle(DeviceRemovedEventAvro payload, String hubId, Instant timestamp) {
        sensorRepository.deleteById(payload.getId());
    }

    @Override
    public Class<DeviceRemovedEventAvro> payloadType() {
        return DeviceRemovedEventAvro.class;
    }
}
