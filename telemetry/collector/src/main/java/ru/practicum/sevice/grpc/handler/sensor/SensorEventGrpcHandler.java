package ru.practicum.sevice.grpc.handler.sensor;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

public interface SensorEventGrpcHandler {
    SensorEventProto.PayloadCase getMessageType();
    void handle(SensorEventProto event);
}
