package ru.practicum.service.grpc.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.MotionSensorEvent;
import ru.practicum.service.kafka.KafkaService;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class MotionSensorEventGrpcHandler implements SensorEventGrpcHandler{


    private final KafkaService kafkaService;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto eventProto) {

        MotionSensorEvent sensorEvent = new MotionSensorEvent();
        sensorEvent.setId(eventProto.getId());
        sensorEvent.setHubId(eventProto.getHubId());
        sensorEvent.setTimestamp(
                Instant.ofEpochSecond(
                        eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos())
        );
        sensorEvent.setMotion(eventProto.getMotionSensorEvent().getMotion());
        sensorEvent.setVoltage(eventProto.getMotionSensorEvent().getVoltage());
        sensorEvent.setLinkQuality(eventProto.getMotionSensorEvent().getLinkQuality());

        kafkaService.kafkaSensorEvent(sensorEvent);

    }
}
