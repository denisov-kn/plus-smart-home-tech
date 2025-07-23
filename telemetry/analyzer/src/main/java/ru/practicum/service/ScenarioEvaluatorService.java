package ru.practicum.service;

import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequestProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.List;

public interface ScenarioEvaluatorService {
    List<DeviceActionRequestProto> evaluate(SensorsSnapshotAvro sensorsSnapshotAvro);
}
