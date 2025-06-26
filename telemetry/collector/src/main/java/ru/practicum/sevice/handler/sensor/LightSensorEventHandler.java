package ru.practicum.sevice.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.LightSensorEvent;
import ru.practicum.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
public class LightSensorEventHandler implements SensorEventHandler<LightSensorEvent> {

    @Override
    public SensorEventAvro handle(LightSensorEvent event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(LightSensorAvro.newBuilder()
                        .setLinkQuality(event.getLinkQuality())
                        .setLuminosity(event.getLuminosity())
                        .build())
                .build();
    }

    @Override
    public SensorEventType getType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }
}
