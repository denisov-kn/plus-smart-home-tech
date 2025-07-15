package ru.practicum;

import ru.practicum.model.hub.event.scenario.ActionType;
import ru.practicum.model.hub.event.scenario.ConditionType;
import ru.practicum.model.hub.event.scenario.OperationType;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;

public class AvroMappingUtils {

    public static ConditionType mapConditionType(ConditionTypeAvro conditionTypeAvro) {
       return switch (conditionTypeAvro) {
            case ConditionTypeAvro.MOTION -> ConditionType.MOTION;
            case ConditionTypeAvro.LUMINOSITY -> ConditionType.LUMINOSITY;
            case ConditionTypeAvro.SWITCH -> ConditionType.SWITCH;
            case ConditionTypeAvro.TEMPERATURE -> ConditionType.TEMPERATURE;
            case ConditionTypeAvro.CO2LEVEL -> ConditionType.CO2LEVEL;
            case ConditionTypeAvro.HUMIDITY -> ConditionType.HUMIDITY;
        };
    }

    public static OperationType mapOperationType(ConditionOperationAvro conditionOperationAvro) {
        return switch (conditionOperationAvro) {
            case ConditionOperationAvro.EQUALS -> OperationType.EQUALS;
            case ConditionOperationAvro.GREATER_THAN -> OperationType.GREATER_THAN;
            case ConditionOperationAvro.LOWER_THAN -> OperationType.LOWER_THAN;
        };
    }

    public static ActionType mapActionType(ActionTypeAvro actionTypeAvro) {
        return switch (actionTypeAvro) {
            case ActionTypeAvro.INVERSE -> ActionType.INVERSE;
            case ActionTypeAvro.ACTIVATE -> ActionType.ACTIVATE;
            case ActionTypeAvro.DEACTIVATE -> ActionType.DEACTIVATE;
            case ActionTypeAvro.SET_VALUE -> ActionType.SET_VALUE;
        };
    }
}
