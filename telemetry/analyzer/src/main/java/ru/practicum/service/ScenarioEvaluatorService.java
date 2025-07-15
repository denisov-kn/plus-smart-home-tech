package ru.practicum.service;

import ru.practicum.model.hubroute.DeviceActionRequest;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.List;

public interface ScenarioEvaluatorService {
    List<DeviceActionRequest> evaluate(SensorsSnapshotAvro sensorsSnapshotAvro);
}
