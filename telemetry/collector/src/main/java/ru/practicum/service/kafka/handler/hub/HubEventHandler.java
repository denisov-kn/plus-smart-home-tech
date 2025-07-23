package ru.practicum.service.kafka.handler.hub;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubEventHandler {
    boolean supports(HubEventProto proto);
    HubEventAvro handle(HubEventProto proto);
}
