package ru.practicum.service.kafka.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;


@Component
public class SwitchSensorEventHandler implements SensorEventHandler {

    @Override
    public boolean supports(SensorEventProto proto) {
        return proto.hasSwitchSensorEvent();
    }

    @Override
    public SensorEventAvro handle(SensorEventProto proto) {
        return SensorEventAvro.newBuilder()
                .setId(proto.getId())
                .setHubId(proto.getHubId())
                .setTimestamp(MappingUtils.toInstant(proto.getTimestamp()))
                .setPayload(SwitchSensorAvro.newBuilder()
                        .setState(proto.getSwitchSensorEvent().getState())
                        .build())
                .build();
    }

}

