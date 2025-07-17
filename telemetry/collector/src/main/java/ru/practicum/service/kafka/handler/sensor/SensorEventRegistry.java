package ru.practicum.service.kafka.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;


import java.util.List;


@Component
@RequiredArgsConstructor
public class SensorEventRegistry {

    private final List<SensorEventHandler> handlers;

    @SuppressWarnings("unchecked")
    public SensorEventAvro handle(SensorEventProto proto) {
        return handlers.stream()
                .filter(h -> h.supports(proto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No handler found for proto: " + proto))
                .handle(proto);
    }

}
