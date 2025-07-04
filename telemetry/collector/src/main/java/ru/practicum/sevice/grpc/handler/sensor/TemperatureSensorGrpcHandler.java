package ru.practicum.sevice.grpc.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.Temperature;
import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.TemperatureSensorEvent;
import ru.practicum.sevice.kafka.KafkaService;
import ru.practicum.sevice.kafka.handler.sensor.TemperatureSensorEventHandler;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TemperatureSensorGrpcHandler implements SensorEventGrpcHandler{

    private final KafkaService kafkaService;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto event) {
        TemperatureSensorEvent temperatureSensorEvent = new TemperatureSensorEvent();
        temperatureSensorEvent.setId(event.getId());
        temperatureSensorEvent.setHubId(event.getHubId());
        temperatureSensorEvent.setTimestamp(
                Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(),
                        event.getTimestamp().getNanos())
        );
        temperatureSensorEvent.setTemperatureC(event.getTemperatureSensorEvent().getTemperatureC());
        temperatureSensorEvent.setTemperatureF(event.getTemperatureSensorEvent().getTemperatureF());

        kafkaService.kafkaSensorEvent(temperatureSensorEvent);
    }
}
