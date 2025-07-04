package ru.practicum.sevice.kafka.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.SensorEventType;
import ru.practicum.model.sensor.SwitchSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

@Component
public class SwitchSensorEventHandler implements SensorEventHandler<SwitchSensorEvent> {

    @Override
    public SensorEventAvro handle(SwitchSensorEvent event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(SwitchSensorAvro.newBuilder()
                        .setState(event.getState())
                        .build())
                .build();
    }

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}

