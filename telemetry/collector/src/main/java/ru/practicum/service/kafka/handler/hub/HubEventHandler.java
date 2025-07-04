package ru.practicum.service.kafka.handler.hub;

import ru.practicum.model.hub.event.HubEvent;
import ru.practicum.model.hub.event.HubEventType;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubEventHandler <T extends HubEvent>{
    HubEventAvro handle(T event);
    HubEventType getType();
}
