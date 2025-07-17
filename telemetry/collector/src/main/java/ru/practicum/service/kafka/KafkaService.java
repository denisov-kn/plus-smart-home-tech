package ru.practicum.service.kafka;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;


public interface KafkaService {
    void kafkaSensorEvent(SensorEventProto sensorEvent);
    void kafkaHubEvent(HubEventProto hubEvent);

}
