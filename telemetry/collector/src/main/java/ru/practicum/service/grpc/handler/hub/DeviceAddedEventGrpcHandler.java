package ru.practicum.service.grpc.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.model.hub.event.device.DeviceAddedEvent;
import ru.practicum.service.kafka.KafkaService;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DeviceAddedEventGrpcHandler implements HubEventGrpcHandler{

    private final KafkaService kafkaService;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }

    @Override
    public void handle(HubEventProto event) {
        DeviceAddedEvent deviceAddedEvent = new DeviceAddedEvent();
        deviceAddedEvent.setId(event.getDeviceAdded().getId());
        deviceAddedEvent.setHubId(event.getHubId());
        deviceAddedEvent.setTimestamp(
                Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(),
                        event.getTimestamp().getNanos())
        );
        deviceAddedEvent.setDeviceType(MappingUtils.mapDeviceType(
                event.getDeviceAdded().getType())
        );

        kafkaService.kafkaHubEvent(deviceAddedEvent);
    }
}
