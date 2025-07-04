package ru.practicum.sevice.grpc.handler.hub;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

public interface HubEventGrpcHandler {
    HubEventProto.PayloadCase getMessageType();
    void handle(HubEventProto event);
}
