package ru.practicum.service.kafka.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;


@Component
public class LightSensorEventHandler implements SensorEventHandler {

    @Override
    public boolean supports(SensorEventProto proto) {
        return proto.hasLightSensorEvent();
    }

    @Override
    public SensorEventAvro handle(SensorEventProto proto) {
        return SensorEventAvro.newBuilder()
                .setId(proto.getId())
                .setHubId(proto.getHubId())
                .setTimestamp(MappingUtils.toInstant(proto.getTimestamp()))
                .setPayload(LightSensorAvro.newBuilder()
                        .setLinkQuality(proto.getLightSensorEvent().getLinkQuality())
                        .setLuminosity(proto.getLightSensorEvent().getLuminosity())
                        .build())
                .build();
    }


}