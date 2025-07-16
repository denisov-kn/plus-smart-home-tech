package ru.practicum.utils;

import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {
    public static DeviceTypeAvro map(DeviceTypeProto proto) {
        return switch (proto) {
            case MOTION_SENSOR -> DeviceTypeAvro.MOTION_SENSOR;
            case TEMPERATURE_SENSOR -> DeviceTypeAvro.TEMPERATURE_SENSOR;
            case LIGHT_SENSOR -> DeviceTypeAvro.LIGHT_SENSOR;
            case CLIMATE_SENSOR -> DeviceTypeAvro.CLIMATE_SENSOR;
            case SWITCH_SENSOR -> DeviceTypeAvro.SWITCH_SENSOR;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unknown DeviceTypeProto: " + proto);
        };
    }

    public static ConditionTypeAvro map(ConditionTypeProto proto) {
        return switch (proto) {
            case MOTION -> ConditionTypeAvro.MOTION;
            case LUMINOSITY -> ConditionTypeAvro.LUMINOSITY;
            case SWITCH -> ConditionTypeAvro.SWITCH;
            case TEMPERATURE -> ConditionTypeAvro.TEMPERATURE;
            case CO2LEVEL -> ConditionTypeAvro.CO2LEVEL;
            case HUMIDITY -> ConditionTypeAvro.HUMIDITY;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unknown ConditionTypeProto: " + proto);
        };
    }

    public static ConditionOperationAvro map(ConditionOperationProto proto) {
        return switch (proto) {
            case EQUALS -> ConditionOperationAvro.EQUALS;
            case GREATER_THAN -> ConditionOperationAvro.GREATER_THAN;
            case LOWER_THAN -> ConditionOperationAvro.LOWER_THAN;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unknown ConditionOperationProto: " + proto);
        };
    }

    public static ActionTypeAvro map(ActionTypeProto proto) {
        return switch (proto) {
            case ACTIVATE -> ActionTypeAvro.ACTIVATE;
            case DEACTIVATE -> ActionTypeAvro.DEACTIVATE;
            case INVERSE -> ActionTypeAvro.INVERSE;
            case SET_VALUE -> ActionTypeAvro.SET_VALUE;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unknown ActionTypeProto: " + proto);
        };
    }

    public static Instant toInstant(com.google.protobuf.Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }

    public static ScenarioConditionAvro map(ScenarioConditionProto proto) {
        ScenarioConditionAvro.Builder builder = ScenarioConditionAvro.newBuilder()
                .setSensorId(proto.getSensorId())
                .setType(map(proto.getType()))
                .setOperation(map(proto.getOperation()));

        switch (proto.getValueCase()) {
            case BOOL_VALUE -> builder.setValue(proto.getBoolValue());
            case INT_VALUE -> builder.setValue(proto.getIntValue());
            case VALUE_NOT_SET -> builder.setValue(null);
        }

        return builder.build();
    }

    public static DeviceActionAvro map(DeviceActionProto proto) {
        DeviceActionAvro.Builder builder = DeviceActionAvro.newBuilder()
                .setSensorId(proto.getSensorId())
                .setType(map(proto.getType()));

        if (proto.hasValue()) {
            builder.setValue(proto.getValue());
        }

        return builder.build();
    }


    public static List<ScenarioConditionAvro> mapConditions(List<ScenarioConditionProto> conditions) {
        return conditions.stream().map(MappingUtils::map).collect(Collectors.toList());
    }

    public static List<DeviceActionAvro> mapActions(List<DeviceActionProto> actions) {
        return actions.stream().map(MappingUtils::map).collect(Collectors.toList());
    }
}
