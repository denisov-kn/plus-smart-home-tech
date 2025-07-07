package ru.practicum.service.grpc.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.ClimateSensorEvent;
import ru.practicum.service.kafka.KafkaService;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;


@Component
@RequiredArgsConstructor
public class ClimateSensorEventGrpcHandler implements SensorEventGrpcHandler {

    private final KafkaService kafkaService;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto eventProto) {

        ClimateSensorEvent climateSensorEvent = new ClimateSensorEvent();
        climateSensorEvent.setId(eventProto.getId());
        climateSensorEvent.setHubId(eventProto.getHubId());
        climateSensorEvent.setTimestamp(
                Instant.ofEpochSecond(
                        eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos())
        );
        climateSensorEvent.setHumidity(eventProto.getClimateSensorEvent().getHumidity());
        climateSensorEvent.setTemperatureC(eventProto.getClimateSensorEvent().getTemperatureC());
        climateSensorEvent.setCo2Level(eventProto.getClimateSensorEvent().getCo2Level());

        kafkaService.kafkaSensorEvent(climateSensorEvent);
    }
}
