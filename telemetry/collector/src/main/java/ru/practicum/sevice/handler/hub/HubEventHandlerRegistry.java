package ru.practicum.sevice.handler.hub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.model.hub.event.HubEvent;
import ru.practicum.model.hub.event.HubEventType;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HubEventHandlerRegistry {
    private final Map<HubEventType, HubEventHandler<?>> handlers = new HashMap<>();

    @Autowired
    public HubEventHandlerRegistry(List<HubEventHandler<?>> handlerList) {
        for (HubEventHandler<?> handler : handlerList) {
            handlers.put(handler.getType(), handler);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends HubEvent> HubEventAvro handle(T event) {
        HubEventHandler<T> handler = (HubEventHandler<T>) handlers.get(event.getType());
        if (handler == null) {
            throw new IllegalArgumentException("No handler for type: " + event.getType());
        }
        return handler.handle(event);
    }
}
