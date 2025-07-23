package ru.practicum.service.kafka.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;



@Component
public class ClimateSensorEventHandler implements SensorEventHandler {

    @Override
    public boolean supports(SensorEventProto proto) {
        return proto.hasClimateSensorEvent();
    }

    @Override
    public SensorEventAvro handle(SensorEventProto proto) {
        return SensorEventAvro.newBuilder()
                .setId(proto.getId())
                .setHubId(proto.getHubId())
                .setTimestamp(MappingUtils.toInstant(proto.getTimestamp()))
                .setPayload(ClimateSensorAvro.newBuilder()
                        .setTemperatureC(proto.getClimateSensorEvent().getTemperatureC())
                        .setHumidity(proto.getClimateSensorEvent().getHumidity())
                        .setCo2Level(proto.getClimateSensorEvent().getCo2Level())
                        .build())
                .build();
    }

}
