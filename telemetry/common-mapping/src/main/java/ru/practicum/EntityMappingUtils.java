package ru.practicum;

import ru.practicum.model.entity.ActionEntity;
import ru.practicum.model.hub.event.scenario.DeviceAction;

public class EntityMappingUtils {
    public static DeviceAction mapDeviceAction(String sensorId, ActionEntity actionEntity) {
        DeviceAction deviceAction = new DeviceAction();
        deviceAction.setType(actionEntity.getType());
        deviceAction.setValue(actionEntity.getValue());
        deviceAction.setSensorId(sensorId);
        return deviceAction;
    }
}
