package ru.practicum.sevice;

import org.springframework.stereotype.Component;
import ru.practicum.model.sensor.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Component
public class SensorEventMapper {
    public static SensorEventAvro toAvro(SensorEvent event) {


        switch (event.getType()) {
            case CLIMATE_SENSOR_EVENT:
                ClimateSensorEvent climate = (ClimateSensorEvent) event;
                return SensorEventAvro.newBuilder()
                        .setId(climate.getId())
                        .setHubId(climate.getHubId())
                        .setTimestamp(climate.getTimestamp())
                        .setPayload(ClimateSensorAvro.newBuilder()
                                .setTemperatureC(climate.getTemperatureC())
                                .setHumidity(climate.getHumidity())
                                .setCo2Level(climate.getCo2Level())
                                .build())
                        .build();

            case LIGHT_SENSOR_EVENT:
                LightSensorEvent light = (LightSensorEvent) event;
                return SensorEventAvro.newBuilder()
                        .setId(light.getId())
                        .setHubId(light.getHubId())
                        .setTimestamp(light.getTimestamp())
                        .setPayload(LightSensorAvro.newBuilder()
                                .setLinkQuality(light.getLinkQuality())
                                .setLuminosity(light.getLuminosity())
                                .build())
                        .build();

            case MOTION_SENSOR_EVENT:
                MotionSensorEvent motion = (MotionSensorEvent) event;
                return SensorEventAvro.newBuilder()
                        .setId(motion.getId())
                        .setHubId(motion.getHubId())
                        .setTimestamp(motion.getTimestamp())
                        .setPayload(MotionSensorAvro.newBuilder()
                                .setLinkQuality(motion.getLinkQuality())
                                .setMotion(motion.getMotion())
                                .setVoltage(motion.getVoltage())
                                .build())
                        .build();

            case SWITCH_SENSOR_EVENT:
                SwitchSensorEvent sw = (SwitchSensorEvent) event;
                return SensorEventAvro.newBuilder()
                        .setId(sw.getId())
                        .setHubId(sw.getHubId())
                        .setTimestamp(sw.getTimestamp())
                        .setPayload(SwitchSensorAvro.newBuilder()
                                .setState(sw.getState())
                                .build())
                        .build();

            case TEMPERATURE_SENSOR_EVENT:
                TemperatureSensorEvent temp = (TemperatureSensorEvent) event;
                return SensorEventAvro.newBuilder()
                        .setId(temp.getId())
                        .setHubId(temp.getHubId())
                        .setTimestamp(temp.getTimestamp())
                        .setPayload(TemperatureSensorAvro.newBuilder()
                                .setId(temp.getId())
                                .setHubId(temp.getHubId())
                                .setTimestamp(temp.getTimestamp())
                                .setTemperatureC(temp.getTemperatureC())
                                .setTemperatureF(temp.getTemperatureF())
                                .build())
                        .build();

            default:
                throw new IllegalArgumentException("Unknown SensorEventType: " + event.getType());
        }
    }
}
