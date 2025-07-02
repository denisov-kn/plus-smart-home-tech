package ru.practicum.sevice.grpc.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.model.hub.event.device.DeviceRemovedEvent;
import ru.practicum.sevice.kafka.KafkaService;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DeviceRemovedEventGrpcHandler implements HubEventGrpcHandler{

    private  final KafkaService kafkaService;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_REMOVED;
    }

    @Override
    public void handle(HubEventProto event) {
        DeviceRemovedEvent deviceRemovedEvent = new DeviceRemovedEvent();
        deviceRemovedEvent.setHubId(event.getHubId());
        deviceRemovedEvent.setTimestamp(
                Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(),
                        event.getTimestamp().getNanos())
        );
        deviceRemovedEvent.setId(event.getDeviceRemoved().getId());

        kafkaService.kafkaHubEvent(deviceRemovedEvent);

    }
}
