package ru.practicum.utils;

import ru.practicum.entity.ActionEntity;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequestProto;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;
import com.google.protobuf.Timestamp;

import java.time.Instant;

public class MappingUtils {

    public static DeviceActionProto mapDeviceAction(String sensorId, ActionEntity actionEntity) {
        return DeviceActionProto.newBuilder()
                .setType(mapActionType(actionEntity.getType()))
                .setValue(actionEntity.getValue())
                .setSensorId(sensorId)
                .build();

    }


    public static ActionTypeProto mapActionType(ActionTypeAvro avro) {
        return switch (avro) {
            case ACTIVATE -> ActionTypeProto.ACTIVATE;
            case DEACTIVATE -> ActionTypeProto.DEACTIVATE;
            case INVERSE -> ActionTypeProto.INVERSE;
            case SET_VALUE -> ActionTypeProto.SET_VALUE;
        };
    }

    public static Timestamp mapFromInstant(Instant instant) {
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
