package ru.practicum.service.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.AvroMappingUtils;
import ru.practicum.model.entity.*;
import ru.practicum.repository.*;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;

import java.time.Instant;
import java.util.List;

@Slf4j
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

        log.info("Save ScenarioAddedEventAvro: {}", payload);
        log.info("hubId: {}", hubId);
        log.info("timestamp: {}", timestamp);


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

                    String sensorId = avroCondition.getSensorId();
                    SensorEntity sensor = SensorEntity.builder()
                            .id(sensorId)
                            .hubId(hubId)
                            .build();

                    ConditionEntity condition = conditionEntityList.get(payload.getConditions().indexOf(avroCondition));

                    return ScenarioConditionLink.builder()
                            .id(new ScenarioConditionLink.ScenarioConditionId(
                                    scenarioEntity.getId(),
                                    sensor.getId(),
                                    condition.getId()
                            ))
                            .scenario(scenarioEntity)
                            .sensor(sensor)
                            .condition(condition)
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
                    String sensorId = avroAction.getSensorId();
                    SensorEntity sensor = SensorEntity.builder()
                            .id(sensorId)
                            .hubId(hubId)
                            .build();
                    ActionEntity actionEntity = actionEntityList.get(payload.getActions().indexOf(avroAction));
                    return ScenarioActionLink.builder()
                            .id(new ScenarioActionLink.ScenarioActionId(
                                    scenarioEntity.getId(),
                                    sensor.getId(),
                                    actionEntity.getId()
                            ))
                            .scenario(scenarioEntity)
                            .sensor(sensor)
                            .action(actionEntity)
                            .build();
                })
                .toList();

        log.info("Save entity: {}", scenarioEntity);
        scenarioRepository.save(scenarioEntity);

        log.info("Save conditionList: {}", conditionEntityList);
        conditionRepository.saveAll(conditionEntityList);

        log.info("Save action: {}", actionEntityList);
        actionRepository.saveAll(actionEntityList);

        log.info("Save condition links: {}", conditionLinks);
        scenarioConditionLinkRepository.saveAll(conditionLinks);

        log.info("Save action links: {}", actionLinks);
        scenarioActionLinkRepository.saveAll(actionLinks);

    }

    @Override
    public Class<ScenarioAddedEventAvro> payloadType() {
        return ScenarioAddedEventAvro.class;
    }
}
