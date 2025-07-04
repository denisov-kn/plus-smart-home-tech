package ru.practicum.sevice.grpc.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.LightSensorEvent;
import ru.practicum.sevice.kafka.KafkaService;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LightSensorEventGrpcHandler implements SensorEventGrpcHandler {

    private final KafkaService kafkaService;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto event) {
        LightSensorEvent lightSensorEvent = new LightSensorEvent();
        lightSensorEvent.setId(event.getId());
        lightSensorEvent.setHubId(event.getHubId());
        lightSensorEvent.setTimestamp(
                Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(),
                        event.getTimestamp().getNanos())
        );
        lightSensorEvent.setLinkQuality(event.getLightSensorEvent().getLinkQuality());
        lightSensorEvent.setLuminosity(event.getLightSensorEvent().getLuminosity());

        kafkaService.kafkaSensorEvent(lightSensorEvent);
    }
}
