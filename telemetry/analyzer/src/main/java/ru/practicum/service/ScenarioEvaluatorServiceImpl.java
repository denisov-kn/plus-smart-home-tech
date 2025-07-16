package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.EntityMappingUtils;
import ru.practicum.entity.*;
import ru.practicum.model.hub.event.scenario.DeviceAction;
import ru.practicum.model.hubroute.DeviceActionRequest;
import ru.practicum.repository.ScenarioActionLinkRepository;
import ru.practicum.repository.ScenarioConditionLinkRepository;
import ru.practicum.repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScenarioEvaluatorServiceImpl implements ScenarioEvaluatorService {

    private final ScenarioRepository scenarioRepository;
    private final ScenarioConditionLinkRepository scenarioConditionLinkRepository;
    private final ScenarioActionLinkRepository scenarioActionLinkRepository;

    @Override
    public List<DeviceActionRequest> evaluate(SensorsSnapshotAvro sensorsSnapshotAvro) {

        log.info("Analyzer:ScenarioEvaluatorService - sensorsSnapshotAvro: {}", sensorsSnapshotAvro);


        List<ScenarioEntity> scenarioEntities = scenarioRepository.findByHubId(sensorsSnapshotAvro.getHubId());
        List<DeviceActionRequest> results = new ArrayList<>();


        for (ScenarioEntity scenarioEntity : scenarioEntities) {
            List<ScenarioConditionLink> conditionLinks = scenarioConditionLinkRepository.findByScenarioId(scenarioEntity.getId());

            boolean allConditionMatch = conditionLinks.stream()
                    .allMatch(cond -> conditionMatch(cond, sensorsSnapshotAvro));

            if (allConditionMatch) {
                List<ScenarioActionLink> actions = scenarioActionLinkRepository.findAllByScenarioId((scenarioEntity.getId()));

                for (ScenarioActionLink actionLink : actions) {
                    ActionEntity action = actionLink.getAction();
                    SensorEntity sensor = actionLink.getSensor();
                    DeviceActionRequest deviceActionRequest = new DeviceActionRequest();
                    deviceActionRequest.setHubId(sensorsSnapshotAvro.getHubId());
                    deviceActionRequest.setAction(EntityMappingUtils.mapDeviceAction(sensor.getId(), action));
                    deviceActionRequest.setScenarioName(scenarioEntity.getName());
                    deviceActionRequest.setTimestamp(Instant.now());

                    results.add(deviceActionRequest);
                }
            }

        }
        return results;
    }

    private boolean conditionMatch(ScenarioConditionLink cond, SensorsSnapshotAvro sensorsSnapshotAvro) {


        String sensorId = cond.getSensor().getId();
        ConditionEntity condition = cond.getCondition();
        log.debug("ConditionMatch");
        log.debug("SensorID: {}", sensorId);
        log.debug("Condition: {}", condition);
        log.debug("ScenarioConditionLink: {}", cond);



        SensorStateAvro sensorStateAvro = sensorsSnapshotAvro.getSensorsState().get(sensorId);

        if (sensorStateAvro == null) {
            log.warn("Sensor state is missing in snapshot for sensorId: {}", sensorId);
            return false;
        }

        Object data = sensorStateAvro.getData();

        return switch (condition.getType()) {
            case TEMPERATURE -> {
                if (data instanceof TemperatureSensorAvro temp) {
                    yield matchCondition(temp.getTemperatureC(), condition);
                } else if (data instanceof ClimateSensorAvro climate) {
                    yield matchCondition(climate.getTemperatureC(), condition);
                } else {
                    yield false;
                }
            }
            case HUMIDITY -> {
                if (data instanceof ClimateSensorAvro climate) {
                    yield matchCondition(climate.getHumidity(), condition);
                } else {
                    yield false;
                }
            }
            case CO2LEVEL -> {
                if (data instanceof ClimateSensorAvro climate) {
                    yield matchCondition(climate.getCo2Level(), condition);
                } else {
                    yield false;
                }
            }
            case LUMINOSITY -> {
                if (data instanceof LightSensorAvro light) {
                    yield matchCondition(light.getLuminosity(), condition);
                } else {
                    yield false;
                }
            }
            case MOTION -> {
                if (data instanceof MotionSensorAvro motion) {
                    Integer value = motion.getMotion() ? 1 : 0;
                    yield matchCondition(value, condition);
                } else {
                    yield false;
                }
            }
            case SWITCH -> {
                if (data instanceof SwitchSensorAvro sw) {
                    Integer value = sw.getState() ? 1 : 0;
                    yield matchCondition(value, condition);
                } else {
                    yield false;
                }
            }

        };
    }

    private boolean matchCondition(Integer value, ConditionEntity condition) {
        return switch (condition.getOperation()) {
            case EQUALS -> value == condition.getValue();
            case GREATER_THAN -> value > condition.getValue();
            case LOWER_THAN -> value < condition.getValue();
        };
    }


}
