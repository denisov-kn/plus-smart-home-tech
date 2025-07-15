package ru.practicum;


import ru.practicum.model.hub.event.device.DeviceType;
import ru.practicum.model.hub.event.scenario.*;
import ru.practicum.model.hubroute.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;


public class ProtoMappingUtils {

    public static DeviceTypeAvro mapAvroDeviceType(DeviceType type) {
        return switch (type) {
            case DeviceType.MOTION_SENSOR -> DeviceTypeAvro.MOTION_SENSOR;
            case DeviceType.TEMPERATURE_SENSOR -> DeviceTypeAvro.TEMPERATURE_SENSOR;
            case DeviceType.LIGHT_SENSOR -> DeviceTypeAvro.LIGHT_SENSOR;
            case DeviceType.CLIMATE_SENSOR -> DeviceTypeAvro.CLIMATE_SENSOR;
            case DeviceType.SWITCH_SENSOR -> DeviceTypeAvro.SWITCH_SENSOR;
        };
    }

    public static ConditionTypeAvro mapAvroConditionType(ConditionType type) {
        return switch (type) {
            case ConditionType.MOTION -> ConditionTypeAvro.MOTION;
            case ConditionType.LUMINOSITY -> ConditionTypeAvro.LUMINOSITY;
            case ConditionType.SWITCH -> ConditionTypeAvro.SWITCH;
            case ConditionType.TEMPERATURE -> ConditionTypeAvro.TEMPERATURE;
            case ConditionType.CO2LEVEL -> ConditionTypeAvro.CO2LEVEL;
            case ConditionType.HUMIDITY -> ConditionTypeAvro.HUMIDITY;
        };
    }

    public static ConditionOperationAvro mapAvroOperationType(OperationType op) {
        return switch (op) {
            case OperationType.EQUALS -> ConditionOperationAvro.EQUALS;
            case OperationType.GREATER_THAN -> ConditionOperationAvro.GREATER_THAN;
            case OperationType.LOWER_THAN -> ConditionOperationAvro.LOWER_THAN;
        };
    }

    public static ActionTypeAvro mapAvroActionType(ActionType type) {
        return switch (type) {
            case ActionType.ACTIVATE -> ActionTypeAvro.ACTIVATE;
            case ActionType.DEACTIVATE -> ActionTypeAvro.DEACTIVATE;
            case ActionType.INVERSE -> ActionTypeAvro.INVERSE;
            case ActionType.SET_VALUE -> ActionTypeAvro.SET_VALUE;
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
            case DeviceTypeProto.MOTION_SENSOR -> DeviceType.MOTION_SENSOR;
            case DeviceTypeProto.TEMPERATURE_SENSOR -> DeviceType.TEMPERATURE_SENSOR;
            case DeviceTypeProto.LIGHT_SENSOR -> DeviceType.LIGHT_SENSOR;
            case DeviceTypeProto.CLIMATE_SENSOR -> DeviceType.CLIMATE_SENSOR;
            case DeviceTypeProto.SWITCH_SENSOR -> DeviceType.SWITCH_SENSOR;
            case DeviceTypeProto.UNRECOGNIZED -> throw new IllegalArgumentException("Unknown device type: " + proto);
        };
    }

    public static ConditionType mapConditionType(ConditionTypeProto proto) {
        return switch (proto) {
            case ConditionTypeProto.MOTION -> ConditionType.MOTION;
            case ConditionTypeProto.LUMINOSITY -> ConditionType.LUMINOSITY;
            case ConditionTypeProto.SWITCH -> ConditionType.SWITCH;
            case ConditionTypeProto.TEMPERATURE -> ConditionType.TEMPERATURE;
            case ConditionTypeProto.CO2LEVEL -> ConditionType.CO2LEVEL;
            case ConditionTypeProto.HUMIDITY -> ConditionType.HUMIDITY;
            case ConditionTypeProto.UNRECOGNIZED -> throw new IllegalArgumentException("Unknown condition type: " + proto);
        };
    }

    public static OperationType mapOperationType(ConditionOperationProto proto) {
        return switch (proto) {
            case ConditionOperationProto.EQUALS -> OperationType.EQUALS;
            case ConditionOperationProto.GREATER_THAN -> OperationType.GREATER_THAN;
            case ConditionOperationProto.LOWER_THAN -> OperationType.LOWER_THAN;
            case ConditionOperationProto.UNRECOGNIZED -> throw new IllegalArgumentException("Unknown operation: " + proto);
        };
    }

    public static ActionType mapActionType(ActionTypeProto proto) {
        return switch (proto) {
            case ActionTypeProto.ACTIVATE -> ActionType.ACTIVATE;
            case ActionTypeProto.DEACTIVATE -> ActionType.DEACTIVATE;
            case ActionTypeProto.INVERSE -> ActionType.INVERSE;
            case ActionTypeProto.SET_VALUE -> ActionType.SET_VALUE;
            case ActionTypeProto.UNRECOGNIZED -> throw new IllegalArgumentException("Unknown action type: " + proto);
        };
    }

    public static ActionTypeProto mapActionType(ActionType actionType) {
        return switch (actionType) {
            case ACTIVATE -> ActionTypeProto.ACTIVATE;
            case DEACTIVATE -> ActionTypeProto.DEACTIVATE;
            case INVERSE -> ActionTypeProto.INVERSE;
            case SET_VALUE -> ActionTypeProto.SET_VALUE;
            default -> throw new IllegalArgumentException("Unknown action type: " + actionType);
        };
    }

    public static ScenarioCondition mapScenarioCondition(ScenarioConditionProto proto) {

        Integer intValue = null;
        Boolean booleanValue = null;

        switch (proto.getValueCase()) {
            case ScenarioConditionProto.ValueCase.BOOL_VALUE:
                booleanValue = proto.getBoolValue();
                break;
            case ScenarioConditionProto.ValueCase.INT_VALUE:
                intValue = proto.getIntValue();
                break;
            case ScenarioConditionProto.ValueCase.VALUE_NOT_SET:
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

    public static DeviceActionProto mapDeviceAction(DeviceAction deviceAction) {
        return DeviceActionProto.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setType(mapActionType(deviceAction.getType()))  // предполагается обратный маппер
                .setValue(deviceAction.getValue())
                .build();
    }

    public static DeviceActionRequest mapDeviceActionRequest(DeviceActionRequestProto proto) {
        DeviceActionRequest deviceActionRequest = new DeviceActionRequest();
        deviceActionRequest.setHubId(proto.getHubId());
        deviceActionRequest.setScenarioName(proto.getScenarioName());
        deviceActionRequest.setAction(ProtoMappingUtils.mapDeviceAction(proto.getAction()));
        deviceActionRequest.setTimestamp(Instant.ofEpochSecond(proto.getTimestamp().getSeconds(),
                        proto.getTimestamp().getNanos()));
        return deviceActionRequest;
    }

    public static DeviceActionRequestProto mapDeviceActionRequest(DeviceActionRequest deviceActionRequest) {
        return DeviceActionRequestProto.newBuilder()
                .setHubId(deviceActionRequest.getHubId())
                .setScenarioName(deviceActionRequest.getScenarioName())
                .setAction(ProtoMappingUtils.mapDeviceAction(deviceActionRequest.getAction()))
                .setTimestamp(com.google.protobuf.Timestamp.newBuilder()
                        .setSeconds(deviceActionRequest.getTimestamp().getEpochSecond())
                        .setNanos(deviceActionRequest.getTimestamp().getNano())
                        .build())
                .build();
    }



}
