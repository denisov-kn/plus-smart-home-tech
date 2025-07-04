package ru.practicum.service.kafka;

import ru.practicum.model.hub.event.HubEvent;
import ru.practicum.model.sensor.SensorEvent;

public interface KafkaService {
    void kafkaSensorEvent(SensorEvent sensorEvent);
    void kafkaHubEvent(HubEvent hubEvent);

}
