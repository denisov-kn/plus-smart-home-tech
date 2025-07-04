package ru.practicum.sevice.kafka.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.ClimateSensorEvent;
import ru.practicum.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
public class ClimateSensorEventHandler implements SensorEventHandler<ClimateSensorEvent> {

    @Override
    public SensorEventAvro handle(ClimateSensorEvent event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(ClimateSensorAvro.newBuilder()
                        .setTemperatureC(event.getTemperatureC())
                        .setHumidity(event.getHumidity())
                        .setCo2Level(event.getCo2Level())
                        .build())
                .build();
    }

    @Override
    public SensorEventType getType() {
        return SensorEventType.CLIMATE_SENSOR_EVENT;
    }
}
