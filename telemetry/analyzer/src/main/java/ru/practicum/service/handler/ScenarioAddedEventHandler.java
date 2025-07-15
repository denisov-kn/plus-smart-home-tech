package ru.practicum.service.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.AvroMappingUtils;
import ru.practicum.model.entity.*;
import ru.practicum.repository.*;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScenarioAddedEventHandler implements HubEventHandler<ScenarioAddedEventAvro> {

    private final ScenarioRepository scenarioRepository;
    private final ActionRepository actionRepository;
    private final ConditionRepository conditionRepository;
    private final ScenarioConditionLinkRepository scenarioConditionLinkRepository;
    private final ScenarioActionLinkRepository scenarioActionLinkRepository;

    @Transactional
    @Override
    public void handle(ScenarioAddedEventAvro payload, String hubId, Instant timestamp) {
        ScenarioEntity scenarioEntity = new ScenarioEntity();
        scenarioEntity.setHubId(hubId);
        scenarioEntity.setName(payload.getName());

        List<ConditionEntity> conditionEntityList = payload.getConditions().stream()
                .map(avroCondition -> ConditionEntity.builder()
                        .type(AvroMappingUtils.mapConditionType(avroCondition.getType()))
                        .value(switch (avroCondition.getValue()) {
                            case null -> null;
                            case Integer intValue -> intValue;
                            case Boolean boolValue -> boolValue ? 1 : 0;
                            default -> throw new IllegalArgumentException(
                                    "Unsupported value type: " + avroCondition.getValue().getClass());
                        })
                        .operation(AvroMappingUtils.mapOperationType(avroCondition.getOperation()))
                        .build())
                .toList();

        List<ScenarioConditionLink> conditionLinks = payload.getConditions().stream()
                .map(avroCondition -> {
                    return ScenarioConditionLink.builder()
                            .scenario(scenarioEntity)
                            .sensor(SensorEntity.builder().id(avroCondition.getSensorId()).hubId(hubId).build())
                            .condition(conditionEntityList.get(payload.getConditions().indexOf(avroCondition)))
                            .build();
                })
                .toList();

        List<ActionEntity> actionEntityList = payload.getActions().stream()
                .map(avroAction -> ActionEntity.builder()
                        .type(AvroMappingUtils.mapActionType(avroAction.getType()))
                        .value(avroAction.getValue())
                                .build())
                .toList();

        List<ScenarioActionLink> actionLinks = payload.getActions().stream()
                .map(avroAction -> {
                    ActionEntity actionEntity = actionEntityList.get(payload.getActions().indexOf(avroAction));
                    return ScenarioActionLink.builder()
                            .scenario(scenarioEntity)
                            .sensor(SensorEntity.builder().id(avroAction.getSensorId()).hubId(hubId).build()) // либо загрузить
                            .action(actionEntity)
                            .build();
                })
                .toList();

        scenarioRepository.save(scenarioEntity);
        conditionRepository.saveAll(conditionEntityList);
        actionRepository.saveAll(actionEntityList);
        scenarioConditionLinkRepository.saveAll(conditionLinks);
        scenarioActionLinkRepository.saveAll(actionLinks);

    }

    @Override
    public Class<ScenarioAddedEventAvro> payloadType() {
        return ScenarioAddedEventAvro.class;
    }
}
