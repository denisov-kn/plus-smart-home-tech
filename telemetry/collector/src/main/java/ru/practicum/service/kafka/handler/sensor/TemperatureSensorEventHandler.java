package ru.practicum.service.kafka.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.SensorEventType;
import ru.practicum.model.sensor.TemperatureSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

@Component
public class TemperatureSensorEventHandler implements SensorEventHandler<TemperatureSensorEvent> {

    @Override
    public SensorEventAvro handle(TemperatureSensorEvent event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(TemperatureSensorAvro.newBuilder()
                        .setTemperatureC(event.getTemperatureC())
                        .setTemperatureF(event.getTemperatureF())
                        .build())
                .build();
    }

    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
