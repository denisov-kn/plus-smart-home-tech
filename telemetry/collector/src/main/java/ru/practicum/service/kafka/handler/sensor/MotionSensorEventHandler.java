package ru.practicum.service.kafka.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.utils.MappingUtils;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Component
public class MotionSensorEventHandler implements SensorEventHandler{

    @Override
    public boolean supports(SensorEventProto proto) {
        return proto.hasMotionSensorEvent();
    }

    @Override
    public SensorEventAvro handle(SensorEventProto proto) {
        return SensorEventAvro.newBuilder()
                .setId(proto.getId())
                .setHubId(proto.getHubId())
                .setTimestamp(MappingUtils.toInstant(proto.getTimestamp()))
                .setPayload(MotionSensorAvro.newBuilder()
                        .setLinkQuality(proto.getMotionSensorEvent().getLinkQuality())
                        .setMotion(proto.getMotionSensorEvent().getMotion())
                        .setVoltage(proto.getMotionSensorEvent().getVoltage())
                        .build())
                .build();
    }


}
