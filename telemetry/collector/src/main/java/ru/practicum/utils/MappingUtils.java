package ru.practicum.utils;


import ru.practicum.model.hub.event.device.DeviceType;
import ru.practicum.model.hub.event.scenario.*;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;


public class MappingUtils {

    public static DeviceTypeAvro mapAvroDeviceType(DeviceType type) {
        return switch (type) {
            case MOTION_SENSOR -> DeviceTypeAvro.MOTION_SENSOR;
            case TEMPERATURE_SENSOR -> DeviceTypeAvro.TEMPERATURE_SENSOR;
            case LIGHT_SENSOR -> DeviceTypeAvro.LIGHT_SENSOR;
            case CLIMATE_SENSOR -> DeviceTypeAvro.CLIMATE_SENSOR;
            case SWITCH_SENSOR -> DeviceTypeAvro.SWITCH_SENSOR;
        };
    }

    public static ConditionTypeAvro mapAvroConditionType(ConditionType type) {
        return switch (type) {
            case MOTION -> ConditionTypeAvro.MOTION;
            case LUMINOSITY -> ConditionTypeAvro.LUMINOSITY;
            case SWITCH -> ConditionTypeAvro.SWITCH;
            case TEMPERATURE -> ConditionTypeAvro.TEMPERATURE;
            case CO2LEVEL -> ConditionTypeAvro.CO2LEVEL;
            case HUMIDITY -> ConditionTypeAvro.HUMIDITY;
        };
    }

    public static ConditionOperationAvro mapAvroOperationType(OperationType op) {
        return switch (op) {
            case EQUALS -> ConditionOperationAvro.EQUALS;
            case GREATER_THAN -> ConditionOperationAvro.GREATER_THAN;
            case LOWER_THAN -> ConditionOperationAvro.LOWER_THAN;
        };
    }

    public static ActionTypeAvro mapAvroActionType(ActionType type) {
        return switch (type) {
            case ACTIVATE -> ActionTypeAvro.ACTIVATE;
            case DEACTIVATE -> ActionTypeAvro.DEACTIVATE;
            case INVERSE -> ActionTypeAvro.INVERSE;
            case SET_VALUE -> ActionTypeAvro.SET_VALUE;
        };
    }

    public static ScenarioConditionAvro mapAvroScenarioCondition(ScenarioCondition condition) {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(condition.getSensorId())
                .setType(mapAvroConditionType(condition.getType()))
                .setOperation(mapAvroOperationType(condition.getOperation()))
                .setValue(condition.getValue())
                .build();
    }

    public static DeviceActionAvro mapAvroDeviceAction(DeviceAction action) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(action.getSensorId())
                .setType(mapAvroActionType(action.getType()))
                .setValue(action.getValue())
                .build();
    }

    public static DeviceType mapDeviceType(DeviceTypeProto proto) {
        return switch (proto) {
            case MOTION_SENSOR -> DeviceType.MOTION_SENSOR;
            case TEMPERATURE_SENSOR -> DeviceType.TEMPERATURE_SENSOR;
            case LIGHT_SENSOR -> DeviceType.LIGHT_SENSOR;
            case CLIMATE_SENSOR -> DeviceType.CLIMATE_SENSOR;
            case SWITCH_SENSOR -> DeviceType.SWITCH_SENSOR;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unknown device type: " + proto);
        };
    }

    public static ConditionType mapConditionType(ConditionTypeProto proto) {
        return switch (proto) {
            case MOTION -> ConditionType.MOTION;
            case LUMINOSITY -> ConditionType.LUMINOSITY;
            case SWITCH -> ConditionType.SWITCH;
            case TEMPERATURE -> ConditionType.TEMPERATURE;
            case CO2LEVEL -> ConditionType.CO2LEVEL;
            case HUMIDITY -> ConditionType.HUMIDITY;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unknown condition type: " + proto);
        };
    }

    public static OperationType mapOperationType(ConditionOperationProto proto) {
        return switch (proto) {
            case EQUALS -> OperationType.EQUALS;
            case GREATER_THAN -> OperationType.GREATER_THAN;
            case LOWER_THAN -> OperationType.LOWER_THAN;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unknown operation: " + proto);
        };
    }

    public static ActionType mapActionType(ActionTypeProto proto) {
        return switch (proto) {
            case ACTIVATE -> ActionType.ACTIVATE;
            case DEACTIVATE -> ActionType.DEACTIVATE;
            case INVERSE -> ActionType.INVERSE;
            case SET_VALUE -> ActionType.SET_VALUE;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unknown action type: " + proto);
        };
    }

    public static ScenarioCondition mapScenarioCondition(ScenarioConditionProto proto) {

        Integer intValue = null;
        Boolean booleanValue = null;

        switch (proto.getValueCase()) {
            case BOOL_VALUE:
                booleanValue = proto.getBoolValue();
                break;
            case INT_VALUE:
                intValue = proto.getIntValue();
                break;
            case VALUE_NOT_SET:
            default:
        }

        return new ScenarioCondition(
                proto.getSensorId(),
                mapConditionType(proto.getType()),
                mapOperationType(proto.getOperation()),
                intValue,
                booleanValue
        );
    }

    public static DeviceAction mapDeviceAction(DeviceActionProto proto) {
        return new DeviceAction(
                proto.getSensorId(),
                mapActionType(proto.getType()),
                proto.getValue()
        );
    }

}
