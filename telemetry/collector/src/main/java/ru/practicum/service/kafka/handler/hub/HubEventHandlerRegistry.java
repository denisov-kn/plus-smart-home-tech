package ru.practicum.service.kafka.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HubEventHandlerRegistry {

    private final List<HubEventHandler> handlers;


    public HubEventAvro handle(HubEventProto proto) {
        return handlers.stream()
                .filter(h -> h.supports(proto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No handler for proto payload: " + proto.getPayloadCase()))
                .handle(proto);
    }
}
