package ru.practicum;

import ru.practicum.dto.hub.event.*;
import ru.practicum.dto.hub.event.device.*;
import ru.practicum.dto.hub.event.scenario.*;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.HubEventProtocol.*;

import java.time.Instant;
import java.util.stream.Collectors;

public class HubEventMapper {

    public static HubEventAvro toAvro(HubEvent event) {
        switch (event.getType()) {
            case DEVICE_ADDED -> {
                DeviceAddedEvent e = (DeviceAddedEvent) event;
                DeviceAddedEventAvro deviceAdded = DeviceAddedEventAvro.newBuilder()
                        .setId(e.getId())
                        .setType(mapDeviceType(e.getDeviceType()))
                        .build();
                return HubEventAvro.newBuilder()
                        .setHubId(e.getHubId())
                        .setTimestamp(e.getTimestamp())
                        .setPayload(deviceAdded)
                        .build();
            }
            case DEVICE_REMOVED -> {
                DeviceRemovedEvent e = (DeviceRemovedEvent) event;
                DeviceRemovedEventAvro deviceRemoved = DeviceRemovedEventAvro.newBuilder()
                        .setId(e.getId())
                        .build();
                return HubEventAvro.newBuilder()
                        .setHubId(e.getHubId())
                        .setTimestamp(e.getTimestamp())
                        .setPayload(deviceRemoved)
                        .build();
            }
            case SCENARIO_ADDED -> {
                ScenarioAddedEvent e = (ScenarioAddedEvent) event;
                var conditionsAvro = e.getConditions().stream()
                        .map(HubEventMapper::toAvro)
                        .collect(Collectors.toList());
                var actionsAvro = e.getActions().stream()
                        .map(HubEventMapper::toAvro)
                        .collect(Collectors.toList());

                ScenarioAddedEventAvro scenarioAdded = ScenarioAddedEventAvro.newBuilder()
                        .setName(e.getName())
                        .setConditions(conditionsAvro)
                        .setActions(actionsAvro)
                        .build();

                return HubEventAvro.newBuilder()
                        .setHubId(e.getHubId())
                        .setTimestamp(e.getTimestamp())
                        .setPayload(scenarioAdded)
                        .build();
            }
            case SCENARIO_REMOVED -> {
                ScenarioRemovedEvent e = (ScenarioRemovedEvent) event;
                ScenarioRemovedEventAvro scenarioRemoved = ScenarioRemovedEventAvro.newBuilder()
                        .setName(e.getName())
                        .build();

                return HubEventAvro.newBuilder()
                        .setHubId(e.getHubId())
                        .setTimestamp(e.getTimestamp())
                        .setPayload(scenarioRemoved)
                        .build();
            }
            default -> throw new IllegalArgumentException("Unknown HubEventType: " + event.getType());
        }
    }

    private static ScenarioConditionAvro toAvro(ScenarioCondition condition) {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(condition.getSensorId())
                .setType(mapConditionType(condition.getType()))
                .setOperation(mapOperationType(condition.getOperation()))
                .setValue(condition.getValue())
                .build();
    }

    private static DeviceActionAvro toAvro(DeviceAction action) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(action.getSensorId())
                .setType(mapActionType(action.getType()))
                .setValue(action.getValue())
                .build();
    }

    private static DeviceTypeAvro mapDeviceType(DeviceType type) {
        return switch (type) {
            case MOTION_SENSOR -> DeviceTypeAvro.MOTION_SENSOR;
            case TEMPERATURE_SENSOR -> DeviceTypeAvro.TEMPERATURE_SENSOR;
            case LIGHT_SENSOR -> DeviceTypeAvro.LIGHT_SENSOR;
            case CLIMATE_SENSOR -> DeviceTypeAvro.CLIMATE_SENSOR;
            case SWITCH_SENSOR -> DeviceTypeAvro.SWITCH_SENSOR;
        };
    }

    private static ConditionTypeAvro mapConditionType(ConditionType type) {
        return switch (type) {
            case MOTION -> ConditionTypeAvro.MOTION;
            case LUMINOSITY -> ConditionTypeAvro.LUMINOSITY;
            case SWITCH -> ConditionTypeAvro.SWITCH;
            case TEMPERATURE -> ConditionTypeAvro.TEMPERATURE;
            case CO2LEVEL -> ConditionTypeAvro.CO2LEVEL;
            case HUMIDITY -> ConditionTypeAvro.HUMIDITY;
        };
    }

    private static ConditionOperationAvro mapOperationType(OperationType operation) {
        return switch (operation) {
            case EQUALS -> ConditionOperationAvro.EQUALS;
            case GREATER_THAN -> ConditionOperationAvro.GREATER_THAN;
            case LOWER_THAN -> ConditionOperationAvro.LOWER_THAN;
        };
    }

    private static ActionTypeAvro mapActionType(ActionType actionType) {
        return switch (actionType) {
            case ACTIVATE -> ActionTypeAvro.ACTIVATE;
            case DEACTIVATE -> ActionTypeAvro.DEACTIVATE;
            case INVERSE -> ActionTypeAvro.INVERSE;
            case SET_VALUE -> ActionTypeAvro.SET_VALUE;
        };
    }


}
