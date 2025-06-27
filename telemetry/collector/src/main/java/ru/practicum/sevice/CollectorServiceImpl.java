package ru.practicum.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.practicum.model.hub.event.HubEvent;
import ru.practicum.model.sensor.SensorEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.sevice.handler.hub.HubEventHandlerRegistry;
import ru.practicum.sevice.handler.sensor.SensorEventRegistry;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;


@Service
@RequiredArgsConstructor
@Slf4j
public class CollectorServiceImpl implements CollectorService {

    private final KafkaProducer<String, SpecificRecordBase> producer;
    private final HubEventHandlerRegistry hubRegistry;
    private final SensorEventRegistry sensorRegistry;

    @Value("${kafka.topics.sensor-events}")
    private String sensorTopic;

    @Value("${kafka.topics.hub-events}")
    private String hubTopic;

    @Override
    public void collectSensorEvent(SensorEvent sensorEvent) {
        SensorEventAvro avro = sensorRegistry.handle(sensorEvent);
        log.info("Avro sensor event: {}", avro);
        producer.send(new ProducerRecord<>(sensorTopic, avro));
    }

    @Override
    public void collectHubEvent(HubEvent hubEvent) {
        HubEventAvro avro = hubRegistry.handle(hubEvent);
        log.info("Avro hub event: {}", avro);
        producer.send(new ProducerRecord<>(hubTopic, avro));
    }
}
