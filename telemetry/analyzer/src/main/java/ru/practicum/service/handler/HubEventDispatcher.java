package ru.practicum.service.handler;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HubEventDispatcher {
    private final Map<Class<?>, HubEventHandler<?>> handlers = new HashMap<>();

    public HubEventDispatcher(List<HubEventHandler<?>> handlerList) {
        for (HubEventHandler<?> handler : handlerList) {
            handlers.put(handler.payloadType(), handler);
        }
    }


    public void dispatch(HubEventAvro event) {
        SpecificRecordBase payload = (SpecificRecordBase) event.getPayload();
        HubEventHandler<?> handler = handlers.get(payload.getClass());

        if (handler == null) {
            throw new IllegalArgumentException("No handler found for payload type: " + payload.getClass());
        }

        @SuppressWarnings("unchecked")
        HubEventHandler<SpecificRecordBase> typedHandler = (HubEventHandler<SpecificRecordBase>) handler;

        typedHandler.handle(payload, event.getHubId(), event.getTimestamp());
    }

}
