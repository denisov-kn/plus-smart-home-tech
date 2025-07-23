package ru.practicum.service.kafka.handler.sensor;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;


public interface SensorEventHandler {
    boolean supports(SensorEventProto proto);
    SensorEventAvro handle(SensorEventProto proto);
}