package ru.practicum.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.practicum.service.handler.HubEventDispatcher;

@Service
@RequiredArgsConstructor
public class HubEventService {

    private final HubEventDispatcher hubEventDispatcher;

    @Transactional
    public void processEvent(HubEventAvro hubEventavro) {
        hubEventDispatcher.dispatch(hubEventavro);
    }

}
