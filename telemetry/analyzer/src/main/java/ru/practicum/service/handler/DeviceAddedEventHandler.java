package ru.practicum.service.handler;

import lombok.RequiredArgsConstructor;
import ru.practicum.entity.SensorEntity;
import org.springframework.stereotype.Component;
import ru.practicum.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DeviceAddedEventHandler implements HubEventHandler<DeviceAddedEventAvro>{

    private final SensorRepository sensorRepository;

    @Override
    public void handle(DeviceAddedEventAvro payload, String hubId, Instant timestamp) {

        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setId(payload.getId());
        sensorEntity.setHubId(hubId);
        sensorRepository.save(sensorEntity);

    }

    @Override
    public Class<DeviceAddedEventAvro> payloadType() {
        return DeviceAddedEventAvro.class;
    }
}
