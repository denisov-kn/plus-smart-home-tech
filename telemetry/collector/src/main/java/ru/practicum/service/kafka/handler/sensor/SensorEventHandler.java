package ru.practicum.service.kafka.handler.sensor;

import ru.practicum.model.sensor.SensorEvent;
import ru.practicum.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

public interface SensorEventHandler<T extends SensorEvent> {
    SensorEventAvro handle(T event);
    SensorEventType getType();
}