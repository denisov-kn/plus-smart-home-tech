package ru.practicum.sevice.handler.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.SensorEvent;
import ru.practicum.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SensorEventRegistry {

    private final Map<SensorEventType, SensorEventHandler<?>> handlers = new HashMap<>();

    @Autowired
    public SensorEventRegistry(List<SensorEventHandler<?>> handlerList) {
        for (SensorEventHandler<?> handler : handlerList) {
            handlers.put(handler.getType(), handler);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends SensorEvent> SensorEventAvro handle(T event) {
        SensorEventHandler<T> handler = (SensorEventHandler<T>) handlers.get(event.getType());
        if (handler == null) {
            throw new IllegalArgumentException("No handler for type: " + event.getType());
        }
        return handler.handle(event);
    }
}
