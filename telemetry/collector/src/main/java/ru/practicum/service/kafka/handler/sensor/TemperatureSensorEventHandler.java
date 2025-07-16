package ru.practicum.service.kafka.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

@Component
public class TemperatureSensorEventHandler implements SensorEventHandler {

    @Override
    public boolean supports(SensorEventProto proto) {
        return proto.hasTemperatureSensorEvent();
    }

    @Override
    public SensorEventAvro handle(SensorEventProto proto) {
        return SensorEventAvro.newBuilder()
                .setId(proto.getId())
                .setHubId(proto.getHubId())
                .setTimestamp(MappingUtils.toInstant(proto.getTimestamp()))
                .setPayload(TemperatureSensorAvro.newBuilder()
                        .setTemperatureC(proto.getTemperatureSensorEvent().getTemperatureC())
                        .setTemperatureF(proto.getTemperatureSensorEvent().getTemperatureF())
                        .build())
                .build();
    }
}
