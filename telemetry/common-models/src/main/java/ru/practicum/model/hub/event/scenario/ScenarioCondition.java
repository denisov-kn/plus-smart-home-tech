package ru.practicum.model.hub.event.scenario;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ScenarioCondition {
    String sensorId;
    ConditionType type;
    OperationType operation;
    Integer intValue;
    Boolean booleanValue;

    public Object getValue() {
        if (booleanValue != null) return booleanValue;
        else return intValue;
    }
}
